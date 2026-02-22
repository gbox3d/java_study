package modernjava

class ex02 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val list = mutableListOf("Apple", "Banana", "Cherry")
            list.sortByDescending { it }
            list.forEach { println(it) }
        }
    }
}
