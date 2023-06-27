package io.ktakashi.tsii5m.socket.ec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
    private final String host;
    private final int port;
    private final BufferedReader userIn;
    private final OutputStream userOut;

    public EchoClient(String host, int port, InputStream userIn, OutputStream userOut) {
        this.host = host;
        this.port = port;
        this.userIn = new BufferedReader(new InputStreamReader(userIn));
        this.userOut = userOut;
    }

    public void start() throws IOException {
        try (var socket = new Socket(host, port);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var out = socket.getOutputStream()) {
            var finish = false;
            do {
                prompt(userOut);
                var input = read(userIn);
                write(out, input);
                var response = read(in);
                if (response == null) break; // server connection lost or so
                write(userOut, response + "\n");
                finish = response.equals("end");
            } while (!finish);
        }
    }

    private void prompt(OutputStream out) throws IOException {
        out.write("> ".getBytes());
        out.flush();
    }

    private String read(BufferedReader in) throws IOException {
        return in.readLine();
    }

    private void write(OutputStream out, String input) throws IOException {
        out.write(input.getBytes());
        out.flush();
    }

    public static void main(String[] args) throws IOException {
        var client = new EchoClient("localhost", 9000, System.in, System.out);
        client.start();
    }
}
