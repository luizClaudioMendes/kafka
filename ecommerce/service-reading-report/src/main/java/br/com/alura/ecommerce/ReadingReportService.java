package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ReadingReportService {

    private static final Path SOURCE = new File("src/main/resources/report.txt").toPath();

    public static void main(String[] args) {
        var reportService = new ReadingReportService();
        try(var service = new KafkaService<>(
                ReadingReportService.class.getSimpleName(), // group
                "USER_GENERATE_READING_REPORT", // topic
                reportService::parse, // parse function
                User.class, // expected type of message
                new HashMap<String, String>()//cria um mapa vazio que nao vai ter nada para override nas propriedades
        )) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, User> record) throws IOException {
        System.out.println("-----------------");
        System.out.println("Processando report for " + record.value());

        var user = record.value();
        var target = new File(user.getReportPath() );
        IO.copyTo(SOURCE, target);
        IO.append(target, "created for " + user.getUuid());

        System.out.println("File Created: "+ target.getAbsolutePath());

        
    }
}
