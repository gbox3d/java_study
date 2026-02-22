package basic

class ex01 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Hello, Java World! (Kotlin)")
            args.forEachIndexed { index, value ->
                println("args[$index]: $value")
            }
        }
    }
}
