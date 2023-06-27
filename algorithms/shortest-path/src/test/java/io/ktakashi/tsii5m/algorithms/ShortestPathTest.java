package io.ktakashi.tsii5m.algorithms;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathTest {
    private final ShortestPath testSubject = new ShortestPath();

    @ParameterizedTest
    @CsvSource(value = {
            "/path1.txt, 1 2 4 7",
            "/path2.txt, 1 4 6 7",
            "/path3.txt, 1 2 4 7"
    })
    public void resolve(String file, String expected) throws Exception {
        var list = Arrays.asList(expected.trim().split("\\s+"));
        try (var is = ShortestPathTest.class.getResourceAsStream(file)) {
            var result = testSubject.resolve(is);
            assertEquals(list, result);
        }
    }
}
