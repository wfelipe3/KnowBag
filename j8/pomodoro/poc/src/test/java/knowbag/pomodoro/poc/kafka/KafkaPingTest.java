package knowbag.pomodoro.poc.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by feliperojas on 12/19/15.
 */
public class KafkaPingTest {

    @Test
    public void pingKafkaServer() throws InterruptedException, TimeoutException, ExecutionException {
        sendPing();
        Thread.sleep(1000);
        String s = pollFromConsumer(createConsumer());
        assertThat(s, is("ping"));
    }

    private String pollFromConsumer(KafkaConsumer<String, String> consumer) {
        ConsumerRecords<String, String> records = consumer.poll(10);
        if (records.isEmpty()) {
            return pollFromConsumer(consumer);
        } else {
            String value = records.iterator().next().value();
            consumer.close();
            return value;
        }

    }

    private KafkaConsumer<String, String> createConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.99.100:9092");
        props.put("group.id", "new group");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("ping"));
        return consumer;
    }

    private void sendPing() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.99.100:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<>("ping", "ping", "ping"));

        producer.close();
    }
}
