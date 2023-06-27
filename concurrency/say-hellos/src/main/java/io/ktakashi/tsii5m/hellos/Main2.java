package io.ktakashi.tsii5m.hellos;

import java.io.PrintStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main2 {
    public static void main(String[] args) {
        var threads = IntStream.range(0, Integer.parseInt(args[0]))
                .mapToObj(i -> new Thread(makeRunnable(i, System.out)))
                .toList();
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private static Runnable makeRunnable(int i, PrintStream out) {
        return () -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            out.println("hello world " + i);
        };
    }
}
