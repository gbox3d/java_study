package exceptionapi

class ex01 {
    companion object {
        fun divide(a: Int, b: Int): Int = a / b

        @JvmStatic
        fun main(args: Array<String>) {
            try {
                println(divide(10, 0))
            } catch (e: ArithmeticException) {
                println("error: ${e.message}")
            } finally {
                println("finally block executed")
            }
        }
    }
}
