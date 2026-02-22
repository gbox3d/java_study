package network

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.charset.StandardCharsets
import java.util.concurrent.CountDownLatch

class ex01 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ready = CountDownLatch(1)
            val portHolder = IntArray(1)

            val receiver = Thread {
                DatagramSocket(0).use { socket ->
                    portHolder[0] = socket.localPort
                    ready.countDown()

                    val buffer = ByteArray(1024)
                    val packet = DatagramPacket(buffer, buffer.size)
                    socket.receive(packet)

                    val msg = String(packet.data, 0, packet.length, StandardCharsets.UTF_8)
                    println("udp received: $msg")
                }
            }

            receiver.start()
            ready.await()

            DatagramSocket().use { sender ->
                val data = "hello udp".toByteArray(StandardCharsets.UTF_8)
                val packet = DatagramPacket(data, data.size, InetAddress.getByName("127.0.0.1"), portHolder[0])
                sender.send(packet)
            }

            receiver.join()
        }
    }
}
