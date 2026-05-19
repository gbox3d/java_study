package chapter09;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 스레드 두 개를 동시에 실행하고 진행바로 시각화한다.
 *
 * Ex01 의 \r 갱신 패턴을 그대로 가져와, 두 워커의 진행률을 한 줄에 나란히 표시한다.
 * 두 부분 진행바가 동시에 차오르는 모습으로 "병렬 실행" 을 본다.
 *
 * 핵심 학습 포인트:
 *   - new Thread(Runnable, name).start()  : 새 스레드 시작
 *   - thread.join()                        : 해당 스레드가 끝날 때까지 호출한 쪽이 기다림
 *   - 두 스레드가 동시에 진행되는 모습이 한 줄 안의 두 진행바 차오름으로 보인다
 *   - 매 실행마다 두 워커의 평균 속도가 무작위로 정해져, 누가 먼저 끝날지 매번 달라진다
 *
 * 동작 환경: Ex01 과 동일 — \r 만 사용 (ANSI 미사용). 어떤 콘솔에서도 잘 보인다.
 */
public class Ex02 {
    private static final int TRACK_WIDTH = 20;
    private static final int[] progress = new int[2];     // [0] = worker-A, [1] = worker-B
    private static final Object SCREEN_LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        // 평균 한 걸음 슬립(ms)을 무작위로 정한다. 값이 작을수록 빠른 워커.
        int avgSleepA = 20 + ThreadLocalRandom.current().nextInt(60);
        int avgSleepB = 20 + ThreadLocalRandom.current().nextInt(60);

        System.out.println();
        System.out.println("  Two threads in parallel  (avgSleep: A=" + avgSleepA + "ms, B=" + avgSleepB + "ms)");

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

    /** 두 워커의 진행바를 한 줄에 같이 그린다. \r 만 사용 (Ex01 과 같은 패턴). */
    static void redraw() {
        synchronized (SCREEN_LOCK) {
            StringBuilder sb = new StringBuilder();
            sb.append('\r');
            sb.append("  A:").append(makeBar(progress[0]))
              .append(String.format(" %3d%%", progress[0]));
            sb.append("   B:").append(makeBar(progress[1]))
              .append(String.format(" %3d%%", progress[1]));
            System.out.print(sb);
            System.out.flush();
        }
    }

    /** "|====    |" 모양의 진행바 문자열을 만든다. */
    static String makeBar(int pct) {
        int filled = pct * TRACK_WIDTH / 100;
        StringBuilder bar = new StringBuilder(TRACK_WIDTH + 2);
        bar.append('|');
        for (int j = 0; j < TRACK_WIDTH; j++) {
            bar.append(j < filled ? '=' : ' ');
        }
        bar.append('|');
        return bar.toString();
    }
}
