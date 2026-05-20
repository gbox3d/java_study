package chapter09;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 두 스레드 동시 진행 -- 콘솔 두 줄을 같은 자리에 다시 그려서 두 작업의 진행 상태를 보여 준다.
 *
 * Ex01_01 에서 익힌 "같은 자리에 다시 그리기" 기법을, 이번엔 스레드 두 개로 옮긴다.
 * worker 스레드 둘이 진행바를 한 줄씩 맡아 동시에 차오르고,
 * main 은 두 스레드를 start() 로 띄운 뒤 join() 으로 둘 다 끝나기를 기다린다.
 * 갱신과 렌더를 분리하는 다음 단계는 Ex01_03, 시계 + 사용자 입력은 Ex02_01 에서 다룬다.
 *
 * 핵심 도구:
 *   - new Thread(Runnable).start() : 새 스레드를 띄운다.
 *   - thread.join()                : 그 스레드가 끝날 때까지 호출한 쪽이 기다린다.
 *   - synchronized (lock)          : 두 스레드가 화면을 동시에 그리지 않도록, 한 번에 하나만 들어가게 묶는다.
 *   - "\r" (carriage return) : 커서를 줄의 맨 앞으로 옮긴다. 다음 출력이 같은 줄을 덮어쓴다.
 *   - "\033[2A"              : 커서를 위로 2줄 올린다. 다음 출력이 이전 진행바 2줄을 덮어쓴다.
 *   - "\033[2K"              : 현재 줄을 지운다.
 *   - System.out.flush()      : 버퍼를 즉시 비워 화면에 바로 반영시킨다.
 *   - Thread.sleep(ms)        : 현재 스레드만 잠시 멈춰 진행이 눈에 보이게 한다.
 *
 * 동작 환경: ANSI escape code를 지원하는 콘솔이면 잘 보인다. (Windows Terminal, IntelliJ Terminal 등)
 *
 * Windows / PowerShell 참고:
 *   PowerShell 안에서 "`e[31mRED`e[0m" 같은 ANSI 색상 테스트가 성공해도,
 *   Java처럼 외부 프로그램이 출력한 ANSI 커서 제어 코드("\033[2A", "\033[2K")는
 *   콘솔의 Virtual Terminal Processing 설정이 켜져 있어야 해석된다.
 *
 *   만약 화면에 "\033[2Kbar1 ..." 처럼 ANSI 코드가 문자 그대로 보이면,
 *   아래 명령을 PowerShell에서 실행한 뒤 PowerShell 창을 완전히 닫고 새로 열어 실행한다.
 *
 *   reg add HKCU\Console /v VirtualTerminalLevel /t REG_DWORD /d 1 /f
 */
public class Ex01_02 {


    private static int bar1_filled = 0;
    private static int bar2_filled = 0;
    private static boolean first_render = true;

    // 화면 출력 자물쇠. t1, t2 가 동시에 render_bars() 안으로 들어오면
    // ANSI 커서 이동("\033[2A")과 두 줄 출력이 뒤섞여 화면이 깨진다.
    // 이 객체로 잠가 한 번에 한 스레드만 그리도록 한다.
    private static final Object SCREEN_LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        
        Thread t1 = new Thread(() -> {
            try {
                _worker1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                _worker2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("Done.");
    }

    static void _worker1() throws InterruptedException {
        ThreadLocalRandom rng = ThreadLocalRandom.current(); 
        for (int i = 0; i <= 30; i++) {
            bar1_filled = i;
            Thread.sleep(rng.nextInt(100, 500)); // 0.1~0.5초 사이 무작위로 멈춰 진행이 눈에 보이게 한다.
            render_bars();
        }
    }

    static void _worker2() throws InterruptedException {
        ThreadLocalRandom rng = ThreadLocalRandom.current(); 
        for (int i = 0; i <= 30; i++) {
            bar2_filled = i;
            Thread.sleep(rng.nextInt(100, 500)); // 0.1~0.5초 사이 무작위로 멈춰 진행이 눈에 보이게 한다.
            render_bars();
        }
    }

    //bar1,bar2 를 그리는 메서드. ANSI escape code로 두 줄을 같은 자리에 다시 그린다.
    static void render_bars() {
        // t1, t2 가 동시에 그리지 않도록 잠근다. "커서 2줄 위로 -> 두 줄 출력" 이
        // 통째로 끝나야 화면이 안 깨진다. (락이 없으면 두 스레드의 출력이 뒤섞인다.)
        synchronized (SCREEN_LOCK) {
            String bar1 = "[" + "#".repeat(bar1_filled) + " ".repeat(30 - bar1_filled) + "]";
            String bar2 = "[" + "#".repeat(bar2_filled) + " ".repeat(30 - bar2_filled) + "]";

            if (!first_render) {
                System.out.print("\033[2A"); // 커서를 이전 진행바 2줄 위로 올린다.
            }

            System.out.print("\r\033[2Kbar1 " + bar1 + "\n");
            System.out.print("\r\033[2Kbar2 " + bar2 + "\n");
            System.out.flush(); // 버퍼를 즉시 비워 화면에 바로 반영시킨다.
            first_render = false;
        }
    }
}
