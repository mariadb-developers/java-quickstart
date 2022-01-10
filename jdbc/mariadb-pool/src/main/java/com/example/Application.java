package com.example;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Defines the entry point of the application. Uses the {@code Service}
 * class to simulate requesting a service 1000 times using a thread pool of
 * 200 threads.
 */
public class Application {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(200);

        IntStream.range(0, 1000)
                .mapToObj(i -> (Runnable) () -> requestService())
                .forEach(executorService::submit);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    public static void requestService() {
        try {
            Service service = Service.getInstance();
            List<String> list = service.getAllProgrammingLanguages();
            list.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
