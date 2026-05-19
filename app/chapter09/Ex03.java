package chapter09;

import java.util.Scanner;

/**
 * 스톱워치(타이머) — 두 스레드가 공유 상태를 두고 협력하는 패턴.
 *
 * 화면 갱신은 Ex01/Ex02 와 같은 \r 패턴만 사용 (ANSI 미사용).
 * 매초 timer 스레드가 현재 줄을 "\r  [MM:SS STATE] > " 로 다시 그린다.
 * 사용자가 Enter 를 누르면 커서가 한 줄 내려가고, 그때부터 timer 가 새 줄에서 갱신을 이어간다.
 * 결과적으로 명령을 한 번 누를 때마다 이전 상태가 한 줄씩 "히스토리"로 남는다.
 *
 * 화면 모양 (예):
 *   Stopwatch -- commands: start | stop | reset | quit
 *     [00:00 STOPPED] > start
 *     [00:02 RUNNING] > stop
 *     [00:02 STOPPED] > _            <-- 매초 \r 로 같은 자리 갱신
 *
 * 핵심 학습 포인트:
 *   - volatile boolean / int               : 두 스레드가 공유하는 상태의 가시성(visibility) 보장
 *   - Scanner.nextLine()                   : main 만 멈춘다. timer 스레드는 계속 돈다
 *   - timer.interrupt() → timer.join()     : 다른 스레드를 깔끔하게 정리하는 표준 절차
 *
 * 명령: start / stop / reset / quit
 *
 * 시각적 제약: timer 가 매초 \r 로 같은 줄을 덮어쓰므로, 사용자가 타이핑 중이면
 *   직전 글자가 잠시 묻혔다 다시 보이는 듯한 흔들림이 생길 수 있다.
 *   Scanner 는 stdin 버퍼에서 정확히 읽으므로 명령 처리 자체는 정상.
 *
 * 동작 환경: Ex01/Ex02 와 동일 — 어떤 콘솔에서도 잘 보인다 (ANSI 미사용).
 */
public class Ex03 {

    private static volatile boolean running = false;
    private static volatile int elapsedSeconds = 0;
    private static final Object SCREEN_LOCK = new Object();      // Ex02 의 SCREEN_LOCK 과 같은 역할

    public static void main(String[] args) throws InterruptedException {
        System.out.println();
        System.out.println("  Stopwatch -- commands: start | stop | reset | quit");

        Thread timer = new Thread(Ex03::runTimer, "timer");
        timer.start();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                if (!scanner.hasNextLine()) break;
                String cmd = scanner.nextLine().trim().toLowerCase();
                if ("quit".equals(cmd)) break;
                switch (cmd) {
                    case "start":  running = true;      break;
                    case "stop":   running = false;     break;
                    case "reset":  elapsedSeconds = 0;  break;
                    default:       /* 알 수 없는 명령은 무시 */
                }
                // Enter 직후 커서가 새 줄로 내려갔으므로, 그 새 줄에 곧바로 새 상태를 그려 준다.
                // (다음 timer tick 까지 최대 1초 빈 줄이 보이는 걸 막기 위함)
                redraw();
            }
        }

        timer.interrupt();
        timer.join();
        System.out.println();
        System.out.println("Bye.");
    }

    /** 타이머 스레드: 1초마다 (running 이면) 카운터를 올리고 현재 줄을 다시 그린다. */
    static void runTimer() {
        try {
            while (true) {
                if (running) {
                    elapsedSeconds++;
                }
                redraw();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // 종료 신호 → 빠져나감
        }
    }

    /** 현재 줄을 "\r  [MM:SS STATE] > " 로 다시 그린다. \r 만 사용 (Ex01/Ex02 와 같은 패턴). */
    static void redraw() {
        synchronized (SCREEN_LOCK) {
            int mm = elapsedSeconds / 60;
            int ss = elapsedSeconds % 60;
            String state = running ? "RUNNING" : "STOPPED";
            System.out.printf("\r  [%02d:%02d %s] > ", mm, ss, state);
            System.out.flush();
        }
    }
}
