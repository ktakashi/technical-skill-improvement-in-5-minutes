package nl.rabobank.pmt.parot.thespendables.hellos;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class Main3 {
    public static void main(String... args) {
        hellos(Integer.parseInt(args[0]), System.out);
    }

    public static void hellos(int count, PrintStream out) {
        var executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < count; i++) {
            var c = i;
            executor.submit(() -> {
                var sb = new StringBuilder();
                sb.append("hello world ").append(c + 1).append("\n");
                try {
                    out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    // ignore
                }
            });
        }
        executor.shutdown();
    }
}
