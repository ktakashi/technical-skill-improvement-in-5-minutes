package io.ktakashi.tsii5m.fcd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Main {
    public static void main(String... args) {
        if (args.length < 2) {
            System.err.println("cmd filename algorithm");
            System.exit(-1);
        }
        var filename = args[0];
        var algorithm = args[1];
        try {
            System.out.println(fcd(filename, algorithm));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

    }

    public static String fcd(String filename, String algorithm) throws Exception {
        return digest(getContent(filename), algorithm);
    }

    private static String digest(byte[] content, String algorithm) throws NoSuchAlgorithmException {
        var md = MessageDigest.getInstance(algorithm);
        return Base64.getEncoder().encodeToString(md.digest(content));
    }

    private static byte[] getContent(String filename) throws IOException {
        return Files.readAllBytes(Paths.get(filename));
    }
}
