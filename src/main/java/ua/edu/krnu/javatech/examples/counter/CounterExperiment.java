package ua.edu.krnu.javatech.examples.counter;

import java.util.ArrayList;
import java.util.List;

public class CounterExperiment {
    private static final int THREADS = 8;
    private static final int INCREMENTS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        runFiveTimes("UnsafeCounter", UnsafeCounter::new);
        runFiveTimes("SynchronizedCounter", SynchronizedCounter::new);
        runFiveTimes("AtomicCounter", AtomicCounter::new);
    }

    private static void runFiveTimes(String name,
                                     java.util.function.Supplier<Counter> factory)
            throws InterruptedException {
        System.out.println("\n" + name);
        for (int run = 1; run <= 5; run++) {
            runExperiment(factory.get(), run);
        }
    }

    private static void runExperiment(Counter counter, int run)
            throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Runnable task = () -> {
            for (int i = 0; i < INCREMENTS; i++) counter.increment();
        };

        for (int i = 0; i < THREADS; i++)
            threads.add(new Thread(task));

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        int expected = THREADS * INCREMENTS;
        System.out.printf("Run %d: expected=%d, actual=%d%n",
                run, expected, counter.getValue());
    }
}
