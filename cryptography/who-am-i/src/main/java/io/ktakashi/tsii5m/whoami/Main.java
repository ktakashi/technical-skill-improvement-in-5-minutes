package io.ktakashi.tsii5m.whoami;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {
        var keySpec = new SecretKeySpec("it's my password".getBytes(), "AES");
        var cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        var name = new String(cipher.doFinal(Base64.getDecoder().decode("KU/jedevzAn17dtkbNrBxQ==")));
        System.out.println(name);
    }
}
