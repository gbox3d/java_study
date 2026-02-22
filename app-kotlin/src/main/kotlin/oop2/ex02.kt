package oop2

class ex02 {
    open class Animal {
        open fun sound() {
            println("...")
        }
    }

    class Dog : Animal() {
        override fun sound() {
            println("woof")
        }
    }

    class Cat : Animal() {
        override fun sound() {
            println("meow")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val animals = arrayOf(Dog(), Cat())
            animals.forEach { it.sound() }
        }
    }
}
