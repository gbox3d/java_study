package modernjava

class ex01 {
    class Box<T> {
        private var value: T? = null

        fun set(value: T) {
            this.value = value
        }

        fun get(): T? = value
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val box = Box<String>()
            box.set("generic value")
            println(box.get())
        }
    }
}
