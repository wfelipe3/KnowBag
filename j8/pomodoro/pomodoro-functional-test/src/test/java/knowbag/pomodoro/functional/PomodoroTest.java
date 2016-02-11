package knowbag.pomodoro.functional;

import com.rabbitmq.client.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import javaslang.control.Try;
import knowbag.pomodoro.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;
import static knowbag.pomodoro.time.StopWatch.TimeUnit.MILLIS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by feliperojas on 10/27/15.
 */
public class PomodoroTest {

    private static final String QUEUE = "pomodoro-mq";

    @Test
    public void givenStartedPomodoroWhenRestPhaseStartsThenReceiveNotificationInGivenTime() throws Exception {
        StopWatch.StopWatchStart start = StopWatch.StopWatchStart.start();
        PomodoroClient.newPomodoroIn("http://192.168.99.100:4567")
                .withWorkTime(2, SECONDS)
                .withRestTime(1, SECONDS)
                .start();
        Sync sync = Sync.newSync();
        assertWhenMessageArrive(message -> {
            assertThat(message, is("rest"));
            sync.continueSync();
        });
        sync.waitSync();
        StopWatch.StopWatchStop stop = start.stop();
        assertThat(stop.time().in(MILLIS), is(2000L));
    }

    private void assertWhenMessageArrive(java.util.function.Consumer<String> consumer) {
        executeWithMqIn("192.168.99.100", channel -> {
            try {
                channel.basicConsume(QUEUE, true, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                            throws IOException {
                        consumer.accept(new String(body, "UTF-8"));
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    static private class PomodoroClient {
        private final String url;
        private int workTime;
        private TimeUnit workTimeUnit;
        private int restTime;
        private TimeUnit restTimeUnit;

        public static PomodoroClient newPomodoroIn(String url) {
            return new PomodoroClient(url);
        }

        private PomodoroClient(String url) {
            this.url = url;
        }

        public Pomodoro start() {
            Client client = Client.create();
            try {
                ClientResponse response = client.resource(MessageFormat.format("{0}/{1}", url, "pomodoro")).post(ClientResponse.class);
                if (response.getStatus() != 200) {
                    throw new RuntimeException();
                }
                return new Pomodoro();
            } finally {
                client.destroy();
            }
        }

        public PomodoroClient withWorkTime(int workTime, TimeUnit timeUnit) {
            this.workTime = workTime;
            this.workTimeUnit = timeUnit;
            return this;
        }

        public PomodoroClient withRestTime(int restTime, TimeUnit timeUnit) {
            this.restTime = restTime;
            this.restTimeUnit = timeUnit;
            return this;
        }
    }

    private static class Pomodoro {
    }

    private void executeWithMqIn(String host, java.util.function.Consumer<Channel> consumer) {
        consumer.accept(createChannel(host));
    }

    private Channel createChannel(String host) {
        return Try.of(() -> {
            Connection connection = createConnection(host);
            Channel channel = connection.createChannel();
            return declareQueue(channel);
        }).orElseThrow((Function<Throwable, RuntimeException>) RuntimeException::new);
    }

    private Channel declareQueue(Channel channel) throws IOException {
        channel.queueDeclare(QUEUE, false, false, false, null);
        return channel;
    }

    private Connection createConnection(String host) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        return factory.newConnection();
    }

    private static class Sync {

        private final Object sync;

        public Sync() {
            this.sync = new Object();
        }

        public static Sync newSync() {
            return new Sync();
        }

        public void continueSync() {
            synchronized (sync) {
                sync.notify();
            }
        }

        public void waitSync() {
            synchronized (sync) {
                try {
                    sync.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
