package br.com.alura.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var dispatcher = new KafkaDispatcher()) {

            for (int i = 0; i < 10; i++) {
                var key = UUID.randomUUID().toString();
                var value = key + "- 12345, 6789, 1209";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

                var email = "thank you for your order! we are processing your order!";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, value);
            }
        }
    }
}
