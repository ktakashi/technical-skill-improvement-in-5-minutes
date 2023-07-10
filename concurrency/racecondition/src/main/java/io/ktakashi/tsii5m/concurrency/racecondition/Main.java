package io.ktakashi.tsii5m.concurrency.racecondition;

import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void main(String... args) {
        var counter = new int[]{1};
        var threads = IntStream.range(0, 2)
                .mapToObj(c -> new Thread(() -> process(counter, c)))
                .toList();
        threads.forEach(Thread::start);
        threads.forEach(Main::safeJoin);
        System.out.println(counter[0]);

        // Validation, if counter and counter0 are different or not :)
        var counter0 = new int[]{1};
        for (int i = 0; i < 2; i++) process(counter0, i);
        System.out.println(counter0[0]);
    }

    private static void process(int[] counter, int c) {
        var r = new Random();
        for (int i = 1; i < 100; i++) {
            int t = counter[0];
            t += i * ((c % 2 == 0)? c : -c);
            safeSleep(r.nextInt(10)); // To avoid accidental sequential execution
            counter[0] = t;
        }
    }

    private static void safeSleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }

    private static void safeJoin(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
