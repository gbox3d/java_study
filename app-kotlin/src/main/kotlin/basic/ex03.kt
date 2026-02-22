package basic

class ex03 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val height = 5
            for (i in 0 until height) {
                print(" ".repeat(height - i - 1))
                println("*".repeat(i * 2 + 1))
            }
        }
    }
}
