package arraymethod

class ex02 {
    companion object {
        fun sum(vararg numbers: Int): Int = numbers.sum()

        @JvmStatic
        fun main(args: Array<String>) {
            println("sum(1, 2, 3, 4, 5) = ${sum(1, 2, 3, 4, 5)}")
        }
    }
}
