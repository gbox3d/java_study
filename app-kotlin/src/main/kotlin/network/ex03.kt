package network

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.Collections
import java.util.concurrent.Executors

class ex03 {
    companion object {
        private const val PORT = 8888
        private val clients = Collections.synchronizedList(mutableListOf<PrintWriter>())

        @JvmStatic
        fun main(args: Array<String>) {
            println("chat server template (port $PORT)")
            println("use startServer() to run a simple multi-client chat server")
        }

        fun startServer() {
            val pool = Executors.newFixedThreadPool(10)
            try {
                ServerSocket(PORT).use { serverSocket ->
                    while (!Thread.currentThread().isInterrupted) {
                        val socket = serverSocket.accept()
                        pool.execute(ClientHandler(socket))
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                pool.shutdown()
            }
        }

        private fun broadcast(message: String) {
            synchronized(clients) {
                clients.forEach { it.println(message) }
            }
        }

        private class ClientHandler(private val socket: Socket) : Runnable {
            private var out: PrintWriter? = null
            private var name: String = "anonymous"

            override fun run() {
                try {
                    BufferedReader(InputStreamReader(socket.getInputStream())).use { reader ->
                        out = PrintWriter(socket.getOutputStream(), true)
                        name = reader.readLine() ?: "anonymous"
                        clients.add(out)
                        broadcast("[$name] joined")

                        while (true) {
                            val message = reader.readLine() ?: break
                            broadcast("[$name]: $message")
                        }
                    }
                } catch (_: IOException) {
                    println("connection closed: $name")
                } finally {
                    out?.let { clients.remove(it) }
                    broadcast("[$name] left")
                    try {
                        socket.close()
                    } catch (_: IOException) {
                    }
                }
            }
        }
    }
}
