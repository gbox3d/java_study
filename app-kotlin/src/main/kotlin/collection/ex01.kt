package collection

class ex01 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val names = mutableListOf("Alice", "Bob", "Charlie")
            names.remove("Bob")
            println("contains Alice: ${names.contains("Alice")}")
            println("list: $names")
        }
    }
}
