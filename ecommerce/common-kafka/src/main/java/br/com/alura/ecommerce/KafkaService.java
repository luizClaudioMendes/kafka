package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

class KafkaService<T> implements Closeable {
    private final KafkaConsumer<String, Message<T>> consumer;
    private final ConsumerFunction parse;

    KafkaService(String groupID, String topic, ConsumerFunction<T> parse, Class<T> type, Map<String, String> properties) {
        this(parse, groupID, type, properties);
        consumer.subscribe(Collections.singletonList(topic)); // inscriçao nos topicos ouvidos
    }

    KafkaService(String groupID, Pattern topic, ConsumerFunction<T> parse, Class<T> type, Map<String, String> properties) {
        this(parse, groupID, type, properties);
        consumer.subscribe(topic); // inscriçao nos topicos por regex
    }

    private KafkaService(ConsumerFunction parse, String groupID, Class<T> type, Map<String, String> properties) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(getProperties(type, groupID, properties));
    }

    void run() {
        while (true) { // fica chamando o kafka para procurar mensagens
            var records = consumer.poll(Duration.ofMillis(100)); // consulta o kafka por mais mensagens
            if (!records.isEmpty()) {
                System.out.println("encontrei " + records.count() + " registros");
                for (var record : records) {
                    try {
                        parse.consume(record);
                    } catch (Exception e) {
                        // only catches exception because no matter which exception
                        // i want to recover and parse next one
                        // so far, just logging
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Properties getProperties(Class<T> type, String groupID, Map<String, String> overrideProperties) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());// deserializador da chave
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName()); // deserializador de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupID);// consumer group name
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());// client name

        properties.putAll(overrideProperties);//sobrescreve as propriedades
        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
