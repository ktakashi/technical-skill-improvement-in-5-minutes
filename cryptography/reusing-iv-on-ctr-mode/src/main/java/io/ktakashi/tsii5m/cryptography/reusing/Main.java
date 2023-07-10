package io.ktakashi.tsii5m.cryptography.reusing;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HexFormat;

public class Main {
    private static final String CIPHER_TEXT0 = "a6f0c21ed2d791f4f8f06463ee441105743ad88e95b724b1046faaf432dafcc23356be54b8b1";
    private static final String PLAIN_TEXT0 = "This is my test message for my system.";
    private static final String[] CIPHER_TEXTS = new String[] {
            "bbbfdd08d2dc97bdf9fd6476e517005d7a3ac79191be35b11165bba12dc6fcc23356be54b8be",
            "bcf7c90296c7c2b7f4e76472f347094a702b8b908df032e81174bdb97383b2d43c40b810"
    };

    public static void main(String... args) {
        var cipherText0 = HexFormat.of().parseHex(CIPHER_TEXT0);
        var plainText0 = PLAIN_TEXT0.getBytes(StandardCharsets.UTF_8);
        var keyIv = byteArrayXor(cipherText0, plainText0);
        var plainTexts = Arrays.stream(CIPHER_TEXTS)
                .map(ct -> HexFormat.of().parseHex(ct))
                .map(ct -> byteArrayXor(keyIv, ct))
                .map(String::new)
                .toList();
        System.out.println(plainTexts);
    }

    private static byte[] byteArrayXor(byte[] ba0, byte[] ba1) {
        int len = Math.min(ba0.length, ba1.length);
        var r = new byte[len];
        for (int i = 0; i < len; i++) {
            r[i] = (byte)(ba0[i] ^ ba1[i]);
        }
        return r;
    }
}
