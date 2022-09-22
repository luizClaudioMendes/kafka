package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class CreateUserService {

    private final Connection connection;

    CreateUserService() throws SQLException {
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
        var createUserService = new CreateUserService();
        try(var service = new KafkaService<>(
                CreateUserService.class.getSimpleName(), // consumer group
                "ECOMMERCE_NEW_ORDER", // topic
                createUserService::parse, // parse function
                new HashMap<String, String>()//cria um mapa vazio que nao vai ter nada para override nas propriedades
        )) {
            service.run();
        }
    }

    private final KafkaDispatcher<Order> orderDispatcher= new KafkaDispatcher<Order>();

    private void parse(ConsumerRecord<String, Message<Order>> record) throws SQLException {
        System.out.println("-----------------");
        System.out.println("Processando new order, checking for new user");
        System.out.println(record.value());

        var order = record.value().getPayload();

        if(isNewUser(order.getEmail())) {
            System.out.println("is new user");
            insertNewUser(order.getEmail());
            System.out.println("usuario " + order.getEmail() + " adicionado");
        } else {
            System.out.println("Using old user");
        }

    }

    private void insertNewUser(String email) throws SQLException {
        var insert = connection.prepareStatement(
                "insert into users (uuid, email) " +
                "values (?,?)");

        var uuid = UUID.randomUUID().toString();
        insert.setString(1, uuid);
        insert.setString(2, email);

        insert.execute();

        System.out.println("usuario "+  uuid + " e " + email + " adicionado");
    }

    private boolean isNewUser(String email) throws SQLException {
        var exists = connection.prepareStatement("select uuid from Users " +
                "where email = ? limit 1");
        exists.setString(1, email);
        var results = exists.executeQuery();
        return !results.next();
    }
}
