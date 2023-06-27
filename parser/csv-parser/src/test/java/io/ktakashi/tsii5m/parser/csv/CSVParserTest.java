package io.ktakashi.tsii5m.parser.csv;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.util.Preconditions;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CSVParserTest {

    @ParameterizedTest
    @CsvSource(value = {
            "/simple.csv:aaa:bbb:ccc",
            "/complex.csv:aaa:bb,b:cc\"c:dd\\nd"
    }, delimiter = ':')
    void parse(String file, @AggregateWith(VarargsAggregator.class) String... values) throws Exception {
        var is = getInputStream(file);
        var parser = new CSVParser();
        var result = parser.parse(is);
        assertEquals(1, result.size());
        assertEquals(Arrays.stream(values).map(CSVParserTest::unescape).collect(Collectors.toList()), result.get(0));
    }

    private InputStream getInputStream(String file) {
        return CSVParserTest.class.getResourceAsStream(file);
    }

    private static String unescape(String s) {
        var sb = new StringBuilder(s.length());
        for (var i = 0; i < s.length(); i++) {
            var c = s.charAt(i);
            if (c == '\\') {
                var c2 = s.charAt(i+1);
                if (c2 == 'n') {
                    sb.append('\n');
                    i++;
                    continue;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    static class VarargsAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            Class<?> parameterType = context.getParameter().getType();
            Preconditions.condition(parameterType.isArray(), () -> "must be an array type, but was " + parameterType);
            Class<?> componentType = parameterType.getComponentType();
            return IntStream.range(context.getIndex(), accessor.size())
                    .mapToObj(index -> accessor.get(index, componentType))
                    .toArray(size -> (Object[]) Array.newInstance(componentType, size));
        }
    }
}
