package luzbetak;

/**
 * Apache Kafka App
 *
 */
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class App {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();

        // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.20.0.3:9092");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        RandomString rand =  new RandomString();
        Producer<String, String> producer = new KafkaProducer<>(props);

        try { 
            for (int i = 0; i < 1000000; i++) {

                ProducerRecord<String, String> data;
                // data = new ProducerRecord<>("even", 0, Integer.toString(i), String.format("%d is even", i));
                data = new ProducerRecord<>("test", 0, rand.generateString(10), rand.generateString(20));

                System.out.println("Sending Data: " + i + " " + data.toString());
                producer.send(data);
                Thread.sleep(500);

            }
        } finally {

            System.out.println("Producer Flush");
            producer.flush();

            System.out.println("Producer Close");
            producer.close();
       }
   }
}
