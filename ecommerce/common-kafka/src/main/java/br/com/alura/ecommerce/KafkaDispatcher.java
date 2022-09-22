package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, Message<T>> producer;

    KafkaDispatcher() {
        this.producer = new KafkaProducer<>(properties());
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // ip e porta do kafka
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da chave
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName()); // nome da classe de deserializaçao da mensagem
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        return properties;
    }

    Future<RecordMetadata> sendAsync(String topic, String key, CorrelationId id, T payload) throws ExecutionException, InterruptedException {
        var value = new Message<T>(id, payload);
        var record = new ProducerRecord<>(topic, key, value); // parametros: topico, chave, mensagem

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando nesse topico: " + data.topic() + "::: partition " + data.partition() + "/ offset" + data.offset() + "/ timestamp" + data.timestamp());
        };

        return producer.send(record, callback);// envia a mensagem assincrona
    }

    void send(String topic, String key, CorrelationId id, T payload) throws ExecutionException, InterruptedException {
        Future<RecordMetadata> future = sendAsync(topic, key, id, payload);
        future.get(); // envia a mensagem sincrona com callback (lambda)
    }


    @Override
    public void close() {
        producer.close();
    }
}
