package chapter09;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 진행바 기본 틀 -- 콘솔 한 줄을 같은 자리에 다시 그려서 진행 상태를 보여 준다.
 *
 * 이 예제에는 아직 추가 스레드가 없다. main 스레드 하나가 30 칸짜리 트랙을 채워간다.
 * Ex02 에서 이 패턴을 그대로 두 개의 스레드에 적용해 동시 진행을 보여 준다.
 *
 * 핵심 도구:
 *   - "\r" (carriage return) : 커서를 줄의 맨 앞으로 옮긴다. 다음 출력이 같은 줄을 덮어쓴다.
 *   - System.out.flush()      : 버퍼를 즉시 비워 화면에 바로 반영시킨다.
 *   - Thread.sleep(ms)        : 잠시 멈춰 진행이 눈에 보이게 한다.
 *
 * 동작 환경: 일반 콘솔이면 어디서나 잘 보인다. (ANSI escape 미사용)
 */
public class Ex01 {
    private static final int TRACK_WIDTH = 30;

    public static void main(String[] args) throws InterruptedException {

        /* 
        무작위 값 생성기. `new Random()` 대신 쓰는 이유는 
        ① 스레드마다 별도의 난수 상태를 가져 빠르고 안전하며,
        ② 사용법이 짧다 (`rng.nextInt(4)` 는 0~3 사이 정수). Ex02 처럼 여러 스레드가 각자 난수를 쓸 때 특히 유용하다.
        */  
        ThreadLocalRandom rng = ThreadLocalRandom.current(); 
        
        int filled = 0;
        while (filled < TRACK_WIDTH) {
            // 한 걸음 1~2 칸 진행
            filled = Math.min(TRACK_WIDTH, filled + 1);
            render(filled);
            // 60~119ms 잠시 멈춰 변화를 보이게 한다
            Thread.sleep(60 + rng.nextInt(60));
        }

        System.out.println();          // 진행바 줄을 마무리하고 다음 줄로
        System.out.println("Done.");
    }

    /** 채워진 칸 수(filled)를 받아 진행바 한 줄을 같은 자리에 다시 그린다. */
    static void render(int filled) {
        StringBuilder bar = new StringBuilder();
        bar.append('\r').append('|');               // 커서를 줄 맨 앞으로 + 트랙 시작
        for (int j = 0; j < TRACK_WIDTH; j++) {
            bar.append(j < filled ? '=' : ' ');
        }
        bar.append('|');

        System.out.printf("%s %3d%%", bar, filled * 100 / TRACK_WIDTH);
        System.out.flush();
    }
}
