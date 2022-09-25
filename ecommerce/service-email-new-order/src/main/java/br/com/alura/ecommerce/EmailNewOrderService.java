package br.com.alura.ecommerce;

import br.com.alura.ecommerce.consumer.KafkaService;
import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class EmailNewOrderService {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var emailService = new EmailNewOrderService();
        try (var service = new KafkaService<>(
                EmailNewOrderService.class.getSimpleName(), // group
                "ECOMMERCE_NEW_ORDER", // topic
                emailService::parse, // parse function
                new HashMap<String, String>()//cria um mapa vazio que nao vai ter nada para override nas propriedades
        )) {
            service.run();
        }
    }

    private final KafkaDispatcher<String> emailDispatcher = new KafkaDispatcher<>();

    private void parse(ConsumerRecord<String, Message<Order>> record) throws ExecutionException, InterruptedException {
        System.out.println("-----------------");
        System.out.println("Processando new order, preparing email");
        System.out.println(record.key());
        var message = record.value();
        System.out.println(message);

        var order = message.getPayload();
        var emailCode = "thank you for your order! we are processing your order!";
        var id = message.getId().continueWith(EmailNewOrderService.class.getSimpleName());
        emailDispatcher.send(
                "ECOMMERCE_SEND_EMAIL",
                order.getEmail(),
                id,
                emailCode);
    }
}
