Simple aggregator
=================

You get a key and value pair formatted file of the following format.

```
key0=value0
key1=value1
  :
  : so on
```

We want to know which key(s) are duplicated, and which value exists 
the most frequently.

For example, given the following input

```
Bob=sucks
Bob=scrum bastard
Jim=half a century
Jun=young
Win=cheater
Petar=bang meister
CB=sucks
```

Then the duplicated key is `Bob` and the most frequently appeared 
value is `sucks`
