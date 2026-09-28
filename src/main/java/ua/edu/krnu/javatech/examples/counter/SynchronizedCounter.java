package ua.edu.krnu.javatech.examples.counter;

public class SynchronizedCounter implements Counter {
    private int value = 0;

    public synchronized void increment() { value++; }
    public synchronized int getValue() { return value; }
}
