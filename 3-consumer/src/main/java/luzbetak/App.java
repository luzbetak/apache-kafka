package luzbetak;

/**
 * Apache Kafka Consumer
 * https://kafka.apache.org/20/javadoc/org/apache/kafka/clients/consumer/KafkaConsumer.html
 */
//import java.util.Properties;
//import java.util.Arrays;
import java.util.*;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class App {

   public static void main(String[] args) throws Exception {

      Properties props = new Properties();
      props.put("bootstrap.servers", "localhost:9092");
      props.put("group.id", "test");
      props.put("enable.auto.commit", "false");
      props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
      props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

      KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
      consumer.subscribe(Arrays.asList("test"));
      final int minBatchSize = 10;

      List<ConsumerRecord<String, String>> buffer = new ArrayList<>();

      while (true) {

          ConsumerRecords<String, String> records = consumer.poll(100);

          for (ConsumerRecord<String, String> record : records) {

              System.out.println(record.toString());
              // System.out.println(record.key() + " " + record.value());
              buffer.add(record);
          }

          if (buffer.size() >= minBatchSize) {

              System.out.println("--------- Buffer Listing Check --------");
              System.out.println(buffer.get(1).key() + " " + buffer.get(1).value());

              //insertIntoDb(buffer);
              consumer.commitSync();
              buffer.clear();
          }
      }
   }
}
