# Week 09 - VT 터미널 제어와 쓰레드

## 학습 목표

- VT(Virtual Terminal) escape 로 콘솔의 같은 자리를 다시 그리는 법을 안다 (`\r`, `\033[2A`/`\033[1A`, `\033[2K`)
- `new Thread(Runnable).start()` 로 새 스레드를 띄우고, `join()` 으로 종료를 기다린다
- **여러 스레드가 동시에 진행되는 모습**을 진행바로 시각화한다 (Ex01_xx)
- 두 스레드가 한 화면을 함께 그릴 때 `synchronized` 로 출력을 직렬화하는 이유를 안다 (Ex01_02)
- 갱신(update)과 그리기(render)를 **다른 스레드로 분리**하는 패턴, 공유 변수에 `volatile` 을 붙이는 이유를 안다 (Ex01_03)
- 표준 종료 절차 `interrupt() → join()` (interruption protocol) 로 다른 스레드를 깔끔하게 멈춘다
- 백그라운드 스레드 위에 VT 제어로 **사용자와 상호작용하는 화면**을 입힌다 (Ex02_xx)
- 커서 저장·복원(`ESC 7` / `ESC 8`)으로 사용자 입력과 화면 갱신을 매끄럽게 공존시킨다 (Ex02_03)

## 예제 클래스

- 패키지: `chapter09`
- 이 챕터는 **VT 터미널 제어**와 **쓰레드**를 함께 다룬다. 쓰레드가 화면을 살아 움직이게 하는 엔진이고, VT escape 가 그 화면을 그리는 붓이다.
- 클래스는 두 갈래로 묶인다.

**`Ex01_xx` — 진행바로 보는 쓰레드**

| 클래스 | 한 줄 설명 | 새로 나오는 것 |
|---|---|---|
| `Ex01_01` | 진행바 기본 틀 — 단일 스레드가 두 트랙을 차례로 채운다 | `\r`, ANSI `\033[2A`/`\033[2K`, `flush` |
| `Ex01_02` | 두 스레드가 진행바를 동시에 채운다 | `Thread`/`start()`/`join()`, `synchronized` |
| `Ex01_03` | 갱신과 그리기를 다른 스레드로 분리한다 | `volatile`, render 스레드, `interrupt()` |

**`Ex02_xx` — 쓰레드 위에 VT 로 옷 입히기 (타이머 → 인터랙티브)**

| 클래스 | 한 줄 설명 | 새로 나오는 것 |
|---|---|---|
| `Ex02_01` | 타이머 — 자동으로 돌고, Enter 로 종료 | 백그라운드 스레드, 한 줄 VT 갱신 |
| `Ex02_02` | 스톱워치 — start/stop/reset 명령 | `Scanner` REPL, 고정 레이아웃 `\033[1A` |
| `Ex02_03` | 스톱워치 — 타이핑 흔들림을 없앤다 | 커서 저장·복원 `ESC 7`/`ESC 8` |

## 이번 주 핵심 개념

- 스레드는 한 프로그램 안에서 동시에 진행될 수 있는 작업 흐름의 단위다.
- `Runnable` 은 "이 작업을 스레드로 실행할 수 있다"는 표현. 람다 `() -> { ... }` 또는 메서드 참조 `Ex02_01::runTimer` 로 짧게 적을 수 있다.
- `start()` 는 새 스레드를 실제로 띄운다. `run()` 을 직접 부르면 그냥 같은 스레드에서 메서드 호출 — 동시 진행이 되지 않는다.
- `Thread.sleep(ms)` 는 **현재 스레드만** 멈춘다. 다른 스레드는 영향을 받지 않는다.
- `Scanner.nextLine()` 같은 블로킹 호출도 마찬가지로 **호출한 스레드만** 멈춘다. 그래서 타이머 스레드가 따로 떠 있으면 입력 대기 시간에도 타이머는 계속 흐른다 (Ex02_xx 의 핵심).
- 다른 스레드를 깔끔하게 끝내는 표준 절차: `target.interrupt()` 로 신호를 보내고 (sleep 중이면 `InterruptedException` 으로 깨어난다), `target.join()` 으로 정리가 끝나기를 기다린다.
- 여러 스레드가 한 화면이나 한 변수를 함께 쓰면 충돌이 생긴다. 해법은 두 가지 — **`synchronized` 락** (한 번에 하나만 들어가게) 과 **`volatile`** (쓴 값을 즉시 보이게).
- VT escape 는 콘솔 커서를 옮기고 줄을 지우는 "제어 문자열" 이다. 화면을 스크롤하지 않고 **같은 자리에 다시 그리는** 모든 것이 여기서 나온다.

## 실행 방법

```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter09/*.java

java -cp out/classes chapter09.Ex01_01   # 진행바 기본 틀 (단일 스레드)
java -cp out/classes chapter09.Ex01_02   # 두 스레드 동시 진행
java -cp out/classes chapter09.Ex01_03   # 갱신/렌더 스레드 분리
java -cp out/classes chapter09.Ex02_01   # 타이머 (Enter 로 종료)
java -cp out/classes chapter09.Ex02_02   # 스톱워치 (start/stop/reset/quit)
java -cp out/classes chapter09.Ex02_03   # 스톱워치 + 커서 흔들림 해결
```

여섯 예제 모두 화면을 같은 자리에 다시 그리려고 VT escape (`\033[1A`/`\033[2A`, `\033[2K`, `ESC 7`/`ESC 8`) 를 쓴다. VT(Virtual Terminal) 처리가 켜진 콘솔 — Windows Terminal, VSCode 통합 터미널, IntelliJ 터미널 — 에서 실행한다. PowerShell 에서 ANSI 코드가 글자 그대로 보이면 [Ex01_01.java](../../app/chapter09/Ex01_01.java) 주석의 `reg add` 안내를 따른다.

## 예제별 설명

### Ex01_01 - 진행바 기본 틀 (단일 스레드)

대상 소스: [app/chapter09/Ex01_01.java](../../app/chapter09/Ex01_01.java)

진행바를 콘솔에 그리는 가장 단순한 형태. **아직 추가 스레드가 없다 — main 하나만 돈다.** main 이 진행바 두 줄을 *차례로* 채운다: `bar1` 을 0→30 까지 채운 뒤, `bar2` 를 0→30 까지 채운다. 다음 예제(`Ex01_02`)에서 이 두 줄을 두 스레드가 *동시에* 채우게 할 것이므로, 여기서는 "어떻게 같은 자리에 다시 그리는가" 자체만 익힌다.

**두 줄을 같은 자리에 다시 그리기**

진행바가 두 줄이라 `\r`(줄 맨 앞으로) 만으로는 부족하다. 커서를 윗줄로 올리는 ANSI escape 가 필요하다.

| 도구 | 역할 |
|---|---|
| `\r` (carriage return) | 커서를 현재 줄의 맨 앞으로 옮긴다 (줄바꿈 없음). |
| `\033[2A` | 커서를 위로 2줄 올린다 (직전에 그린 진행바 2줄 위로). |
| `\033[2K` | 커서가 있는 줄 전체를 지운다 (이전 잔상 제거). |
| `System.out.flush()` | 출력 버퍼를 즉시 비워 화면에 곧장 반영. |
| `Thread.sleep(ms)` | 잠시 멈춰 진행이 눈에 보이게 한다. |

`render_bars()` 는 매번 `\033[2A` 로 두 줄 위로 올라간 뒤 두 줄을 새로 찍는다. 단 맨 처음 호출에는 위에 그려진 줄이 없으므로 `first_render` 플래그로 `\033[2A` 를 건너뛴다.

```java
static void render_bars() {
    String bar1 = "[" + "#".repeat(bar1_filled) + " ".repeat(30 - bar1_filled) + "]";
    String bar2 = "[" + "#".repeat(bar2_filled) + " ".repeat(30 - bar2_filled) + "]";

    if (!first_render) {
        System.out.print("\033[2A");                 // 커서를 진행바 2줄 위로
    }
    System.out.print("\r\033[2Kbar1 " + bar1 + "\n");
    System.out.print("\r\033[2Kbar2 " + bar2 + "\n");
    System.out.flush();
    first_render = false;
}
```

> **`ThreadLocalRandom`**: 멈춤 시간을 `ThreadLocalRandom.current().nextInt(100, 500)` (0.1~0.5초) 로 정한다. `new Random()` 대신 쓰는 이유는 ① 스레드마다 별도의 난수 상태를 가져 빠르고 안전하며, ② 사용법이 짧다. 단일 스레드인 `Ex01_01` 에서는 효과가 미미하지만, 같은 코드를 `Ex01_02` 의 멀티스레드 환경에 그대로 옮겨도 안전하도록 처음부터 이걸 쓴다.

전형적인 실행 결과 (마지막 프레임):

```
bar1 [##############################]
bar2 [##############################]
Done.
```

### Ex01_02 - 두 스레드가 진행바를 동시에

대상 소스: [app/chapter09/Ex01_02.java](../../app/chapter09/Ex01_02.java)

`Ex01_01` 에서는 main 하나가 `bar1` 을 채운 뒤 `bar2` 를 채웠다. `Ex01_02` 는 같은 두 줄을 **워커 스레드 두 개**가 한 줄씩 맡아 동시에 채운다. 두 진행바가 같이 차오르는 모습으로 "병렬 실행" 을 본다.

**스레드 시작과 대기**

```java
Thread t1 = new Thread(() -> {
    try {
        _worker1();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
});
// t2 도 같은 모양 (_worker2 를 실행)

t1.start();   // 새 스레드 시작
t2.start();
t1.join();    // t1 이 끝날 때까지 main 이 기다림
t2.join();    // t2 가 끝날 때까지 main 이 기다림
```

- `start()` 는 새 스레드를 띄운다. (`run()` 을 직접 부르면 그냥 같은 스레드에서 실행돼 동시 진행이 안 된다 — 꼭 직접 확인해 보자.)
- `join()` 이 없으면 `main` 이 먼저 끝나서 `"Done."` 이 진행바 한가운데에 끼어드는 모습을 볼 수 있다 (실습 권장).
- 각 워커는 `ThreadLocalRandom.current()` 로 0.1~0.5초 사이 무작위로 멈춘다. 매 실행마다 누가 먼저 끝날지 달라진다.

**두 스레드가 한 화면을 그린다 — `synchronized` 로 직렬화**

`render_bars()` 는 t1, t2 **양쪽에서** 호출된다. 락이 없으면 "커서 2줄 위로 → 두 줄 출력" 도중에 다른 스레드가 끼어들어, `\033[2A` 가 몇 줄 위를 가리키는지 어긋나며 화면이 깨진다. 그래서 출력 전체를 자물쇠 하나로 묶는다.

```java
private static final Object SCREEN_LOCK = new Object();

static void render_bars() {
    synchronized (SCREEN_LOCK) {
        // ... 커서 이동 + 두 줄 출력 ...
    }
}
```

`synchronized (SCREEN_LOCK)` 안에는 한 번에 한 스레드만 들어간다. 한 스레드가 두 줄을 다 그릴 때까지 다른 스레드는 문 앞에서 기다린다.

`Ex01_01` 과의 차이점 한눈에:

| 항목 | Ex01_01 | Ex01_02 |
|---|---|---|
| 스레드 수 | 1 (main 만) | 3 (main + worker 2개) |
| 두 진행바 | 차례로 채움 | 동시에 채움 |
| 화면 그리기 | main 이 직접 | worker 둘이 각자 `render_bars()` 호출 |
| 출력 직렬화 | 필요 없음 | `synchronized (SCREEN_LOCK)` 으로 충돌 방지 |

전형적인 실행 결과 (마지막 프레임):

```
bar1 [##############################]
bar2 [##############################]
Done.
```

> `bar1_filled` / `bar2_filled` 는 평범한 `int` 로 공유한다 — 각 워커가 자기 변수만 쓰고, 화면 출력은 `SCREEN_LOCK` 으로 묶여 있어 이 예제에서는 충분하다. 한 변수를 한 스레드가 쓰고 다른 스레드가 읽을 때의 가시성 문제와 `volatile` 은 `Ex01_03` 에서 다룬다.

### Ex01_03 - 갱신과 그리기를 다른 스레드로 분리

대상 소스: [app/chapter09/Ex01_03.java](../../app/chapter09/Ex01_03.java)

`Ex01_02` 에서는 워커 둘이 *진행률 갱신*과 *화면 그리기*를 둘 다 했다. 그래서 출력이 겹치지 않게 `synchronized` 락이 필요했다. `Ex01_03` 은 한 발 더 나아가 **그리는 일을 전담하는 render 스레드**를 따로 둔다.

| 스레드 | 일감 |
|---|---|
| worker 2개 | 진행률(`bar1_filled` / `bar2_filled`) 만 갱신. 화면은 안 건드린다. |
| render 1개 | 50ms 마다 화면만 다시 그린다. |

main 은 셋을 모두 띄운다 — worker 2개 + render 1개.

**락이 사라지고, `volatile` 이 등장한다**

화면에 쓰는 스레드가 render 하나뿐이라 출력이 충돌하지 않는다 → `SCREEN_LOCK` 이 필요 없어진다. 대신 진행률을 worker 가 쓰고 render 가 읽으므로, 그 값에 `volatile` 을 붙여 가시성을 보장한다.

```java
private static volatile int bar1_filled = 0;
private static volatile int bar2_filled = 0;
```

`volatile` 이 없으면 render 스레드가 worker 가 쓴 새 값을 곧바로 못 보고 옛 값을 들고 있을 수 있다. `volatile` 은 "이 변수는 여러 스레드가 함께 보니, 쓴 값을 즉시 보이게 해 줘" 라고 JVM 에 알리는 표시다.

> **`Ex01_02` 의 락 ↔ `Ex01_03` 의 volatile**: 둘은 같은 "가시성" 문제의 두 해법이다. 화면 출력처럼 *여럿이 한 자원을 동시에 건드리면* 락으로 한 번에 하나만 들어가게 하고, 진행률처럼 *한 명이 쓰고 다른 명이 읽기만 하면* `volatile` 로 가볍게 푼다.

**render 스레드는 무한 루프 — `interrupt()` 로 멈춘다**

render 스레드는 `while (true)` 로 계속 그린다. 워커가 다 끝나면 main 이 이 무한 루프를 멈춰야 한다.

```java
t1.join();              // 워커 둘이 끝나기를 기다린다
t2.join();
renderer.interrupt();   // render 스레드의 sleep 을 깨워 루프를 빠져나오게 한다
renderer.join();        // render 스레드가 정리될 때까지 기다린다
render_bars();          // 마지막 프레임(둘 다 100%) 을 한 번 더 그린다
```

이 `interrupt() → join()` 절차가 자바 표준 종료 방식이다. `Ex02_xx` 에서 다시 쓴다.

전형적인 실행 결과 (마지막 프레임):

```
bar1 [##############################]
bar2 [##############################]
Done.
```

### Ex02_01 - 타이머: VT 화면 제어의 첫걸음

대상 소스: [app/chapter09/Ex02_01.java](../../app/chapter09/Ex02_01.java)

`Ex02_xx` 는 쓰레드 위에 VT 터미널 제어로 "화면을 꾸미는" 데 초점을 둔다 — 쓰레드 자체는 `Ex01_xx` 에서 익혔으니, 여기서는 그 위에 옷을 입힌다. `Ex02_01` 은 그 첫걸음이자 가장 단순한 형태다.

- `timer` 스레드가 1초마다 경과 시간을 **같은 줄에 다시 그린다**.
- `main` 은 `Enter` 입력을 기다리며 멈춰 있다. `Enter` 를 누르면 종료.
- `main` 이 입력 대기로 멈춰 있는 동안에도 `timer` 는 계속 도는 것 — **그게 스레드를 쓰는 이유다.**

**VT 도구**:

| 도구 | 역할 |
|---|---|
| `\r` | 커서를 줄 맨 앞으로 옮긴다 |
| `\033[2K` | 커서가 있는 줄을 통째로 지운다 |

`redraw()` 가 이 둘로 "같은 줄을 지우고 다시 쓰기" 를 한다:

```java
static void redraw() {
    int mm = elapsedSeconds / 60;
    int ss = elapsedSeconds % 60;
    System.out.printf("\r\033[2K  elapsed: %02d:%02d", mm, ss);
    System.out.flush();
}
```

**공유 상태가 없다**: `elapsedSeconds` 는 `timer` 스레드 혼자 쓰고 읽는다. 다른 스레드와 공유하지 않으므로 `volatile` 이 필요 없다. 명령(`start`/`stop`)이 생겨 `main` 과 상태를 공유하는 `Ex02_02` 에서 `volatile` 이 등장한다.

전형적인 실행 결과:

```
  Timer  (press Enter to quit)

  elapsed: 00:07          <- 매초 같은 자리에서 갱신

Bye.
```

### Ex02_02 - 스톱워치: 명령으로 제어하는 타이머

대상 소스: [app/chapter09/Ex02_02.java](../../app/chapter09/Ex02_02.java)

`Ex02_01` 의 타이머에 **명령**을 더한다. `start` / `stop` / `reset` 명령으로 상태를 바꾸고, 별도 `timer` 스레드가 그 상태를 관찰해 카운터를 굴린다. 두 스레드가 **공유 상태**를 두고 협력하는 표준 패턴이다.

화면은 위에서부터 이렇게 쌓인다:

```
  Stopwatch                              <- 한 번만 출력 (고정)
  commands: start | stop | reset | quit  <- 한 번만 출력 (고정)
  timer: 00:07 [RUNNING]                 <- 매초 timer 스레드가 같은 자리에서 갱신
  start_                                 <- 사용자가 명령을 친다
```

`timer:` 줄은 입력 줄 바로 위에 있다. timer 스레드가 매초 `\033[1A` 로 한 줄 위로 올라가 그 줄을 지우고 다시 쓴 뒤, 커서를 입력 줄로 되돌린다. `Ex01_02` 가 진행바를 그릴 때 쓴 도구(`\033[NA` + `\033[2K`)를 그대로 가져온 것이다.

**명령 → 상태 전이**:

| 명령 | 효과 |
|---|---|
| `start` | `running = true` — 매초 카운터 증가 시작 |
| `stop`  | `running = false` — 카운터 정지 (경과 시간은 유지) |
| `reset` | `elapsedSeconds = 0` — 카운터 0으로 초기화 |
| `quit`  | 프로그램 종료 |
| 그 외 | 무시되고 다음 입력 대기 |

**스레드 두 개와 공유 상태**:

| 스레드 | 일감 |
|---|---|
| `main`  | 사용자 명령을 읽고 공유 상태를 바꾼다. |
| `timer` | 매초 `running` 이 `true` 면 카운터를 1 올리고, timer 줄을 다시 그린다. |

| 공유 변수 | 타입 | 누가 쓰나 | 누가 읽나 |
|---|---|---|---|
| `running` | `volatile boolean` | main | timer |
| `elapsedSeconds` | `volatile int` | main (reset), timer (++) | timer (출력) |

**`volatile` — `Ex01_03` 에 이어 다시**:

```java
private static volatile boolean running = false;
private static volatile int elapsedSeconds = 0;
```

`Ex01_03` 에서 본 그대로다 — `running` / `elapsedSeconds` 를 한 스레드가 쓰고 다른 스레드가 읽으므로, `volatile` 로 쓴 값을 곧바로 보게 한다. 락 없이 가벼운 공유.

**핵심 코드 — 화면 갱신**:

```java
static void redraw() {
    synchronized (SCREEN_LOCK) {
        int mm = elapsedSeconds / 60;
        int ss = elapsedSeconds % 60;
        String state = running ? "RUNNING" : "STOPPED";
        // \033[1A 로 timer 줄로 올라가 \033[2K 로 지우고 다시 쓴 뒤, %n 으로 입력 줄 복귀
        System.out.printf("\033[1A\r\033[2K  timer: %02d:%02d [%s]%n", mm, ss, state);
        System.out.flush();
    }
}
```

`Ex01_02` 의 `render_bars()` 와 같은 도구다 — 커서를 위로 올리고(`\033[1A`), 줄을 지우고(`\033[2K`), 다시 쓴다. 다른 점은 *몇 줄* 올라가느냐뿐 (`Ex01_02` 는 진행바 2줄이라 `\033[2A`, 여기는 timer 1줄이라 `\033[1A`).

**핵심 코드 — main 의 명령 루프**:

```java
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
        // Enter 로 한 줄 내려간 커서를 입력 줄 자리로 되돌리고, 새 상태를 즉시 그린다
        System.out.print("\033[1A\r\033[2K");
        redraw();
    }
}
```

이 구조가 **REPL (Read-Eval-Print Loop)** 의 기본형이다. `jshell`, 파이썬 인터프리터, `bash` 모두 같은 골격 — 입력 → 처리 → 다시 입력. Enter 를 누르면 커서가 한 줄 내려가므로, 명령 처리 뒤 `\033[1A` 로 커서를 입력 줄 자리에 다시 맞춰 줘야 다음 `redraw()` 의 `\033[1A` 가 timer 줄을 정확히 가리킨다.

**main 과 timer 모두 redraw 를 부른다**: 두 스레드(main 의 명령 직후 호출, timer 의 매초 호출)가 모두 `redraw()` 를 부르므로, `synchronized (SCREEN_LOCK)` 으로 출력 한 덩어리가 통째로 나가게 묶는다. `Ex01_02` 의 `SCREEN_LOCK` 과 같은 역할이다.

**시각적 제약 — `Ex02_03` 에서 해결한다**:

`redraw()` 는 커서를 timer 줄로 올렸다가 입력 줄 **맨 앞**으로 되돌린다. 그래서 사용자가 입력 줄에서 타이핑하는 도중 timer tick 이 발생하면, 커서가 잠깐 맨 앞으로 돌아가 **이미 친 글자 위에 겹친다**. `Scanner` 는 stdin 버퍼에서 정확히 읽으므로 명령 처리 자체는 정상이지만, 보기에 어색하다. 이 흔들림을 다음 예제 `Ex02_03` 에서 커서 저장·복원으로 없앤다.

**`Ex01_xx` 에서 못 본 새 도구**:

| 도구 | 역할 |
|---|---|
| `Scanner.nextLine()` | 표준 입력에서 한 줄이 들어올 때까지 호출한 스레드를 멈춘다. 다른 스레드는 영향 없음. |
| `Scanner.hasNextLine()` | 다음 줄 존재 검사 (블로킹). EOF 시 `false`. |
| `switch (cmd)` | 명령에 따라 다른 상태 전이를 일으키는 간단한 상태 머신. |
| `System.out.printf` / `%02d` | 두 자리 0 채움 정수 서식. `00:00` 형식 만들기. |

**종료 흐름 (자바 표준 interruption protocol)**:

1. 사용자가 `quit` 입력 → `Scanner.nextLine()` 반환 → main 이 루프 탈출
2. main 이 `timer.interrupt()` → timer 의 `Thread.sleep` 이 `InterruptedException` 던지며 깨어남
3. timer 의 `catch` 블록이 잡고 루프를 빠져나감 → timer 종료
4. main 의 `timer.join()` 이 반환 → main 이 `"Bye."` 출력 후 종료

`Ex01_03` 의 render 스레드를 멈춘 절차와 똑같다 — `interrupt()` 로 깨우고 `join()` 으로 기다린다.

### Ex02_03 - 스톱워치: 커서 저장·복원으로 흔들림 없애기

대상 소스: [app/chapter09/Ex02_03.java](../../app/chapter09/Ex02_03.java)

`Ex02_02` 와 거의 같은 스톱워치다. **딱 한 메서드, `redraw()` 만 다르다.** `Ex02_02` 의 "시각적 제약" — 타이핑 중 커서가 친 글자 위로 겹치는 문제 — 를 잡는 것이 `Ex02_03` 의 전부다.

**문제의 원인**: `Ex02_02` 의 `redraw()` 는 마지막에 커서를 입력 줄 **맨 앞(col 1)** 에 둔다. 사용자가 `sta` 까지 쳤어도 다음 글자가 col 1 부터 echo 된다.

**해결 — `ESC 7` / `ESC 8` (커서 저장·복원)**:

| 도구 | 역할 |
|---|---|
| `ESC 7` (`"\0337"`) | 지금 커서 위치를 저장한다 (DECSC) |
| `ESC 8` (`"\0338"`) | 저장해 둔 위치로 커서를 복원한다 (DECRC) |

`redraw()` 는 그리기 전에 `ESC 7` 로 **지금 커서 자리**(= 사용자가 타이핑하던 끝)를 저장하고, timer 줄을 다시 그린 뒤, `ESC 8` 로 그 자리로 되돌린다:

```java
private static final String SAVE_CURSOR    = "\0337";
private static final String RESTORE_CURSOR = "\0338";

static void redraw() {
    synchronized (SCREEN_LOCK) {
        int mm = elapsedSeconds / 60;
        int ss = elapsedSeconds % 60;
        String state = running ? "RUNNING" : "STOPPED";
        System.out.print(SAVE_CURSOR);                                   // 커서 저장 (입력하던 끝)
        System.out.printf("\033[1A\r\033[2K  timer: %02d:%02d [%s]", mm, ss, state);
        System.out.print(RESTORE_CURSOR);                                // 커서 복원
        System.out.flush();
    }
}
```

핵심은 **프로그램이 입력 길이를 몰라도 된다**는 점이다. 사용자가 몇 글자 쳤는지는 `nextLine()` 이 반환하기 전엔 알 수 없다 — 하지만 알 필요가 없다. 커서 위치는 *터미널*이 기억하고, `ESC 7`/`ESC 8` 은 그 위치를 저장·복원할 뿐이다.

> **Java 함정**: `ESC` 는 `\033`. `ESC 7` 을 `"\0337"` 로 써도 동작하지만, `\033` 뒤에 숫자가 붙으면 8진수로 헷갈리기 쉽다. 그래서 `SAVE_CURSOR` / `RESTORE_CURSOR` 라는 이름 있는 상수로 빼 의도를 분명히 했다.

`Ex02_02` 와 `Ex02_03` 을 번갈아 실행하며 명령을 천천히 타이핑해 보면 차이가 바로 보인다 — 같은 프로그램인데 `Ex02_03` 은 타이핑이 흔들리지 않는다.

## 실습 체크리스트

- `Ex01_01` 을 실행해 `bar1` 이 다 찬 뒤 `bar2` 가 차례로 채워지는 것을 확인했다.
- `Ex01_01` 의 `\033[2A` 한 줄을 잠깐 지우고 실행해, 진행바가 같은 자리에 안 그려지고 줄이 계속 쌓이는 모습을 보고 `\033[2A` 가 무엇을 하는지 확인했다.
- `Ex01_01` 의 `System.out.flush()` 를 잠깐 지워 보고, 갱신이 묶여서 한 번에 보이는 현상을 확인했다 (환경에 따라 다를 수 있음).
- `Ex01_02` 를 여러 번 실행해 두 진행바의 도착 시점이 매번 달라지는 것을 확인했다.
- `Ex01_02` 에서 `t1.join(); t2.join();` 두 줄을 잠깐 지우고 실행해, `"Done."` 이 진행바 사이에 끼어드는 모습을 직접 봤다.
- `Ex01_02` 에서 `t1.start()` / `t2.start()` 를 `t1.run()` / `t2.run()` 으로 바꿔 보고, 동시 진행이 사라지고 차례대로 끝나는 것을 확인했다.
- `Ex01_02` 의 `render_bars()` 에서 `synchronized (SCREEN_LOCK)` 을 잠깐 빼고 여러 번 실행해, 두 스레드의 출력이 가끔 뒤섞여 진행바가 어긋나는 모습을 확인했다.
- `Ex01_03` 을 실행해 두 진행바가 동시에 차오르고 `Done.` 으로 끝나는 것을 확인했다.
- `Ex01_03` 에서 `renderer.interrupt()` 를 잠깐 지우고 실행해, 워커가 끝나도 프로그램이 끝나지 않고 매달리는 것을 확인했다 (render 스레드의 무한 루프).
- `Ex01_03` 의 `bar1_filled` / `bar2_filled` 에서 `volatile` 을 빼고 실행해, 동작은 비슷해 보여도 "render 스레드가 새 값을 본다는 보장이 없다"는 점을 설명할 수 있다.
- `Ex02_01` 을 실행해 `elapsed: MM:SS` 가 매초 같은 자리에서 갱신되는 것을 확인했다.
- `Ex02_01` 에서 Enter 를 누르기 전까지 timer 가 계속 도는 것을 확인했다 — main 이 입력 대기로 멈춰 있어도.
- `Ex02_02` 를 실행해 `timer: 00:00 [STOPPED]` 줄이 입력 줄 바로 위에서 매초 갱신되는 것을 확인했다.
- `Ex02_02` 에서 `start` → `[RUNNING]` 으로 카운터 증가, `stop` → 정지(시간 유지), `reset` → `00:00` 인 것을 확인했다.
- `Ex02_02` 에서 명령을 천천히 타이핑하는 도중 timer tick 이 오면, 커서가 친 글자 위로 겹치는 "시각적 제약" 을 직접 봤다.
- `Ex02_02` 에서 `timer.interrupt()` 를 잠깐 지워 보고, `quit` 을 입력해도 `Bye.` 가 한 박자 늦게 나오는 모습을 확인했다.
- `Ex02_02` 와 `Ex02_03` 을 번갈아 실행하며 명령을 천천히 타이핑해, `Ex02_03` 에서는 커서 흔들림이 없는 것을 비교 확인했다.
- `Ex02_03` 의 `redraw()` 에서 `SAVE_CURSOR` / `RESTORE_CURSOR` 출력을 잠깐 빼 보고, `Ex02_02` 와 똑같이 흔들리는 것을 확인했다.

## 퀴즈 예시

- `\r` 과 `\n` 은 어떻게 다른가?
- `\033[2A` 와 `\033[2K` 는 각각 무엇을 하는가? 진행바가 두 줄일 때 왜 `\r` 만으로는 부족한가?
- `start()` 와 `run()` 의 차이는 무엇인가?
- `join()` 이 없으면 어떤 문제가 생길 수 있는가?
- `Ex01_02` 에서 `render_bars()` 를 `synchronized` 로 묶지 않으면 어떤 문제가 생기는가? 왜 그런가?
- `Ex01_03` 은 화면에 쓰는 스레드가 render 하나뿐인데, 왜 `synchronized` 락이 없어도 되는가?
- `synchronized` 와 `volatile` 은 각각 어떤 상황에 쓰는가?
- `Ex02_01` 에는 왜 `volatile` 이 필요 없고, `Ex02_02` 에서는 왜 필요해지는가?
- 같은 코드를 여러 번 실행했을 때 결과가 매번 달라지는 이유는 무엇인가?
- `Scanner.nextLine()` 이 main 을 멈추는 동안에도 `Ex02_01` 의 timer 스레드는 왜 계속 도는가?
- `interrupt()` 가 하는 일은 무엇인가? `InterruptedException` 은 누가 던지는가?
- `Ex02_03` 의 `redraw()` 가 `ESC 7` / `ESC 8` 로 하는 일은 무엇인가? 프로그램이 사용자 입력 길이를 모르는데 어떻게 커서를 입력 끝에 둘 수 있는가?

## 추천 추가 실습

- `Ex01_01` 의 진행바 모양을 `[#####.....]` 처럼 빈 칸을 다른 문자로 꾸며 보기
- `Ex01_02` 의 워커 수를 3개로 늘려 보기 (`bar3_filled` 와 `_worker3()` 추가, `render_bars()` 에 줄 하나 추가, `\033[2A` 를 `\033[3A` 로)
- `Ex01_03` 의 render 간격(`Thread.sleep(50)`)을 늘려(예: 500ms) 보고, 진행바 갱신이 뚝뚝 끊겨 보이는 것을 관찰하기
- `Ex02_01` 의 `elapsed: MM:SS` 옆에 점이 하나씩 늘었다 줄었다 하는 간단한 애니메이션 붙여 보기
- `Ex02_02` 에 `pause` / `resume` 을 `stop` / `start` 의 별칭으로 추가해 명령 어휘 늘리기
- `Ex02_02` 에 `status` 명령 추가 — 입력하면 현재 경과 시간과 상태를 한 줄로 즉시 출력
- `Ex02_03` 의 `timer:` 줄에 색을 입혀 보기 — `RUNNING` 은 초록(`\033[32m`), `STOPPED` 는 빨강(`\033[31m`), 끝에 `\033[0m` 로 닫기
- `Ex02_03` 의 카운터를 100ms (0.1초) 단위로 만들어 `mm:ss.s` 형식으로 표시하기
