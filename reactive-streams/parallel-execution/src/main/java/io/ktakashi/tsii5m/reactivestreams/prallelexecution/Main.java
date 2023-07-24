package io.ktakashi.tsii5m.reactivestreams.prallelexecution;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {
    public static void main(String... args) {
        Flux.fromStream(IntStream.range(1, 100).boxed())
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .map(Main::heavyProc)
                .doOnNext(System.out::println)
                .subscribe();
    }

    private static Map.Entry<Integer, Integer> heavyProc(int i) {
        long mask = (1L << 32)-1; // Java's integer has 32 bit length with two's complement
        long v = (long)-i & mask;
        var bi = BigInteger.valueOf(v);
        return Map.entry(i, bi.modPow(BigInteger.valueOf(v), BigInteger.valueOf(i)).intValue());
    }
}
