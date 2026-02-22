package oop1;

public class ex03 {
    static class BankAccount {
        private int balance;

        BankAccount(int initialBalance) {
            this.balance = initialBalance;
        }

        void deposit(int amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

        boolean withdraw(int amount) {
            if (amount <= 0 || balance < amount) {
                return false;
            }
            balance -= amount;
            return true;
        }

        int getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        account.deposit(500);
        System.out.println("withdraw 300: " + account.withdraw(300));
        System.out.println("withdraw 5000: " + account.withdraw(5000));
        System.out.println("balance: " + account.getBalance());
    }
}
