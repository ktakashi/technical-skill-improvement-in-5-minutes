Parallel execution
==================

You have the below method (does basically nothing)

```java
private static Map.Entry<Integer, Integer> heavyProc(int i) {
    long mask = (1L << 32)-1; // Java's integer has 32 bit length with two's complement
    long v = (long)-i & mask;
    var bi = BigInteger.valueOf(v);
    return Map.entry(i, bi.modPow(bi, BigInteger.valueOf(i)).intValue());
}
```
Use `Flux` to compute the `heavyProc` of `1..100` in parallel.
