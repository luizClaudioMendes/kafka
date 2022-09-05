package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class FraudDetectorService {
    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try(var service = new KafkaService<Order>(
                FraudDetectorService.class.getSimpleName(), // group
                "ECOMMERCE_NEW_ORDER", // topic
                fraudService::parse, // parse function
                Order.class, // expected type of message
                new HashMap<String, String>()//cria um mapa vazio que nao vai ter nada para override nas propriedades
        )) {
            service.run();
        }
    }

    private final KafkaDispatcher<Order> orderDispatcher= new KafkaDispatcher<Order>();

    private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException {
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

        var order = record.value();
        if(isFraud(order)) {
            // pretending that the fraud is >= 4500
            System.out.println("Order is a fraud");
            orderDispatcher.send("ECOMMERCE_ORDER_REJECTED", order.getUserId(), order);

        } else {
            orderDispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getUserId(), order);
            System.out.println("Approved: " + order);
        }
    }

    private static boolean isFraud(Order order) {
        return order.getAmount().compareTo(new BigDecimal("4500")) >= 0;
    }
}
