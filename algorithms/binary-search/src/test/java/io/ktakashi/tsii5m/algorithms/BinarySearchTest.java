package io.ktakashi.tsii5m.algorithms;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTest {
    private BinarySearch bs = new BinarySearch();
    @ParameterizedTest
    @CsvSource({
            "1 3 4 5 6 8 9 11, 5, 4"
    })
    void testSearch(String input, String element, String expected) {
        var list = Arrays.asList(input.split("\\s"));
        assertEquals(expected, bs.search(list, element));
    }
}
