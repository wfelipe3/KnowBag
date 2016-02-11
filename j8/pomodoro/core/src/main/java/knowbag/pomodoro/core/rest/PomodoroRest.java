package knowbag.pomodoro.core.rest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javaslang.control.Try;
import knowbag.pomodoro.ioutils.GenericAutoCloseable;
import spark.Spark;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * Created by feliperojas on 10/28/15.
 */
public class PomodoroRest {

    private static final String QUEUE = "pomodoro-mq";

    public static void main(String[] args) {
        Spark.post("/pomodoro", (req, res) -> {
            Thread.sleep(2000);
            executeWithMQ("192.168.99.100", channel -> Try.run(() -> channel.basicPublish("", QUEUE, null, "rest".getBytes())));
            return "";
        });
    }

    private static void executeWithMQ(String host, Consumer<Channel> consumer) {
        try (GenericAutoCloseable<Connection> connection = createConnection(host);
             GenericAutoCloseable<Channel> channel = getChannel(connection.get())) {
            consumer.accept(declareQueue(channel.get()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static GenericAutoCloseable<Channel> getChannel(Connection connection) {
        try {
            return GenericAutoCloseable.of(connection.createChannel(), c -> Try.run(c::close));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Channel declareQueue(Channel channel) throws IOException {
        channel.queueDeclare(QUEUE, false, false, false, null);
        return channel;
    }

    private static GenericAutoCloseable<Connection> createConnection(String host) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            return GenericAutoCloseable.of(factory.newConnection(), c -> Try.run(c::close));
        } catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
