package chapter09;

import java.util.Scanner;

/**
 * 타이머 + VT 화면 제어의 첫걸음 -- 타이머가 한 줄을 같은 자리에서 갱신한다.
 *
 * 쓰레드 자체는 Ex01_xx 에서 이미 익혔다. Ex02_xx 부터는 그 쓰레드 위에
 * ANSI(VT) 터미널 제어로 "화면을 꾸미는" 데 초점을 둔다. 이 예제가 그 시작이다.
 *
 * 동작:
 *   - timer 스레드가 1초마다 경과 시간을 같은 줄에 다시 그린다.
 *   - main 은 Enter 입력을 기다리며 멈춰 있다. Enter 를 누르면 종료한다.
 *   - main 이 입력 대기로 멈춰 있는 동안에도 timer 는 계속 도는 것 -- 그게 스레드다.
 *
 * VT 도구 (이 예제의 주인공):
 *   - "\r"      : 커서를 줄 맨 앞으로 옮긴다
 *   - "\033[2K" : 커서가 있는 줄을 통째로 지운다
 *   이 둘로 "같은 줄을 지우고 다시 쓰기" 를 한다.
 *
 * 동작 환경: ANSI escape 지원 콘솔 (Windows Terminal, VSCode 터미널 등).
 *   PowerShell 에서 ANSI 가 글자 그대로 보이면 Ex01_01.java 주석의 reg add 안내 참고.
 */
public class Ex02_01 {

    // timer 스레드 혼자만 쓰고 읽는다 -- 다른 스레드와 공유하지 않으므로 volatile 이 필요 없다.
    private static int elapsedSeconds = 0;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("  Timer  (press Enter to quit)");
        System.out.println();   // timer 줄 자리

        Thread timer = new Thread(Ex02_01::runTimer, "timer");
        timer.start();

        // main 은 여기서 Enter(한 줄 입력)가 들어올 때까지 멈춘다.
        // 그 동안에도 timer 스레드는 자기 일(1초마다 갱신)을 계속한다.
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
        }

        // Enter 가 들어왔다 -> 표준 절차로 timer 를 멈춘다: interrupt 로 신호, join 으로 대기.
        timer.interrupt();
        timer.join();
        System.out.println("\nBye.");
    }

    /** timer 스레드: 1초마다 경과 시간을 다시 그리고, 1초 기다리고, 카운터를 올린다. */
    static void runTimer() {
        try {
            while (true) {
                redraw();              // 지금까지의 경과 시간을 그린다
                Thread.sleep(1000);    // 1초 기다린다
                elapsedSeconds++;      // 1초가 지났다
            }
        } catch (InterruptedException e) {
            // interrupt 신호를 받으면 sleep 이 깨어나며 여기로 온다 -> 루프 종료.
        }
    }

    /** 경과 시간을 같은 줄에 다시 그린다. \r 로 줄 앞으로 가서 \033[2K 로 지우고 새로 쓴다. */
    static void redraw() {
        int mm = elapsedSeconds / 60;
        int ss = elapsedSeconds % 60;
        System.out.printf("\r\033[2K  elapsed: %02d:%02d", mm, ss);
        // System.out.printf(" elapsed: %02d:%02d  ", mm, ss); // ANSI escape 를 안쓴 경우
        System.out.flush();
    }
}
