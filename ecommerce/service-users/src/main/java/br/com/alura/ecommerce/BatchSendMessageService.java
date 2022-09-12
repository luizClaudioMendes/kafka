package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BatchSendMessageService {
    private final Connection connection;

    BatchSendMessageService() throws SQLException {
        String url = "jdbc:sqlite:target/users_database.db"; // o sqlite vai criar o banco users_database
        this.connection = DriverManager.getConnection(url);
        try {
            connection.createStatement().execute("create table users (" +
                    "uuid varchar(200) primary key," +
                    "email varchar(200))");
        } catch (SQLException ex) {
            // be carefull, the sql could be wrong
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) throws SQLException {
        var batchService = new BatchSendMessageService();
        try(var service = new KafkaService<>(
                BatchSendMessageService.class.getSimpleName(), // consumer group
                "SEND_MESSAGE_TO_ALL_USERS", // topic
                batchService::parse, // parse function
                String.class, // expected type of message
                Map.of()//cria um mapa vazio que nao vai ter nada para override nas propriedades
        )) {
            service.run();
        }
    }

    private final KafkaDispatcher<Order> orderDispatcher= new KafkaDispatcher<Order>();

    private final KafkaDispatcher<User> userDispatcher = new KafkaDispatcher();
    private void parse(ConsumerRecord<String, String> record) throws SQLException, ExecutionException, InterruptedException {
        System.out.println("-----------------");
        System.out.println("Processando new batch");
        System.out.println("Topic: " + record.value());

        for (User user : getAllUsers()) {
            userDispatcher.send(record.value(), user.getUuid(), user);
        }

    }

    private List<User> getAllUsers() throws SQLException {
        var results = connection.prepareStatement("select uuid from Users").executeQuery();
        List<User> users = new ArrayList<>();
        while(results.next()) {
            users.add(new User(results.getString(1)));
        }
        return users;
    }
}
