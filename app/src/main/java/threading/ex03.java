package threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ex03 {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            int taskNo = i;
            pool.execute(() -> System.out.println(Thread.currentThread().getName() + " task-" + taskNo));
        }

        pool.shutdown();
        try {
            if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
