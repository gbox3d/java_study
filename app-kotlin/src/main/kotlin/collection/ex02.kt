package collection

class ex02 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val dict = mutableMapOf(
                "Network" to "network",
                "Thread" to "thread"
            )

            for ((key, value) in dict) {
                println("$key : $value")
            }
        }
    }
}
