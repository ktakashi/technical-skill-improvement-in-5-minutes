Thread local variable
=====================

Suppose you have this class and method

```jva
public class MyFormatter {
    public static String format(Object... args) {
        return String.format("Args: %s", args);
    }
}
```
The class became so famous and you can't change the method signature,
but users are asking to make it capable to change the format.

NOTE: The class is used in a multithreading environment as well
