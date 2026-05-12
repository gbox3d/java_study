package chapter09;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 스레드 두 개를 동시에 실행하고 진행바로 시각화한다.
 *
 * 이번 주차의 시작 예제이므로 다루는 개념은 최소로 둔다:
 *   - new Thread(Runnable, name).start()  : 새 스레드 시작
 *   - thread.join()                        : 해당 스레드가 끝날 때까지 호출한 쪽이 기다림
 *   - 두 스레드가 진짜로 동시에 진행되는 모습이 두 줄 진행바의 동시 차오름으로 보인다
 *   - 매 실행마다 두 워커의 평균 속도가 무작위로 정해져, 누가 먼저 끝날지 매번 달라진다
 *
 * 진행률(progress)은 평범한 int 배열을 쓴다. 이번 예제는 "스레드가 동시에 돈다"는
 * 사실 자체에 집중하기 위함이다. 가시성 / 원자성 / 락은 Ex02 (synchronized),
 * Ex04 (AtomicInteger) 에서 단계적으로 다룬다.
 *
 * 출력은 영문 ASCII만 사용 (Windows 콘솔 인코딩에 영향을 안 받게).
 *
 * 동작 환경: PowerShell 7, Windows Terminal, VSCode 통합 터미널.
 */
public class Ex02 {
    private static final int TRACK_WIDTH = 30;
    private static final int[] progress = new int[2];     // [0] = worker-A, [1] = worker-B
    private static final Object SCREEN_LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        // 평균 한 걸음 슬립(ms)을 무작위로 정한다. 값이 작을수록 빠른 워커.
        int avgSleepA = 20 + ThreadLocalRandom.current().nextInt(60);
        int avgSleepB = 20 + ThreadLocalRandom.current().nextInt(60);

        System.out.println();
        System.out.println("  Two threads in parallel  (avgSleep: A=" + avgSleepA + "ms, B=" + avgSleepB + "ms)");
        System.out.println();
        System.out.println();   // worker-A 자리
        System.out.println();   // worker-B 자리

        Thread tA = new Thread(() -> runWorker(0, avgSleepA), "worker-A");
        Thread tB = new Thread(() -> runWorker(1, avgSleepB), "worker-B");

        tA.start();
        tB.start();

        // join() : 두 스레드가 모두 끝날 때까지 main 이 기다린다.
        // 이 두 줄을 빼면 main 이 먼저 끝나 "Both finished" 메시지가
        // 진행바 한가운데 끼어드는 모습을 볼 수 있다.
        tA.join();
        tB.join();

        System.out.println();
        System.out.println("  Both finished.");
    }

    /** id 번 워커가 0 -> 100% 까지 진행. */
    static void runWorker(int id, int avgSleep) {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        while (progress[id] < 100) {
            progress[id] = Math.min(100, progress[id] + 1 + rng.nextInt(4));   // 1~4% 진행
            redraw();
            try {
                int jitter = rng.nextInt(30) - 10;                              // -10 ~ +19ms 흔들림
                Thread.sleep(Math.max(5, avgSleep + jitter));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /** 두 줄짜리 진행바를 같은 자리에 다시 그린다. SCREEN_LOCK 으로 출력 충돌 방지. */
    static void redraw() {
        synchronized (SCREEN_LOCK) {
            System.out.print("\033[2A");                       // 커서 2줄 위로
            for (int i = 0; i < 2; i++) {
                int pct    = progress[i];
                int filled = pct * TRACK_WIDTH / 100;
                StringBuilder bar = new StringBuilder(TRACK_WIDTH + 2);
                bar.append('|');
                for (int j = 0; j < TRACK_WIDTH; j++) {
                    bar.append(j < filled ? '=' : ' ');
                }
                bar.append('|');
                String label = (i == 0) ? "A" : "B";
                // \033[K : 줄 끝까지 지우기 (이전 잔상 제거)
                System.out.printf("\033[K  worker-%s %s %3d%%%n", label, bar, pct);
            }
            System.out.flush();
        }
    }
}
