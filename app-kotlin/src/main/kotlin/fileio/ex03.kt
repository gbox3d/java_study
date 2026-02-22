package fileio

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.nio.file.Files
import java.nio.file.Path

class ex03 {
    data class User(val id: String, @Transient val password: String?) : Serializable

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val file = Path.of("app-kotlin", "build", "tmp", "user.dat")

            try {
                Files.createDirectories(file.parent)
                val saved = User("alice", "secret123")

                ObjectOutputStream(FileOutputStream(file.toFile())).use { out ->
                    out.writeObject(saved)
                }

                ObjectInputStream(FileInputStream(file.toFile())).use { input ->
                    val loaded = input.readObject() as User
                    println("saved:  $saved")
                    println("loaded: $loaded")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
