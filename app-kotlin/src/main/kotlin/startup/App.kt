package startup

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class App {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val now = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            println("Current time: ${now.format(formatter)}")
        }
    }
}
