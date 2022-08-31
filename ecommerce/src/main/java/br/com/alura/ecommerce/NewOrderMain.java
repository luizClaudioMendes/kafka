package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties()); //<String,String> é a chave,valor, sendo o valor o tipo da mensagem

        var value = "12345, 6789, 1209";
        var record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", value, value); // parametros: topico, chave, mensagem

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando nesse topico: " + data.topic() + "::: partition " + data.partition() + "/ offset" + data.offset() + "/ timestamp" + data.timestamp());
        };

        var email = "thank you for your order! we are processing your order!";
        var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", email, email);

        // producer.send(record); // envia a mensagem assincrona
        // producer.send(record).get(); // envia a mensagem sincrona (espera a resposta de recebimento)
        producer.send(record, callback).get(); // envia a mensagem sincrona com callback (lambda)
        //segunda mensagem
        producer.send(emailRecord, callback).get();// envia a mensagem sincrona com callback (lambda)
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); // ip e porta do kafka
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da chave
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // nome da classe de deserializaçao da mensagem
        return properties;
    }
}
