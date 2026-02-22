package threading

class ex02 {
    class Account {
        private var balance = 1000

        @Synchronized
        fun withdraw(amount: Int) {
            if (balance >= amount) {
                try {
                    Thread.sleep(50)
                } catch (_: InterruptedException) {
                    Thread.currentThread().interrupt()
                    return
                }
                balance -= amount
                println("${Thread.currentThread().name} withdrew $amount, balance=$balance")
            } else {
                println("${Thread.currentThread().name} failed, balance=$balance")
            }
        }

        fun getBalance(): Int = balance
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val account = Account()
            val task = Runnable {
                repeat(3) {
                    account.withdraw(300)
                }
            }

            val t1 = Thread(task, "worker-1")
            val t2 = Thread(task, "worker-2")

            t1.start()
            t2.start()

            t1.join()
            t2.join()

            println("final balance=${account.getBalance()}")
        }
    }
}
