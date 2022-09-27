package br.com.alura.ecommerce;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OrdersDatabase implements Closeable {

    private final Connection connection;

    OrdersDatabase() throws SQLException {
        String url = "jdbc:sqlite:target/orders_database.db"; // o sqlite vai criar o banco users_database
        this.connection = DriverManager.getConnection(url);
        try {
            connection.createStatement().execute("create table orders (uuid varchar(200) primary key)");

            System.out.println("ORDER TABLE CREATED");
        } catch (SQLException ex) {
            System.out.println("ERROR CREATING ORDER TABLE");
            // be carefull, the sql could be wrong
            ex.printStackTrace();
        }

    }

    public boolean saveNewOrder(Order order) throws SQLException {
        // String url = "jdbc:sqlite:target/orders_database.db"; // o sqlite vai criar o banco users_database
        // this.connection = DriverManager.getConnection(url);
        try {
            var stmt = connection.prepareStatement("insert into orders (uuid) values (?)");
            stmt.setString(1, order.getOrderId());
            stmt.execute();

            System.out.println("new order saved");
            return true;
        } catch (SQLException ex) {
            // be carefull, the sql could be wrong
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
