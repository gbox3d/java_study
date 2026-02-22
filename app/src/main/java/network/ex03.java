package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ex03 {
    private static final int PORT = 8888;
    private static final List<PrintWriter> CLIENTS = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        System.out.println("chat server template (port " + PORT + ")");
        System.out.println("use startServer() to run a simple multi-client chat server");
    }

    static void startServer() {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();
                pool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    static void broadcast(String message) {
        synchronized (CLIENTS) {
            for (PrintWriter writer : CLIENTS) {
                writer.println(message);
            }
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter out;
        private String name;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                out = new PrintWriter(socket.getOutputStream(), true);
                name = in.readLine();
                CLIENTS.add(out);
                broadcast("[" + name + "] joined");

                String message;
                while ((message = in.readLine()) != null) {
                    broadcast("[" + name + "]: " + message);
                }
            } catch (IOException e) {
                System.out.println("connection closed: " + name);
            } finally {
                if (out != null) {
                    CLIENTS.remove(out);
                }
                broadcast("[" + name + "] left");
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
