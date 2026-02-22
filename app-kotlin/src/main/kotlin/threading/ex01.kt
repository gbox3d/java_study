package threading

class ex01 {
    class NumberTask(private val label: String) : Runnable {
        override fun run() {
            for (i in 1..10) {
                println("$label -> $i")
                try {
                    Thread.sleep(30)
                } catch (_: InterruptedException) {
                    Thread.currentThread().interrupt()
                    return
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val t1 = Thread(NumberTask("T1"))
            val t2 = Thread(NumberTask("T2"))

            t1.start()
            t2.start()

            t1.join()
            t2.join()
        }
    }
}
