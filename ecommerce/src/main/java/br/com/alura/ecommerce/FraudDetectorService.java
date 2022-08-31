package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class FraudDetectorService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER")); // inscriçao nos topicos ouvidos

        while (true) { // fica chamando o kafka para procurar mensagens

            var records = consumer.poll(Duration.ofMillis(100)); // consulta o kafka por mais mensagens

            if (records.isEmpty()) {
                System.out.println("encontrei " + records.count() + " registros");
                continue;
            }
            for (var record : records) {
                System.out.println("-----------------");
                System.out.println("Processando new order, checking for fraud");
                System.out.println(record.key());
                System.out.println(record.value());
                System.out.println(record.partition());
                System.out.println(record.offset());

                try {
                    // simular um serviço demorado
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // ignoring
                    throw new RuntimeException(e);
                }
                System.out.println("Order processed");
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());// consumer group name

        return properties;
    }
}
