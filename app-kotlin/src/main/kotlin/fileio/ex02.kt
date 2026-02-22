package fileio

import java.nio.file.Files
import java.nio.file.Path

class ex02 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val file = Path.of("app-kotlin", "build", "tmp", "week08_ex02.txt")

            try {
                Files.createDirectories(file.parent)
                Files.newBufferedWriter(file).use { writer ->
                    writer.write("line1")
                    writer.newLine()
                    writer.write("line2")
                }

                Files.newBufferedReader(file).useLines { lines ->
                    lines.forEach { println("read: $it") }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
