package chapter09;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 진행바 기본 틀 -- 콘솔 두 줄을 같은 자리에 다시 그려서 진행 상태를 보여 준다.
 *
 * 이 예제에는 아직 추가 스레드가 없다. main 하나가 두 트랙을 차례로 채워간다.
 * "콘솔에서 같은 자리에 다시 그리기" 기법을 먼저 익혀 두는 사전 예제이며,
 * 스레드 자체는 Ex01_02 (두 스레드 동시 진행) · Ex01_03 (갱신/렌더 분리) · Ex02_01 (스톱워치) 에서 다룬다.
 *
 * 핵심 도구:
 *   - "\r" (carriage return) : 커서를 줄의 맨 앞으로 옮긴다. 다음 출력이 같은 줄을 덮어쓴다.
 *   - "\033[2A"              : 커서를 위로 2줄 올린다. 다음 출력이 이전 진행바 2줄을 덮어쓴다.
 *   - "\033[2K"              : 현재 줄을 지운다.
 *   - System.out.flush()      : 버퍼를 즉시 비워 화면에 바로 반영시킨다.
 *   - Thread.sleep(ms)        : 잠시 멈춰 진행이 눈에 보이게 한다.
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
public class Ex01_01 {


    private static int bar1_filled = 0;
    private static int bar2_filled = 0;
    private static boolean first_render = true;
    
    public static void main(String[] args) throws InterruptedException {

        /* 
        무작위 값 생성기. `new Random()` 대신 쓰는 이유는 
        ① 스레드마다 별도의 난수 상태를 가져 빠르고 안전하며,
        ② 사용법이 짧다 (`rng.nextInt(4)` 는 0~3 사이 정수). Ex01_02 처럼 여러 스레드가 각자 난수를 쓸 때 특히 유용하다.
        */  
        ThreadLocalRandom rng = ThreadLocalRandom.current(); 
        
        
        for (int i = 0; i <= 30; i++) {
            bar1_filled = i;
            Thread.sleep(rng.nextInt(100, 500)); // 0.1~0.5초 사이 무작위로 멈춰 진행이 눈에 보이게 한다.
            render_bars();
        }


        for (int i = 0; i <= 30; i++) {
            bar2_filled = i;
            Thread.sleep(rng.nextInt(100, 500)); // 0.1~0.5초 사이 무작위로 멈춰 진행이 눈에 보이게 한다.
            render_bars();
        }

        System.out.println("Done.");
    }

    //bar1,bar2 를 그리는 메서드. ANSI escape code로 두 줄을 같은 자리에 다시 그린다.
    static void render_bars() {
        String bar1 = "[" + "#".repeat(bar1_filled) + " ".repeat(30 - bar1_filled) + "]";
        String bar2 = "[" + "#".repeat(bar2_filled) + " ".repeat(30 - bar2_filled) + "]";

        if (!first_render) {
            System.out.print("\033[2A"); // 커서를 이전 진행바 2줄 위로 올린다.
        }

        System.out.print("\r\033[2Kbar1 " + bar1 + "\n"); // "\r"로 줄의 맨 앞으로 가서 "\033[2K"로 그 줄을 지운 뒤 새 내용을 쓴다. "\n"로 다음 줄로 내려간다.
        System.out.print("\r\033[2Kbar2 " + bar2 + "\n"); // 다음 줄로 내려간다.
        System.out.flush(); // 버퍼를 즉시 비워 화면에 바로 반영시킨다.
        first_render = false;
    }
}
