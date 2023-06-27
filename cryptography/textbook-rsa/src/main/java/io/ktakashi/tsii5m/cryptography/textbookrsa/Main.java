package io.ktakashi.tsii5m.cryptography.textbookrsa;

import java.math.BigInteger;

public class Main {
    private static final BigInteger PUBLIC_MODULUS = new BigInteger("c96b4ef098c0fcdf55a50ab8aa86a5873dc88375e108a100035c35cb8de279d8e639452a31bb616d7fffe5112f682a370496390137141148c9bebde87720693c854ebc6d680da569d89c0f95664750e1babd3ffabcba704f161c2945bb1cf1c22bdd24d713b35cacfca9ddcf5c6b3530880bd9c656c150d8db2ef8922fbfba95", 16);
    private static final BigInteger PRIVATE_EXPONENT = new BigInteger("a8f55217b31b9843c3f51924452f83a509e7ac96f279298cf8449e7facfa80d9b86dec1df3efca4f6f62fa054b6e2693564ea96ce285fbe5c20e3601ce042b2173411d82fe296868e9f7b771441247098519b316d123d693a54ef0ef58044bc1d97f1d447c551efa52b2a7a2f68e200cc1fefe2aedafd7960d078b2c92daa295", 16);
    private static final BigInteger PUBLIC_EXPONENT = new BigInteger("10001", 16);

    public static void main(String... args) {
        var cipherText = encrypt(args.length > 1 ? args[0] : "test message");
        System.out.println("Cipher Text: 0x" + new BigInteger(cipherText).toString(16));
        System.out.println("Decrypt: " + decrypt(cipherText));
    }

    private static String decrypt(byte[] cipherText) {
        var cipherMessage = new BigInteger(cipherText);
        var message = cipherMessage.modPow(PRIVATE_EXPONENT, PUBLIC_MODULUS);
        return new String(message.toByteArray());
    }

    private static byte[] encrypt(String plainText) {
        var message = stringToInteger(plainText);
        return message.modPow(PUBLIC_EXPONENT, PUBLIC_MODULUS).toByteArray();
    }

    private static BigInteger stringToInteger(String plainText) {
        var bytes = plainText.getBytes();
        return new BigInteger(bytes);
    }

}
