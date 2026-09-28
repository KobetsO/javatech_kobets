package ua.edu.krnu.javatech.examples.basic;

public class ThreadRunnableDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new SimpleTask("Task A"), "worker-1");
        Thread t2 = new Thread(new SimpleTask("Task B"), "worker-2");
        Thread t3 = new Thread(new SimpleTask("Task C"), "worker-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("All threads finished.");
    }
}
