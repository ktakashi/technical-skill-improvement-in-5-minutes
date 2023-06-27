package nl.rabobank.pmt.parot.thespendables.sc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @ParameterizedTest
    @CsvSource({
            "1 + 2 + 3, 6",
            "1 - 2 + 3, 2",
            "10 + 11 - 12 + 13, 22"
    })
    void evaluate(String expr, String ans) throws Exception {
        var in = new ByteArrayInputStream(expr.getBytes());
        var out = new ByteArrayOutputStream();
        Main.evaluate(in, new ByteArrayOutputStream(), out);
        assertEquals(ans, out.toString().trim());
    }
}
