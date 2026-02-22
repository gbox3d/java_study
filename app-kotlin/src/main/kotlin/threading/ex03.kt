package threading

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ex03 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val pool = Executors.newFixedThreadPool(3)

            for (taskNo in 1..10) {
                pool.execute {
                    println("${Thread.currentThread().name} task-$taskNo")
                }
            }

            pool.shutdown()
            if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
                pool.shutdownNow()
            }
        }
    }
}
