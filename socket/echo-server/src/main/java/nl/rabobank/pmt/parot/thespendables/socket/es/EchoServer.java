package nl.rabobank.pmt.parot.thespendables.socket.es;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public void start(int port) throws IOException {
        System.out.printf("Start server on %d%n", port);
        try (var socket = new ServerSocket(port);
             var client = socket.accept()) {
            while (true) {
                echo(client);
            }
        }
    }

    private void echo(Socket socket) throws IOException {
        var in = socket.getInputStream();
        var out = socket.getOutputStream();
        byte[] bytes = read(in);
        System.out.printf("Received message of length %d%n", bytes.length);
        out.write(bytes);
        out.write("\r\n".getBytes());
        out.flush();
    }

    private byte[] read(InputStream is) throws IOException {
        var baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = is.read(buffer)) == 1024) {
            baos.write(buffer, 0, read);
        }
        // if read is -1, then client connection lost, so handle it somehow
        baos.write(buffer, 0, read);
        return baos.toByteArray();
    }

    public static void main(String... args) throws IOException {
        new EchoServer().start(9000);
    }
}
