package modernjava

class ex03 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val values = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            val result = values.filter { it % 2 == 0 }.map { it * it }
            println(result)
        }
    }
}
