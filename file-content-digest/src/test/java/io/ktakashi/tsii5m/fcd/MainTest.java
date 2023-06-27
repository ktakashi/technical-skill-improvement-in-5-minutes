package io.ktakashi.tsii5m.fcd;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @ParameterizedTest(name = "File content digest {0}, {1}")
    @CsvSource(value = {
            "/aaa.txt, MD5, R7zlx09Yn0hn29V+nKn4CA==",
            "/aaa.txt, SHA-1, fiQN50+x7Qj6CNOAY/amqRRiqBU=",
            "/aaa.txt, SHA-256, mDSHbc+wXLFnpcJJU+uljErImxrfV/KPL50JrxB+6PA="
    })
    void main(String filename, String algorithm, String expected) throws Exception {
        String path = MainTest.class.getResource(filename).toURI().getPath();
        assertEquals(expected, Main.fcd(path, algorithm));
    }
}
