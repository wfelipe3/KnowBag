package knowbag.pomodoro.poc.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

/**
 * Created by feliperojas on 12/24/15.
 */
public class RabbitMQPing {

    public static final String QUEUE = "hello";

    @Test
    public void testHelloWorld() throws IOException, TimeoutException, InterruptedException {
        List<String> value = new ArrayList<>();

        executeWithMqIn("192.168.99.100", channel -> {
            try {
                channel.basicConsume(QUEUE, true, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                            throws IOException {
                        value.add(new String(body, "UTF-8"));
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        executeWithMqInAndClose("192.168.99.100", channel -> {
            try {
                channel.basicPublish("", QUEUE, null, "ping".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        assertThat(value, hasItems("ping"));
    }

    private void executeWithMqInAndClose(String host, Consumer<Channel> consumer) throws IOException, TimeoutException {
        Connection connection = createConnection(host);
        Channel channel = connection.createChannel();
        try {
            consumer.accept(declareQueue(channel));
        } finally {
            channel.close();
            connection.close();
        }
    }

    private void executeWithMqIn(String host, Consumer<Channel> consumer) throws IOException, TimeoutException {
        consumer.accept(createChannel(host));
    }

    private Channel createChannel(String host) throws IOException, TimeoutException {
        Connection connection = createConnection(host);
        Channel channel = connection.createChannel();
        return declareQueue(channel);
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

}
