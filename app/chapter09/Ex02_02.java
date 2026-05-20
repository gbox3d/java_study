package chapter09;

import java.util.Scanner;

/**
 * 스톱워치(타이머) -- ANSI escape 로 타이머 줄을 같은 자리에서 갱신한다.
 *
 * Ex02_01 의 타이머에 사용자 명령(start/stop/reset)을 더한 것이다.
 * Ex01_xx 에서 익힌 ANSI 커서 제어를, 이번엔 "사용자 입력과 함께" 쓴다.
 * 화면은 위에서부터 이렇게 쌓인다:
 *   Stopwatch                              <- 한 번만 출력 (고정)
 *   commands: start | stop | reset | quit  <- 한 번만 출력 (고정)
 *   timer: MM:SS [STATE]                   <- 매초 timer 스레드가 같은 자리에서 갱신
 *   (입력 줄)                              <- 사용자가 명령을 친다
 *
 * timer 줄은 입력 줄 바로 위에 있다. redraw() 는 \033[1A 로 한 줄 위(timer 줄)로
 * 올라가 줄을 지우고 다시 쓴 뒤, 커서를 입력 줄로 되돌린다.
 * main 은 명령을 처리한 뒤 \033[1A\r\033[2K 로 커서를 입력 줄 자리에 다시 맞춘다
 * (Enter 로 커서가 한 줄 내려갔으므로).
 *
 * 핵심 학습 포인트:
 *   - volatile boolean / int            : 두 스레드가 공유하는 상태의 가시성(visibility) 보장
 *   - Scanner.nextLine()                : main 만 멈춘다. timer 스레드는 계속 돈다
 *   - timer.interrupt() -> timer.join() : 다른 스레드를 깔끔하게 정리하는 표준 절차
 *   - ANSI \033[1A / \033[2K            : 커서를 위로 올리고 줄을 지워 같은 자리에 다시 그리기
 *
 * 명령: start / stop / reset / quit
 *
 * 시각적 제약 (다음 예제 Ex02_03 에서 해결): 사용자가 입력 줄에서 타이핑하는 도중
 *   timer tick 이 발생하면, redraw() 가 커서를 입력 줄 맨 앞으로 되돌리므로 친 글자
 *   위에 커서가 겹친다. Scanner 는 stdin 버퍼에서 정확히 읽으니 명령 처리는 정상이지만
 *   보기에 어색하다. 이 흔들림을 커서 저장·복원(ESC 7 / ESC 8)으로 없앤 것이 Ex02_03.
 *
 * 동작 환경: Ex01_xx 와 동일 -- ANSI escape 지원 콘솔 (Windows Terminal, VSCode 터미널 등).
 *   PowerShell 에서 ANSI 코드가 글자 그대로 보이면 Ex01_01.java 주석의 reg add 안내를 따른다.
 */
public class Ex02_02 {

    private static volatile boolean running = false;
    private static volatile int elapsedSeconds = 0;
    private static final Object SCREEN_LOCK = new Object();      // Ex01_02 의 SCREEN_LOCK 과 같은 역할

    public static void main(String[] args) throws InterruptedException {
        System.out.println();
        System.out.println("  Stopwatch");
        System.out.println("  commands: start | stop | reset | quit");
        System.out.println();          // timer 줄 자리 잡기 (커서는 이제 입력 줄)
        redraw();                      // timer 줄을 처음 한 번 그린다

        Thread timer = new Thread(Ex02_02::runTimer, "timer");
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
     * timer 줄을 같은 자리에서 다시 그린다.
     * \033[1A 로 한 줄 위(timer 줄)로 가서 \033[2K 로 줄을 지우고 새로 쓴 뒤,
     * %n 으로 커서를 다시 입력 줄(맨 앞)로 되돌린다.
     */
    static void redraw() {
        synchronized (SCREEN_LOCK) {
            int mm = elapsedSeconds / 60;
            int ss = elapsedSeconds % 60;
            String state = running ? "RUNNING" : "STOPPED";
            System.out.printf("\033[1A\r\033[2K  timer: %02d:%02d [%s]%n", mm, ss, state);
            System.out.flush();
        }
    }
}
