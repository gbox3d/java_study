package threading;

public class ex02 {
    static class Account {
        private int balance = 1000;

        public synchronized void withdraw(int amount) {
            if (balance >= amount) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ", balance=" + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " failed, balance=" + balance);
            }
        }

        public int getBalance() {
            return balance;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();

        Runnable task = () -> {
            for (int i = 0; i < 3; i++) {
                account.withdraw(300);
            }
        };

        Thread t1 = new Thread(task, "worker-1");
        Thread t2 = new Thread(task, "worker-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("final balance=" + account.getBalance());
    }
}
