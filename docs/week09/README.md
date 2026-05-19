# Week 09 - 스레드 기본

## 학습 목표

- 콘솔 한 줄을 다시 그려 진행 상태를 보여 주는 **기본 틀**을 안다 (`\r`, `flush`, `Thread.sleep`)
- `new Thread(Runnable, name).start()` 로 새 스레드를 띄우고, `join()` 으로 종료를 기다린다
- **여러 스레드가 동시에 진행되는 모습**을 한 줄 안의 두 진행바로 시각화한다
- **스레드를 왜 쓰는가**를 손으로 체감한다: 한 스레드가 입력 대기로 멈춰 있어도 다른 스레드는 자기 일을 계속한다
- 표준 종료 절차 `interrupt() → join()` (interruption protocol) 로 다른 스레드를 깔끔하게 멈춘다
- 두 스레드가 공유하는 상태에 `volatile` 을 붙이는 이유를 안다 (Ex03)

## 예제 클래스

- 패키지: `chapter09`
- 클래스:
  - `Ex01` — 진행바 기본 틀 (단일 스레드, 콘솔 갱신 기법 사전 학습)
  - `Ex02` — 두 스레드 동시 진행 (`start()` / `join()` 기본)
  - `Ex03` — 스톱워치(타이머) (start/stop/reset/quit 명령 + `volatile` 공유 상태)

## 이번 주 핵심 개념

- 스레드는 한 프로그램 안에서 동시에 진행될 수 있는 작업 흐름의 단위다.
- `Runnable` 은 "이 작업을 스레드로 실행할 수 있다"는 표현. 람다 `() -> { ... }` 또는 메서드 참조 `Ex03::runTimer` 로 짧게 적을 수 있다.
- `start()` 는 새 스레드를 실제로 띄운다. `run()` 을 직접 부르면 그냥 같은 스레드에서 메서드 호출 — 동시 진행이 되지 않는다.
- `Thread.sleep(ms)` 는 **현재 스레드만** 멈춘다. 다른 스레드는 영향을 받지 않는다.
- `Scanner.nextLine()` 같은 블로킹 호출도 마찬가지로 **호출한 스레드만** 멈춘다. 그래서 타이머 스레드가 따로 떠 있으면 입력 대기 시간에도 타이머는 계속 흐른다 (Ex03 의 핵심).
- 다른 스레드를 깔끔하게 끝내는 표준 절차: `target.interrupt()` 로 신호를 보내고 (sleep 중이면 `InterruptedException` 으로 깨어난다), `target.join()` 으로 정리가 끝나기를 기다린다.
- 진행바 같은 시각화는 콘솔에서 "같은 자리에 다시 그리기" 기법으로 만든다 (Ex01, Ex02).
- 두 스레드가 같은 변수를 함께 보면 `volatile` 키워드로 가시성을 보장한다 (Ex03).

## 실행 방법

```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter09/*.java

java -cp out/classes chapter09.Ex01   # 단일 스레드 진행바 기본 틀
java -cp out/classes chapter09.Ex02   # 스레드 2개 동시 진행
java -cp out/classes chapter09.Ex03   # 스톱워치 (start/stop/reset/quit 명령)
```

세 예제 모두 `\r` (carriage return) 과 `flush()` 만 사용한다 (ANSI escape 미사용). 따라서 cmd, PowerShell, Windows Terminal, VSCode 통합 터미널, Git Bash 등 **어떤 콘솔에서도 그대로 동작**한다.

## 예제별 설명

### Ex01 - 진행바 기본 틀 (단일 스레드)

대상 소스: `app/chapter09/Ex01.java`

콘솔에 진행바를 그리는 가장 단순한 형태. **아직 추가 스레드가 없다 — main 스레드 하나만 돈다.** 다음 예제(Ex02)에서 이 패턴 그대로를 두 개의 스레드에 옮길 것이므로, 여기서는 "어떻게 같은 자리에 다시 그리는가" 자체만 익힌다.

진행률은 **퍼센트(%) 가 아니라 채워진 칸 수(`filled`, 0~30)** 로 직접 관리한다. 본질은 "트랙 30칸 중 몇 칸을 채울 것인가"이므로, 백분율 계산이나 출력은 일부러 빼서 군더더기를 없앴다.

핵심 코드:

```java
int filled = 0;
while (filled < TRACK_WIDTH) {                                    // TRACK_WIDTH = 30
    filled = Math.min(TRACK_WIDTH, filled + 1 + rng.nextInt(2));  // 한 걸음 1~2 칸
    render(filled);
    Thread.sleep(60 + rng.nextInt(60));                           // 60~119ms 멈춤
}
System.out.println();
System.out.println("Done.");
```

`render` 한 번에 들어 있는 작은 트릭:

```java
StringBuilder bar = new StringBuilder();
bar.append('\r').append('|');           // 커서를 줄 맨 앞으로
for (int j = 0; j < TRACK_WIDTH; j++) {
    bar.append(j < filled ? '=' : ' ');
}
bar.append('|');
System.out.print(bar);
System.out.flush();
```

| 도구 | 역할 |
|---|---|
| `\r` (carriage return) | 커서를 **현재 줄의 맨 앞**으로 옮긴다. 줄바꿈은 일어나지 않으므로 다음 출력이 같은 줄을 덮어쓴다. |
| `System.out.flush()` | 자바의 출력 버퍼를 즉시 비워 화면에 곧장 반영. 없으면 갱신이 뭉쳐서 한 번에 보일 수 있다. |
| `Thread.sleep(ms)` | 현재 스레드를 잠시 멈춰 진행이 눈에 보이게 한다. |
| `ThreadLocalRandom.current()` | 무작위 값 생성기. `new Random()` 이나 `Math.random()` 대신 쓰는 이유는 ① 스레드마다 별도의 난수 상태를 가져 빠르고 안전하며, ② 사용법이 짧다 (`rng.nextInt(4)` 는 0~3 사이 정수). Ex02 처럼 여러 스레드가 각자 난수를 쓸 때 특히 유용하다. |

> **`ThreadLocalRandom` 이름의 의미**: "Thread-Local Random" — 스레드마다 자기만의 난수 상태를 갖는다는 뜻이다. 보통 `Random` 한 객체를 여러 스레드가 같이 쓰면 내부 상태를 두고 경합이 일어나 느려지는데, `ThreadLocalRandom` 은 이걸 피한다. 단일 스레드 예제(`Ex01`)에서는 성능 차이가 미미하지만, 같은 코드를 Ex02 의 멀티스레드 환경에 그대로 옮겨도 안전하도록 처음부터 이걸 쓴다.

전형적인 실행 결과 (마지막 세 프레임):

```
|============================  |
|============================= |
|==============================|
Done.
```

### Ex02 - 스레드 두 개 동시 진행

대상 소스: `app/chapter09/Ex02.java`

`Ex01` 의 진행바 패턴을 그대로 두 개의 스레드에 옮긴다. 두 워커의 진행바를 한 줄에 나란히 놓고, 같은 줄을 `\r` 로 통째로 다시 그린다 — 두 부분 진행바가 동시에 차오르는 모습으로 "병렬 실행" 을 본다.

스레드 시작과 대기는 핵심 두 줄이다.

```java
Thread tA = new Thread(() -> runWorker(0, avgSleepA), "worker-A");
Thread tB = new Thread(() -> runWorker(1, avgSleepB), "worker-B");

tA.start();   // 새 스레드 시작
tB.start();

tA.join();    // tA 가 끝날 때까지 main 이 기다림
tB.join();    // tB 가 끝날 때까지 main 이 기다림
```

- `start()` 는 새 스레드를 띄운다. (`run()` 을 직접 부르면 그냥 같은 스레드에서 실행돼 동시 진행이 안 된다 — 꼭 직접 확인해 보자.)
- `join()` 이 없으면 `main` 이 먼저 끝나서, `"Both finished."` 메시지가 진행바 한가운데에 끼어드는 모습을 직접 볼 수 있다 (실습 권장).

매 실행마다 결과가 달라지는 이유는 출발 시점에 워커의 평균 속도를 무작위로 정하기 때문이다.

```java
int avgSleepA = 20 + ThreadLocalRandom.current().nextInt(60);   // 20~79ms
int avgSleepB = 20 + ThreadLocalRandom.current().nextInt(60);   // 20~79ms
```

`ThreadLocalRandom` 이 `Ex01` 보다 진가를 발휘하는 곳이 여기다. 두 워커 스레드(`runWorker`) 안에서도 같은 `ThreadLocalRandom.current()` 호출을 쓰지만, 각 스레드가 자기만의 난수 상태를 받아오므로 서로 경합하지 않는다. `Random` 객체 하나를 둘이 공유했다면 내부 락 때문에 느려지거나 같은 값을 두 번 뽑을 위험이 있다.

`Ex01` 과의 차이점 한눈에:

| 항목 | Ex01 | Ex02 |
|---|---|---|
| 스레드 수 | 1 (main 만) | 3 (main + worker-A + worker-B) |
| 진행바 개수 | 1 | 2 (한 줄에 나란히) |
| 갱신 트릭 | `\r` (같은 줄 덮어쓰기) | `\r` (같은 줄 통째로 다시 그리기) |
| 출력 직렬화 | 필요 없음 | `synchronized (SCREEN_LOCK)` 으로 동시 출력 충돌 방지 |
| 진행률 변수 | 단일 `int` | `int[2]` |
| 난수 생성기 | `ThreadLocalRandom` (단일 스레드라 효과는 미미) | `ThreadLocalRandom` (스레드마다 독립 상태로 경합 회피) |

전형적인 실행 결과 (마지막 프레임):

```
  Two threads in parallel  (avgSleep: A=42ms, B=63ms)
  A:|====================| 100%   B:|================    |  82%
  Both finished.
```

진행 중에는 한 줄에 두 진행바가 같이 차오른다. `\r` 갱신이 통째로 다시 그리므로 같은 자리에서 두 부분이 동시에 움직인다.

> 진행률 변수는 평범한 `int[]` 로 공유한다 — 이 예제는 "스레드가 동시에 돈다"는 사실 자체에 집중하기 위함. 두 워커가 자기 칸만 쓰고 다른 칸을 안 쓰므로 큰 문제는 없지만, 엄격한 동시성 안전(메모리 가시성, 락) 처리는 Ex03 에서 `volatile` 로 한 발 더 나아간다.

### Ex03 - 상태 머신 + 공유 상태: 스톱워치(타이머)

대상 소스: `app/chapter09/Ex03.java`

이 예제는 두 스레드가 **공유 상태**를 두고 협력하는 표준 패턴을 보여 준다. 명령에 따라 상태가 바뀌고 (`start` / `stop` / `reset`), 별도 스레드가 그 상태를 계속 관찰해 작업을 수행한다.

화면 갱신은 **Ex01/Ex02 와 같은 `\r` 패턴만** 쓴다 (ANSI 미사용). 매초 timer 스레드가 현재 줄을 `\r` 로 덮어쓰며, 사용자가 Enter 를 누르면 커서가 한 줄 내려가 그 새 줄에서 갱신이 이어진다. 결과적으로 명령을 누를 때마다 직전 상태가 한 줄씩 "히스토리"로 남는다.

화면 모양 (예):

```
  Stopwatch -- commands: start | stop | reset | quit
  [00:00 STOPPED] > start           <-- Enter 직후 한 줄로 굳어짐
  [00:02 RUNNING] > stop
  [00:02 STOPPED] > _               <-- 매초 \r 로 같은 자리 갱신
```

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
| `timer` | 매초 `running` 이 `true` 면 카운터를 1 올리고, 현재 상태를 한 줄 출력한다. |

| 공유 변수 | 타입 | 누가 쓰나 | 누가 읽나 |
|---|---|---|---|
| `running` | `volatile boolean` | main | timer |
| `elapsedSeconds` | `volatile int` | main (reset), timer (++) | timer (출력) |

**`volatile` 키워드 — 처음 등장**:

```java
private static volatile boolean running = false;
private static volatile int elapsedSeconds = 0;
```

`volatile` 이 없으면 JVM/CPU 는 효율을 위해 변수 값을 CPU 캐시에 잠깐 들고 있다가 다른 스레드에 늦게 보여줄 수 있다. `volatile` 을 붙이면 "이 변수는 여러 스레드가 함께 보니까, 한쪽에서 쓴 값을 다른 쪽이 곧바로 보게 해 줘" 라고 JVM 에게 알리는 셈이다. 가장 가벼운 형태의 스레드 간 가시성(visibility) 보장.

**핵심 코드 — 화면 갱신 (Ex02 의 redraw 와 같은 `\r` 패턴)**:

```java
static void redraw() {
    synchronized (SCREEN_LOCK) {
        int mm = elapsedSeconds / 60;
        int ss = elapsedSeconds % 60;
        String state = running ? "RUNNING" : "STOPPED";
        System.out.printf("\r  [%02d:%02d %s] > ", mm, ss, state);
        System.out.flush();
    }
}
```

Ex02 가 두 진행바를 한 줄에 통째로 다시 그렸듯이, 여기서도 현재 줄을 `\r` 로 덮어쓰며 타이머 상태와 프롬프트 `> ` 를 매초 다시 찍는다. 도구 자체는 Ex01 의 `render()` 와 똑같다.

**핵심 코드 — 타이머 스레드**:

```java
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
```

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
        // Enter 직후 커서가 새 줄로 내려갔으니, 그 새 줄에 곧바로 새 상태를 한 번 그려 준다.
        // (다음 timer tick 까지 최대 1초 동안 빈 줄이 보이는 것을 막기 위함)
        redraw();
    }
}
```

이 구조가 **REPL (Read-Eval-Print Loop)** 의 기본형이다. `jshell`, 파이썬 인터프리터, `bash` 모두 같은 골격 — 입력 → 처리 → 다시 입력. 다만 이 예제는 "처리" 부분이 단순한 상태 변경(`switch`) 일 뿐이고, 결과 표시는 별도 timer 스레드가 매초 현재 줄을 `\r` 로 다시 그린다.

**main 과 timer 모두 redraw 를 부른다 — 출력 직렬화는 어떻게 하나**:

두 스레드(main 의 명령 직후 호출, timer 의 매초 호출)가 모두 `redraw()` 를 부르므로, `synchronized (SCREEN_LOCK)` 으로 출력 한 덩어리가 통째로 나가도록 묶는다. Ex02 의 `SCREEN_LOCK` 과 같은 역할이다. 부작용은 없다 — 락 구간이 짧고 (한 줄 printf), 입력 후 즉시 redraw 가 일어나 응답 지연도 없다.

**시각적 제약 — 알아두면 좋은 점**:

매초 `\r` 로 현재 줄을 덮어쓰므로, 사용자가 타이핑 중인 순간에 tick 이 발생하면 **이미 친 글자가 잠깐 묻혔다가 다시 보이는** 흔들림이 생길 수 있다. Scanner 는 stdin 버퍼에서 정확히 읽기 때문에 명령 처리 자체는 정상이지만, 시각적으로는 어색해 보일 수 있다. 학생에게는 "한 명령 빠르게 친 뒤 Enter" 로 안내. Ex02 와 같은 단순한 `\r` 갱신 패턴의 trade-off 다.

> 이 흔들림이 거슬리면 Jansi / JLine 같은 라이브러리로 "위쪽 고정 타이머 + 아래쪽 입력 영역" TUI 를 만들 수 있지만, 그러면 챕터의 초점이 스레드에서 터미널 제어로 옮겨간다. 입문 단계에서는 **표준 라이브러리만으로 어떤 콘솔에서도 그대로 동작하는** 가장 단순한 형태를 일부러 택했다.

**Ex02 에서 못 본 새 도구**:

| 도구 | 역할 |
|---|---|
| `volatile` 키워드 | 변수를 여러 스레드가 안전하게 공유할 수 있도록, 한쪽에서 쓴 값을 다른 쪽이 곧바로 보게 한다. |
| `Scanner.nextLine()` | 표준 입력에서 한 줄이 들어올 때까지 호출한 스레드를 멈춘다. 다른 스레드는 영향 없음. |
| `Scanner.hasNextLine()` | 다음 줄 존재 검사 (블로킹). EOF 시 `false`. |
| `thread.interrupt()` | 다른 스레드에 "그만!" 신호. `sleep` 중이면 `InterruptedException` 으로 깨어남. |
| `switch (cmd)` | 명령에 따라 다른 상태 전이를 일으키는 간단한 상태 머신 |
| `System.out.printf` / `%02d` | 두 자리 0 채움 정수 서식. `00:00` 형식 만들기. |

**종료 흐름 (자바 표준 interruption protocol)**:

1. 사용자가 `quit` 입력 → `Scanner.nextLine()` 반환 → main 이 루프 탈출
2. main 이 `timer.interrupt()` → timer 의 `Thread.sleep` 이 `InterruptedException` 던지며 깨어남
3. timer 의 `catch` 블록이 잡고 루프를 빠져나감 → timer 종료
4. main 의 `timer.join()` 이 반환 → main 이 `"Bye."` 출력 후 종료

## 실습 체크리스트

- `Ex01` 을 실행해 진행바 30칸이 차례로 채워지는 것을 확인했다.
- `Ex01` 의 `\r` 을 `\n` 으로 바꿔서 실행해, 줄이 계속 새로 생기는 모습을 보고 `\r` 이 무엇을 하는지 손으로 확인했다.
- `Ex01` 의 `System.out.flush()` 한 줄을 잠깐 지워 보고, 갱신이 묶여서 끝에 한 번에 보이는 현상을 확인했다 (환경에 따라 다를 수 있음).
- `Ex02` 를 여러 번 실행해 두 워커의 도착 시점이 매번 달라지는 것을 확인했다.
- `Ex02` 에서 `tA.join(); tB.join();` 두 줄을 잠깐 지우고 실행해, `"Both finished."` 메시지가 진행바 사이에 끼어드는 모습을 직접 봤다.
- `Ex02` 에서 `tA.start()` / `tB.start()` 를 `tA.run()` / `tB.run()` 으로 바꿔 보고, 동시 진행이 사라지고 차례대로 끝나는 것을 확인했다.
- `Ex03` 을 실행해 초기 상태 `[00:00 STOPPED] >` 가 매초 같은 줄에서 갱신되는 것을 확인했다.
- `Ex03` 에서 `start` 입력 → 카운터가 매초 증가하고 `[RUNNING]` 으로 바뀌는 것을 확인했다.
- `Ex03` 에서 `stop` 입력 → 카운터가 멈추고 `[STOPPED]` 로 바뀌지만 경과 시간은 유지되는 것을 확인했다.
- `Ex03` 에서 `reset` 입력 → 카운터가 `00:00` 으로 초기화되는 것을 확인했다 (running 상태와 무관).
- `Ex03` 에서 알 수 없는 단어(예: `foo`)를 입력해 보고, 그냥 무시되며 다음 입력을 기다리는 것을 확인했다.
- `Ex03` 의 `volatile` 키워드를 잠깐 두 변수에서 빼고 컴파일·실행해, 동작 자체는 비슷해 보이지만 "안 보장된다"는 점을 설명할 수 있다 (실제 차이는 환경 의존이라 매번 재현되진 않는다).
- `Ex03` 에서 `timer.interrupt()` 를 잠깐 지워 보고, `quit` 을 입력해도 `Bye.` 가 한 박자 늦게 나오는 모습을 확인했다.

## 퀴즈 예시

- `\r` 과 `\n` 은 어떻게 다른가?
- `System.out.flush()` 는 왜 필요한가?
- `start()` 와 `run()` 의 차이는 무엇인가?
- `join()` 이 없으면 어떤 문제가 생길 수 있는가?
- 같은 코드를 여러 번 실행했을 때 결과가 매번 달라지는 이유는 무엇인가?
- `ThreadLocalRandom.current().nextInt(60)` 은 어떤 범위의 정수를 돌려주는가? `new Random()` 대신 이걸 쓰는 이유는?
- `Thread.sleep(1000)` 은 어떤 스레드를 멈추는가? 다른 스레드도 같이 멈추는가?
- `Scanner.nextLine()` 이 main 을 멈추는 동안에도 timer 스레드는 왜 계속 도는가?
- `timer.interrupt()` 가 하는 일은 무엇인가? `InterruptedException` 은 누가 던지는가?
- `Ex03` 의 `running` 과 `elapsedSeconds` 에 `volatile` 을 붙인 이유는 무엇인가?
- `Ex03` 에서 `stop` 후 `reset` 을 누르면 표시는 어떻게 바뀌는가? (상태와 카운터가 각각 어떻게 되는지)

## 추천 추가 실습

- `Ex01` 에서 진행 단위(현재 `1 + rng.nextInt(2)`)와 슬립 시간을 조절해 빠른 / 느린 진행바를 만들어 보기
- `Ex01` 의 진행바 모양을 `[#####.....]` 처럼 다른 문자로 꾸며 보기
- `Ex02` 의 워커 수를 3개로 늘려 보기 (`progress` 배열 크기와 `redraw()` 안의 진행바 출력 부분만 늘리면 된다)
- `Ex02` 에서 두 워커의 평균 속도(`avgSleep`)를 똑같이 맞춰 보고, 그래도 도착 시점이 다른지 관찰하기 (흔들림 jitter 때문에 차이가 난다)
- `Ex03` 에 `pause` / `resume` 을 `stop` / `start` 의 별칭으로 추가해 명령 어휘 늘리기
- `Ex03` 에 `status` 명령 추가 — 입력하면 현재 경과 시간과 상태를 한 줄로 즉시 출력 (다음 timer tick 을 기다리지 않고 main 에서 직접 println)
- `Ex03` 에 `lap` 명령 추가 — 누르면 현재 경과 시간을 별도 줄로 출력. 타이머는 멈추지 않는다.
- `Ex03` 의 카운터를 초 단위 대신 100ms (0.1초) 단위로 만들어, `mm:ss.s` 형식으로 표시하기 (tick 간격과 표시 단위를 같이 조정해야 함)
- `Ex03` 에서 시간 단위가 분(60초) 을 넘기면 `HH:MM:SS` 로 자동 확장하기
