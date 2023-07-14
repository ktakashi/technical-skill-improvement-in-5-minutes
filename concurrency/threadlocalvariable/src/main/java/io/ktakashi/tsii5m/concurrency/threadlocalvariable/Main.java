package io.ktakashi.tsii5m.concurrency.threadlocalvariable;

import java.util.stream.IntStream;

public class Main {
    public static void main(String... args) {
        var threads = IntStream.range(0, 10).mapToObj(i -> new Thread(() -> printTheArgument(i))).toList();
        threads.forEach(Thread::start);
        threads.forEach(Main::safeJoin);
        System.out.println(MyFormatter.format("original"));
    }

    private static void printTheArgument(int i) {
        var values = new Object[i];
        MyFormatter.FORMAT_STRING.set("Format #" + i + ": " + "%s ".repeat(i));
        for (int j = 0; j < i; j++) values[j] = "val" + j;
        System.out.println(MyFormatter.format(values));
        MyFormatter.FORMAT_STRING.remove(); // clean up, otherwise keep holding it (possible memory leak)

    }
    private static void safeJoin(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
