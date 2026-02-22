package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class ex01 {
    public static void main(String[] args) throws Exception {
        CountDownLatch ready = new CountDownLatch(1);
        int[] portHolder = new int[1];

        Thread receiver = new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket(0)) {
                portHolder[0] = socket.getLocalPort();
                ready.countDown();

                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                System.out.println("udp received: " + msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        receiver.start();
        ready.await();

        try (DatagramSocket sender = new DatagramSocket()) {
            byte[] data = "hello udp".getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(
                    data,
                    data.length,
                    InetAddress.getByName("127.0.0.1"),
                    portHolder[0]
            );
            sender.send(packet);
        }

        receiver.join();
    }
}
