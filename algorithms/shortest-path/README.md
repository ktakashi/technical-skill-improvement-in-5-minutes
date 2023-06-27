Shortest Path
=============

Search the shortest path to the goal. You'll get line-oriented
input, the first line is the starting point, the second line is
the goal, the rest of the lines are from-to edges. 

Below is an example:

```
1
7
1 2
2 3
2 4
3 5
3 6
4 6
4 7
5 8
6 8
8 7
```

The answer to the above input is

```
1 2 4 7
```

What would be the shortest path of the below?

```
1
7
1 2
1 4
2 3
2 4
3 5
4 6
5 6
6 7
```
```
1
7
1 2
1 3
2 3
2 4
3 5
4 7
5 4
5 6
6 4
```

Hint: 
BFS, breadth first search: https://en.wikipedia.org/wiki/Breadth-first_search
