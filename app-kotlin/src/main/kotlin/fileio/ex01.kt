package fileio

import java.nio.file.Files
import java.nio.file.Path

class ex01 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val file = Path.of("app-kotlin", "build", "tmp", "week08_ex01.txt")

            try {
                Files.createDirectories(file.parent)
                Files.writeString(file, "Hello, I/O!")
                println(Files.readString(file))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
