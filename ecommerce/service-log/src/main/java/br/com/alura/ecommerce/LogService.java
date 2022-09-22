package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class LogService {
    public static void main(String[] args) {
        var logService = new LogService();
        try (var service = new KafkaService(
                LogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"), // este subject nao é dinamico, entao se surgir um novo ele precisa ser reiniciado
                logService::parse,
                String.class,
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())
        )) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Message<String>> record) {
            System.out.println("-----------------");
            System.out.println("LOG");
            System.out.println(record.topic());
            System.out.println(record.key());
            System.out.println(record.value());
            System.out.println(record.partition());
            System.out.println(record.offset());
    }
}
