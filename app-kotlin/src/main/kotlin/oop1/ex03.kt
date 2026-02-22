package oop1

class ex03 {
    class BankAccount(initialBalance: Int) {
        private var balance: Int = initialBalance

        fun deposit(amount: Int) {
            if (amount > 0) {
                balance += amount
            }
        }

        fun withdraw(amount: Int): Boolean {
            if (amount <= 0 || balance < amount) {
                return false
            }
            balance -= amount
            return true
        }

        fun getBalance(): Int = balance
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val account = BankAccount(1000)
            account.deposit(500)
            println("withdraw 300: ${account.withdraw(300)}")
            println("withdraw 5000: ${account.withdraw(5000)}")
            println("balance: ${account.getBalance()}")
        }
    }
}
