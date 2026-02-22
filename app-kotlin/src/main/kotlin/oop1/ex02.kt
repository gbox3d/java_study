package oop1

class ex02 {
    class Student(val name: String, val age: Int) {
        constructor() : this("Unknown", 20)
        override fun toString(): String = "Student{name='$name', age=$age}"
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(Student())
            println(Student("Bob", 22))
        }
    }
}
