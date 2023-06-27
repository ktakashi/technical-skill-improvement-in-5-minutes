package io.ktakashi.tsii5m.socket.es;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        var socket = new Socket("localhost", 9000);
        var in = socket.getInputStream();
        var out = socket.getOutputStream();

        out.write("hello".getBytes());
        System.out.println(new String(in.readAllBytes()));
    }
}
