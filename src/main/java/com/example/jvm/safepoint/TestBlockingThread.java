package com.example.jvm.safepoint;

import java.util.function.Consumer;

public class TestBlockingThread {
	static Consumer<Long> sleep = millis -> {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    };

    static Thread t1 = new Thread(() -> {
        while (true) {
            long start = System.currentTimeMillis();
            sleep.accept(1000L);
            long cost = System.currentTimeMillis() - start;
            (cost > 1010L ? System.err : System.out).printf("thread: %s, costs %d ms\n", Thread.currentThread().getName(), cost);
        }
    });

    static Thread t2 = new Thread(() -> {
        while (true) {
            for (int i = 1; i <= 1000000000; i++) {
                boolean b = 1.0 / i == 0;
            }
            sleep.accept(10L);
        }
    });

    public static final void main(String[] args) {
        t1.start();
        sleep.accept(1500L);
        t2.start();
    }
}
