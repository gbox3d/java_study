package threading;

public class ex01 {
    static class NumberTask implements Runnable {
        private final String label;

        NumberTask(String label) {
            this.label = label;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                System.out.println(label + " -> " + i);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new NumberTask("T1"));
        Thread t2 = new Thread(new NumberTask("T2"));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
