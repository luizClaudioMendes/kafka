package br.com.alura.ecommerce;

import br.com.alura.ecommerce.dispatcher.KafkaDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class NewOrderServlet extends HttpServlet implements Servlet {

    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<Order>();

    @Override
    public void destroy() {
        super.destroy();
        orderDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            // we are not caring for any security issues
            var email = req.getParameter("email");
            var amount = new BigDecimal(req.getParameter("amount"));

            var orderId = req.getParameter("uuid");// id usado para idempotencia vinda do cliente

            var order = new Order(orderId, amount, email);

            try(var database = new OrdersDatabase()) {

                if (database.saveNewOrder(order)) {
                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", email,
                            new CorrelationId(NewOrderServlet.class.getSimpleName()),
                            order);

                    System.out.println("Processo da nova compra terminado");

                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println("New Order sent");
                } else {
                    System.out.println("Old order received");

                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println("Old Order sent");
                }
            }
        } catch (ExecutionException | InterruptedException | IOException | SQLException e) {
            throw new ServletException(e);
        }
    }
}
