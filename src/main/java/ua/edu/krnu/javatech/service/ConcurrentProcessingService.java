package ua.edu.krnu.javatech.service;

import ua.edu.krnu.javatech.concurrency.EdgeNodeProcessingTask;
import ua.edu.krnu.javatech.model.EdgeNode;
import ua.edu.krnu.javatech.model.ProcessingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentProcessingService {

    public List<ProcessingResult> sequential(List<EdgeNode> nodes) throws Exception {
        List<ProcessingResult> results = new ArrayList<>();
        for (EdgeNode n : nodes) {
            results.add(new EdgeNodeProcessingTask(n).call());
        }
        return results;
    }

    public List<ProcessingResult> fixedPool(List<EdgeNode> nodes) throws Exception {
        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            return execute(nodes, executor);
        }
    }

    public List<ProcessingResult> virtualThreads(List<EdgeNode> nodes) throws Exception {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            return execute(nodes, executor);
        }
    }

    private List<ProcessingResult> execute(List<EdgeNode> nodes, ExecutorService executor) throws Exception {
        List<Future<ProcessingResult>> futures = new ArrayList<>();
        for (EdgeNode n : nodes) {
            futures.add(executor.submit(new EdgeNodeProcessingTask(n)));
        }

        List<ProcessingResult> results = new ArrayList<>();
        for (Future<ProcessingResult> f : futures) {
            results.add(f.get());
        }
        return results;
    }
}
