package io.ktakashi.tsii5m.sa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String... args) throws IOException {
        var result = aggregate(args[0]);
        System.out.println(result.getKey());
        System.out.println(result.getValue());
    }

    private static Map.Entry<List<String>, String> aggregate(String file) throws IOException {
        var path = Paths.get(file);
        try (var br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())))) {
            var dup = new HashMap<String, Integer>();
            var map =  br.lines().map(Main::split)
                    .peek(entry -> dup.compute(entry.getKey(), (k, v) ->  v == null? 0 : v + 1))
                    .collect(Collectors.groupingBy(Map.Entry::getValue));
            var val = map.keySet().stream().min((a, b) -> Integer.compare(map.get(b).size(), map.get(a).size()));
            return Map.entry(dup.keySet().stream().filter(k -> dup.get(k) > 0).collect(Collectors.toList()), val.get());
        }
    }

    private static Map.Entry<String, String> split(String line) {
        var index = line.indexOf('=');
        return Map.entry(line.substring(0, index), line.substring(index + 1));
    }
}
