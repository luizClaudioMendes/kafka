package br.com.alura.ecommerce;

import br.com.alura.ecommerce.consumer.ConsumerService;
import br.com.alura.ecommerce.consumer.KafkaService;
import br.com.alura.ecommerce.consumer.ServiceRunner;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ReadingReportService implements ConsumerService<User> {

    private static final Path SOURCE = new File("src/main/resources/report.txt").toPath();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new ServiceRunner(ReadingReportService::new).start(3); // starta com 3 threads diferentes (paralelizaçao usando threads)
    }

    public void parse(ConsumerRecord<String, Message<User>> record) throws IOException {
        System.out.println("-----------------");
        System.out.println("Processando report for " + record.value());

        var message = record.value();
        var user = message.getPayload();
        var target = new File(user.getReportPath() );
        IO.copyTo(SOURCE, target);
        IO.append(target, "created for " + user.getUuid());

        System.out.println("File Created: "+ target.getAbsolutePath());

        
    }

    @Override
    public String getTopic() {
        return "ECOMMERCE_USER_GENERATE_READING_REPORT";
    }

    @Override
    public String getConsumerGroup() {
        return ReadingReportService.class.getSimpleName();
    }
}
