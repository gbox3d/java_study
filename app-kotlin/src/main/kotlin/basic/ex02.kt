package basic

import java.util.Locale

class ex02 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val id = 2026001
            val gpa = 4.18
            println(String.format(Locale.US, "id: %d, gpa: %.2f (cast to int: %d)", id, gpa, gpa.toInt()))
        }
    }
}
