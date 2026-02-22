package oop2

class ex01 {
    open class Employee(protected val name: String) {
        open fun work() {
            println("$name works")
        }
    }

    class Manager(name: String) : Employee(name) {
        override fun work() {
            println("$name manages team")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val manager: Employee = Manager("Chris")
            manager.work()
        }
    }
}
