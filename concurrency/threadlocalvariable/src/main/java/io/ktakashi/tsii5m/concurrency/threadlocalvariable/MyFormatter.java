package io.ktakashi.tsii5m.concurrency.threadlocalvariable;

public class MyFormatter {
    public static final ThreadLocal<String> FORMAT_STRING = ThreadLocal.withInitial(() -> "Args: %s");
    public static String format(Object... args) {
        return String.format(FORMAT_STRING.get(), args);
    }
}
