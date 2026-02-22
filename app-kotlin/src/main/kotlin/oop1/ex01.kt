package oop1

class ex01 {
    data class Student(val name: String, val age: Int) {
        fun printInfo() {
            println("name=$name, age=$age")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val student = Student("Alice", 20)
            student.printInfo()
        }
    }
}
