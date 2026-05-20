package chapter09;

import java.util.Scanner;

/**
 * 스톱워치 -- 커서 저장·복원(ESC 7 / ESC 8)으로 타이핑 흔들림을 없앤다.
 *
 * Ex02_02 와 거의 같은 스톱워치다. 딱 한 군데, redraw() 만 다르다.
 *
 * Ex02_02 의 문제: timer 가 화면을 다시 그릴 때 커서를 입력 줄 맨 앞으로 되돌린다.
 *   그래서 사용자가 타이핑하는 도중 tick 이 발생하면 친 글자 위에 커서가 겹친다.
 *
 * Ex02_03 의 해결: redraw() 가
 *   1) ESC 7 로 "지금 커서 자리"(= 사용자가 타이핑하던 끝) 를 저장하고
 *   2) 타이머 줄을 다시 그린 뒤
 *   3) ESC 8 로 그 자리로 커서를 복원한다.
 * 터미널이 커서 위치를 기억해 주므로, 프로그램은 사용자가 몇 글자 쳤는지
 * 몰라도 된다 (어차피 nextLine() 전엔 알 수도 없다).
 *
 * 새로 나오는 VT 도구:
 *   - ESC 7 : 커서 위치를 저장 (DECSC)
 *   - ESC 8 : 저장한 위치로 커서 복원 (DECRC)
 *   자바 문자열로는 "\0337" / "\0338" 이다. \033 (ESC) 뒤에 7/8 을 붙인 것인데,
 *   숫자가 붙어 8진수로 헷갈리기 쉬워 아래처럼 이름 있는 상수로 빼 둔다.
 *
 * 명령: start / stop / reset / quit
 *
 * 동작 환경: Ex02_02 와 동일 -- ANSI escape 지원 콘솔 (Windows Terminal, VSCode 터미널 등).
 */
public class Ex02_03 {

    private static volatile boolean running = false;
    private static volatile int elapsedSeconds = 0;
    private static final Object SCREEN_LOCK = new Object();

    // ESC 7 = 커서 위치 저장(DECSC), ESC 8 = 저장한 위치로 복원(DECRC).
    private static final String SAVE_CURSOR    = "\0337";
    private static final String RESTORE_CURSOR = "\0338";

    public static void main(String[] args) throws InterruptedException {
        System.out.println();
        System.out.println("  Stopwatch");
        System.out.println("  commands: start | stop | reset | quit");
        System.out.println();          // timer 줄 자리 잡기 (커서는 이제 입력 줄)
        redraw();                      // timer 줄을 처음 한 번 그린다

        Thread timer = new Thread(Ex02_03::runTimer, "timer");
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
                // Enter 로 커서가 한 줄 내려갔다. 입력 줄 자리로 되돌려 비운 뒤, 새 상태를 즉시 그린다.
                synchronized (SCREEN_LOCK) {
                    System.out.print("\033[1A\r\033[2K");
                    System.out.flush();
                }
                redraw();
            }
        }

        timer.interrupt();
        timer.join();
        System.out.println();
        System.out.println("Bye.");
    }

    /** 타이머 스레드: 1초마다 (running 이면) 카운터를 올리고 timer 줄을 다시 그린다. */
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
            // 종료 신호 -> 빠져나감
        }
    }

    /**
     * timer 줄을 같은 자리에서 다시 그린다. -- Ex02_02 와 딱 이 메서드만 다르다.
     *
     * SAVE_CURSOR 로 지금 커서 자리(사용자가 타이핑하던 끝)를 저장하고,
     * 타이머 줄을 다시 그린 뒤, RESTORE_CURSOR 로 그 자리에 커서를 되돌린다.
     * 그래서 타이핑 도중 tick 이 와도 커서가 친 글자 끝에 그대로 있는다.
     */
    static void redraw() {
        synchronized (SCREEN_LOCK) {
            int mm = elapsedSeconds / 60;
            int ss = elapsedSeconds % 60;
            String state = running ? "RUNNING" : "STOPPED";
            System.out.print(SAVE_CURSOR);                                   // 커서 저장 (입력하던 끝)
            System.out.printf("\033[1A\r\033[2K  timer: %02d:%02d [%s]", mm, ss, state);
            System.out.print(RESTORE_CURSOR);                                // 커서 복원 (입력하던 끝으로)
            System.out.flush();
        }
    }
}
