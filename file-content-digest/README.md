File content digest
===================

Write a program which accepts 2 arguments, file and digest algorithm.
It should display base 64 encoded digest of the file content.

Examples
--------

File1
```
aaa
```
| MD5                       | SHA-1                         | SHA-256                                       |
|---------------------------|-------------------------------|-----------------------------------------------|
| R7zlx09Yn0hn29V+nKn4CA==  | fiQN50+x7Qj6CNOAY/amqRRiqBU=  | mDSHbc+wXLFnpcJJU+uljErImxrfV/KPL50JrxB+6PA=  |

File2
```
lorem ipsum
```
| MD5                      | SHA-1                        | SHA-256                                       |
|--------------------------|------------------------------|-----------------------------------------------|
| gKdR/eV3AoZAxBkADjPrpg== | v7d1mmfa62VBBJC02Yu52n0eos4= | Xiv1fT9AxLbfadrxk2y3ZvgyN0tPwCWafL/wbi9w8mk=  |

NOTE: Both files don't have line feed at the end of the lines.
