Textbook RSA
============

Implement RSA encryption/decryption.

Encryption: m^e ≡ c (mod n)
Decryption: c^d ≡ (m^e)^d ≡ m (mod n)

Where:

m = message (m < n)
e = public exponent
n = public modulus (a.k.a public key)
d = private exponent (a.k.a private key)

For more details: https://en.wikipedia.org/wiki/RSA_(cryptosystem)

Those are example variables:

```
Public modulus (public key): 0xc96b4ef098c0fcdf55a50ab8aa86a5873dc88375e108a100035c35cb8de279d8e639452a31bb616d7fffe5112f682a370496390137141148c9bebde87720693c854ebc6d680da569d89c0f95664750e1babd3ffabcba704f161c2945bb1cf1c22bdd24d713b35cacfca9ddcf5c6b3530880bd9c656c150d8db2ef8922fbfba95
Private exponent (private key): 0xa8f55217b31b9843c3f51924452f83a509e7ac96f279298cf8449e7facfa80d9b86dec1df3efca4f6f62fa054b6e2693564ea96ce285fbe5c20e3601ce042b2173411d82fe296868e9f7b771441247098519b316d123d693a54ef0ef58044bc1d97f1d447c551efa52b2a7a2f68e200cc1fefe2aedafd7960d078b2c92daa295
Public exponent: 0x10001
```
```
Message = 'test message'
Cipher Text = '0xc5259948cc7917403935c32c0503d4a915c0bae3500e9cc1bdcb2002f907bf393f370f2e15b247e57d912c0ef4bf1d881df3f4794f116e436298c22e1855c134b667c0f448910494d3a8d836819e67e0d4ba6085b023b4af4ca3f2c6313f426835d2a9c12b179feba1fd15f147095221c92e1a5f374a593be1bf903be02efbde'
```
