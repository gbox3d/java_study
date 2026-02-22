package exceptionapi

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class ex03 {
    companion object {
        fun isValidDate(input: String): Boolean {
            return try {
                LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE)
                true
            } catch (_: DateTimeParseException) {
                false
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val input1 = "2026-02-22"
            val input2 = "2026-02-30"
            println("$input1 valid? ${isValidDate(input1)}")
            println("$input2 valid? ${isValidDate(input2)}")
        }
    }
}
