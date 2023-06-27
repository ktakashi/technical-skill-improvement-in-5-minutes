Binary search
=============

Problem
-------

You receive a sorted list, whose elements are comparable 
(e.g. integer, string, etc.). You need to implement a method 
which returns one smaller than the given element.

```
Argument1: [1 3 4 5 6 8 9 11]
Argument2: 5
Return: 4
Method signature: public <T extends Comparable<T>> T search(List<T> list, T element);
```

Consider implementing this with O(log n) order. (so don't do linear search)
