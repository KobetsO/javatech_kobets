package ua.edu.krnu.javatech.examples.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorFutureDemo {
    public static void main(String[] args) throws Exception {
        Callable<Integer> task = () -> {
            System.out.println("Running in: " + Thread.currentThread());
            Thread.sleep(300);
            return 12 * 12;
        };

        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            Future<Integer> future = executor.submit(task);
            System.out.println("Task submitted.");

            System.out.println("Result = " + future.get());
        }
    }
}
