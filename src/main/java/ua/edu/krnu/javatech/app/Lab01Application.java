package ua.edu.krnu.javatech.app;

import ua.edu.krnu.javatech.model.*;
import ua.edu.krnu.javatech.service.ConcurrentProcessingService;

import java.util.ArrayList;
import java.util.List;

public class Lab01Application {
    public static void main(String[] args) throws Exception {
        List<EdgeNode> nodes = createNodes(30);
        ConcurrentProcessingService service = new ConcurrentProcessingService();

        measure("Sequential", () -> service.sequential(nodes));
        measure("Fixed pool (4 threads)", () -> service.fixedPool(nodes));
        measure("Virtual threads", () -> service.virtualThreads(nodes));
    }

    private static List<EdgeNode> createNodes(int count) {
        List<EdgeNode> result = new ArrayList<>();
        NodeStatus[] states = NodeStatus.values();
        String[] arches = {"x86_64", "ARM64", "RISC-V"};

        for (int i = 1; i <= count; i++) {
            result.add(new EdgeNode(
                    "edge-node-" + i,
                    arches[i % arches.length],
                    2 * ((i % 4) + 1), // cpuCores: 2, 4, 6, 8
                    8 * ((i % 4) + 1), // ramGb: 8, 16, 24, 32
                    128 * ((i % 3) + 1), // storageGb
                    states[i % states.length],
                    "Site-" + ((i % 5) + 1)
            ));
        }
        return result;
    }

    private static void measure(String mode, Task task) throws Exception {
        long start = System.nanoTime();
        List<ProcessingResult> results = task.run();
        double timeMs = (System.nanoTime() - start) / 1_000_000.0;
        long ok = results.stream().filter(ProcessingResult::success).count();
        long virtual = results.stream().filter(ProcessingResult::virtualThread).count();

        System.out.printf("%n%s: %.2f ms, processed=%d, success=%d, virtual=%d%n",
                mode, timeMs, results.size(), ok, virtual);
    }

    @FunctionalInterface
    private interface Task {
        List<ProcessingResult> run() throws Exception;
    }
}
