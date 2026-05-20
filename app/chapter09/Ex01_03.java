package chapter09;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 갱신과 그리기를 분리 -- 워커는 진행률만 바꾸고, 별도 render 스레드가 화면을 그린다.
 *
 * Ex01_02 에서는 워커 둘이 각자 render_bars() 를 불러 화면을 그렸다.
 * 그래서 두 스레드의 출력이 안 뒤섞이도록 synchronized 락이 필요했다.
 *
 * Ex01_03 은 그리는 일을 전담하는 스레드 하나를 따로 둔다:
 *   - worker 스레드 둘  : 진행률(bar1_filled / bar2_filled) 만 갱신. 화면은 안 건드린다.
 *   - render 스레드 하나 : 일정 간격으로 화면만 다시 그린다.
 *
 * 화면에 쓰는 스레드가 render 하나뿐이라 출력이 충돌하지 않는다 -> SCREEN_LOCK 이 사라진다.
 * 대신 진행률을 worker 가 쓰고 render 가 읽으므로, 그 값에 volatile 을 붙여 가시성을 보장한다.
 * Ex01_02 의 'synchronized 락' 과 Ex01_03 의 'volatile' 은 같은 "가시성" 문제의 두 해법이다.
 *
 * 핵심 학습 포인트:
 *   - 갱신(update) 스레드와 그리기(render) 스레드의 분리 -- 게임 루프 / UI 스레드의 기본형
 *   - volatile : worker 가 쓴 값을 render 스레드가 곧바로 보게 한다
 *   - render 스레드는 무한 루프 -> interrupt() + join() 으로 깔끔하게 멈춘다
 *
 * 동작 환경: Ex01_02 와 동일 -- ANSI escape 지원 콘솔.
 *   PowerShell 에서 ANSI 코드가 글자 그대로 보이면 Ex01_02.java 주석의 reg add 안내를 따른다.
 */
public class Ex01_03 {

    // worker 가 쓰고 render 스레드가 읽는다. 락 없이 공유하므로 volatile 로 가시성을 보장한다.
    private static volatile int bar1_filled = 0;
    private static volatile int bar2_filled = 0;

    // 화면 그리기 상태. render 스레드만 다룬다 (main 의 마지막 한 번은 join 이후라 안 겹침).
    private static boolean first_render = true;

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

        Thread renderer = new Thread(Ex01_03::runRenderer);

        t1.start();
        t2.start();
        renderer.start();

        // 워커 둘이 끝나기를 기다린다.
        t1.join();
        t2.join();

        // 워커가 다 끝났으니 render 스레드를 멈춘다.
        // render 스레드는 무한 루프라, interrupt() 로 sleep 을 깨워 빠져나오게 한 뒤 join() 으로 기다린다.
        renderer.interrupt();
        renderer.join();

        // 마지막 프레임(둘 다 100%) 을 한 번 더 그려 마무리한다.
        render_bars();
        System.out.println("Done.");
    }

    // Ex01_02 의 _worker1 에서 render_bars() 호출만 빠졌다 -- 그리기는 render 스레드가 전담.
    static void _worker1() throws InterruptedException {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        for (int i = 0; i <= 30; i++) {
            bar1_filled = i;
            Thread.sleep(rng.nextInt(100, 500)); // 0.1~0.5초 사이 무작위
        }
    }

    static void _worker2() throws InterruptedException {
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        for (int i = 0; i <= 30; i++) {
            bar2_filled = i;
            Thread.sleep(rng.nextInt(100, 500)); // 0.1~0.5초 사이 무작위
        }
    }

    // render 스레드: 멈추라는 신호(interrupt)가 올 때까지 일정 간격으로 화면을 다시 그린다.
    static void runRenderer() {
        try {
            while (true) {
                render_bars();
                Thread.sleep(50); // 약 20 FPS
            }
        } catch (InterruptedException e) {
            // interrupt() 신호 -> 루프를 빠져나간다.
        }
    }

    // bar1, bar2 를 ANSI escape 로 두 줄 같은 자리에 다시 그린다. (render 스레드만 호출)
    static void render_bars() {
        String bar1 = "[" + "#".repeat(bar1_filled) + " ".repeat(30 - bar1_filled) + "]";
        String bar2 = "[" + "#".repeat(bar2_filled) + " ".repeat(30 - bar2_filled) + "]";

        if (!first_render) {
            System.out.print("\033[2A"); // 커서를 이전 진행바 2줄 위로 올린다.
        }

        System.out.print("\r\033[2Kbar1 " + bar1 + "\n");
        System.out.print("\r\033[2Kbar2 " + bar2 + "\n");
        System.out.flush();
        first_render = false;
    }
}
