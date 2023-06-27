package nl.rabobank.pmt.parot.thespendables.sc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Main {
    public static void main(String... args) throws IOException {
        evaluate(System.in, System.out, System.out);
    }

    public static void evaluate(InputStream in, OutputStream prompt, OutputStream out) throws IOException {
        var reader = new Scanner(in);
        var prompter = new OutputStreamWriter(prompt);
        var writer = new OutputStreamWriter(out);

        prompt(prompter);

        while (reader.hasNextLine()) {
            var line = reader.nextLine();
            if (line.equalsIgnoreCase("end")) break;

            try {
                var tokenizer = new Scanner(line);
                var total = 0;
                var first = true;

                while (tokenizer.hasNext()) {
                    var token = tokenizer.next();
                    switch (token) {
                        case "+" -> total += nextInt(tokenizer);
                        case "-" -> total -= nextInt(tokenizer);
                        default -> {
                            if (first) {
                                total = Integer.parseInt(token);
                                first = false;
                            } else {
                                throw new IllegalStateException("Invalid order");
                            }
                        }
                    }
                }
                writer.write(Integer.toString(total));
                writer.write('\n');
            } catch (RuntimeException e) {
                writer.write(e.getMessage());
                writer.write('\n');
            }
            writer.flush();
            prompt(prompter);
        }
    }

    private static int nextInt(Scanner tokenizer) {
        if (tokenizer.hasNextInt()) {
            return tokenizer.nextInt();
        }
        throw new IllegalStateException("Invalid order of the expression");
    }

    private static void prompt(OutputStreamWriter writer) throws IOException {
        writer.write("Input (type 'end' to finish)> ");
        writer.flush();
    }
}
