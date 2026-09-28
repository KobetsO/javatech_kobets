package ua.edu.krnu.javatech.concurrency;

import ua.edu.krnu.javatech.model.EdgeNode;
import ua.edu.krnu.javatech.model.ProcessingResult;

import java.util.concurrent.Callable;

public class EdgeNodeProcessingTask implements Callable<ProcessingResult> {
    private final EdgeNode node;

    public EdgeNodeProcessingTask(EdgeNode node) {
        this.node = node;
    }

    @Override
    public ProcessingResult call() throws InterruptedException {
        long delayMs = 100 + (node.getId() % 5) * 50;
        Thread.sleep(delayMs); // імітація блокуючого I/O або опитування вузла

        Thread current = Thread.currentThread();
        boolean success = node.getHostname() != null && !node.getHostname().isBlank();

        return new ProcessingResult(
                node.getId(), node.getHostname(), success,
                current.threadId(), current.isVirtual(), delayMs
        );
    }
}
