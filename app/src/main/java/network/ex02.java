package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class ex02 {
    public static void main(String[] args) throws Exception {
        CountDownLatch ready = new CountDownLatch(1);
        int[] portHolder = new int[1];

        Thread server = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(0)) {
                portHolder[0] = serverSocket.getLocalPort();
                ready.countDown();

                try (Socket client = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                    String msg = in.readLine();
                    out.println("echo: " + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        server.start();
        ready.await();

        try (Socket socket = new Socket("127.0.0.1", portHolder[0]);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("hello tcp");
            System.out.println(in.readLine());
        }

        server.join();
    }
}
