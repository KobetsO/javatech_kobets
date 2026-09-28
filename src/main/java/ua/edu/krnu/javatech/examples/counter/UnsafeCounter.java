package ua.edu.krnu.javatech.examples.counter;

public class UnsafeCounter implements Counter {
    private int value = 0;

    public void increment() { value++; }
    public int getValue() { return value; }
}
