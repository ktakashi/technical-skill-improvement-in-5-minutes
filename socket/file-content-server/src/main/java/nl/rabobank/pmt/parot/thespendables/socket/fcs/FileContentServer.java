package nl.rabobank.pmt.parot.thespendables.socket.fcs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;

public class FileContentServer {
    private final int port;

    public FileContentServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket ss = new ServerSocket(port)) {
            while (true) {
                try (var client = ss.accept()) {
                    response(client);
                }
            }
        }
    }

    private void response(Socket client) throws IOException {
        try (var br = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            var path = br.readLine();
            var file = Paths.get(path).toFile();
            if (file.exists()) {
                try (var fis = new FileInputStream(file)) {
                    fis.transferTo(client.getOutputStream());
                }
            } else {
                client.getOutputStream().write("404 not found\n".getBytes());
            }
        }
    }

    public static void main(String... args) throws IOException {
        new FileContentServer(9000).start();
    }
}
