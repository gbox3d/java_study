package collection

class ex03 {
    data class Student(val id: Int, val name: String, val score: Int)

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val students = mutableListOf(
                Student(3, "Kim", 90),
                Student(1, "Lee", 95),
                Student(2, "Park", 95)
            )

            val sorted = students.sortedWith(compareByDescending<Student> { it.score }.thenBy { it.id })
            sorted.forEach { println(it) }
        }
    }
}
