package ua.edu.krnu.javatech.examples.basic;

public class SimpleTask implements Runnable {
    private final String taskName;

    public SimpleTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.printf("%s | %s | step %d%n",
                    Thread.currentThread().getName(), taskName, i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
