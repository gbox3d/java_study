package network

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.CountDownLatch

class ex02 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ready = CountDownLatch(1)
            val portHolder = IntArray(1)

            val server = Thread {
                ServerSocket(0).use { serverSocket ->
                    portHolder[0] = serverSocket.localPort
                    ready.countDown()

                    serverSocket.accept().use { client ->
                        val reader = BufferedReader(InputStreamReader(client.getInputStream()))
                        val writer = PrintWriter(client.getOutputStream(), true)
                        val msg = reader.readLine()
                        writer.println("echo: $msg")
                    }
                }
            }

            server.start()
            ready.await()

            Socket("127.0.0.1", portHolder[0]).use { socket ->
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                val writer = PrintWriter(socket.getOutputStream(), true)
                writer.println("hello tcp")
                println(reader.readLine())
            }

            server.join()
        }
    }
}
