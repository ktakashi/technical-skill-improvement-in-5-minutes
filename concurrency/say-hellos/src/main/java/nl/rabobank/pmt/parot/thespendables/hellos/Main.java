package nl.rabobank.pmt.parot.thespendables.hellos;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String... args) {
        hellos(Integer.parseInt(args[0]), System.out);
    }

    public static void hellos(int count, PrintStream out) {
        var executor = Executors.newWorkStealingPool(5);
        Callable<Boolean> code = () -> {
            IntStream.range(0, count).parallel().forEach(i -> {
                out.println(Thread.currentThread().getName() + " hello world " + (i + 1));
            });
            return true;
        };
        var futures = List.of(executor.submit(code), executor.submit(code), executor.submit(code));
        futures.forEach(future -> {
            try {
                out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
            }
        });
        executor.shutdown();
    }
}
