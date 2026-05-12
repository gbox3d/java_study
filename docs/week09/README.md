# Week 09 - 스레드와 진행바

## 학습 목표

- 콘솔 한 줄을 다시 그려 진행 상태를 보여 주는 **기본 틀**을 안다 (`\r`, `flush`, `Thread.sleep`)
- 같은 패턴을 **여러 스레드**에 적용해 동시 진행을 시각화한다
- `new Thread(Runnable, name).start()` 로 새 스레드를 띄우고, `join()` 으로 종료를 기다린다
- `start()` 와 `run()` 직접 호출의 차이를 안다

## 예제 클래스

- 패키지: `chapter09`
- 클래스: `Ex01` (진행바 기본 틀, 단일 스레드), `Ex02` (스레드 두 개 동시 진행)

## 이번 주 핵심 개념

- 스레드는 한 프로그램 안에서 동시에 진행될 수 있는 작업 흐름의 단위다.
- `Runnable` 은 "이 작업을 스레드로 실행할 수 있다"는 표현. 람다 `() -> { ... }` 로 짧게 적을 수 있다.
- `start()` 는 새 스레드를 실제로 띄운다. `run()` 을 직접 부르면 그냥 같은 스레드에서 메서드를 호출한 것이다 — 동시 진행이 되지 않는다.
- `join()` 은 호출한 쪽이 그 스레드가 끝날 때까지 멈춰 기다리게 한다. 없으면 main 이 먼저 끝나 출력이 어지러워질 수 있다.
- 진행바 같은 시각화는 콘솔에서 "같은 자리에 다시 그리기" 기법으로 만든다. 이번 주차에서는 단일 줄(`\r`)과 여러 줄(ANSI escape) 두 가지를 본다.

## 실행 방법

```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter09/*.java

java -cp out/classes chapter09.Ex01   # 단일 스레드 진행바 기본 틀
java -cp out/classes chapter09.Ex02   # 스레드 2개 동시 진행
```

권장 셸: **PowerShell 7**, **Windows Terminal**, **VSCode 통합 터미널**. Ex02 는 ANSI escape 를 쓰므로 구형 cmd.exe 에서는 깨질 수 있다 (Ex01 은 `\r` 만 쓰므로 어디서나 잘 보인다).

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

`Ex01` 의 진행바 패턴을 그대로 두 개의 스레드에 옮긴다. 두 워커가 동시에 진행되는 모습이 두 줄의 진행바 차오름으로 보인다.

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
| 진행바 줄 수 | 1 | 2 |
| 갱신 트릭 | `\r` (같은 줄 덮어쓰기) | ANSI `\033[2A` (커서 2줄 위로) + `\033[K` (줄 끝까지 지우기) |
| 출력 직렬화 | 필요 없음 | `synchronized (SCREEN_LOCK)` 으로 동시 출력 충돌 방지 |
| 진행률 변수 | 단일 `int` | `int[2]` |
| 난수 생성기 | `ThreadLocalRandom` (단일 스레드라 효과는 미미) | `ThreadLocalRandom` (스레드마다 독립 상태로 경합 회피) |

전형적인 실행 결과:

```
  Two threads in parallel  (avgSleep: A=42ms, B=63ms)

  worker-A |=========================>    |  87%
  worker-B |================>             |  55%

  Both finished.
```

> 진행률 변수는 평범한 `int[]` 로 공유한다 — 이 예제는 "스레드가 동시에 돈다"는 사실 자체에 집중하기 위함. 두 워커가 자기 칸만 쓰고 다른 칸을 안 쓰므로 큰 문제는 없지만, 엄격한 동시성 안전(메모리 가시성, 락) 처리는 별도 학습 주제다.

## 실습 체크리스트

- `Ex01` 을 실행해 진행바 30칸이 차례로 채워지는 것을 확인했다.
- `Ex01` 의 `\r` 을 `\n` 으로 바꿔서 실행해, 줄이 계속 새로 생기는 모습을 보고 `\r` 이 무엇을 하는지 손으로 확인했다.
- `Ex01` 의 `System.out.flush()` 한 줄을 잠깐 지워 보고, 갱신이 묶여서 끝에 한 번에 보이는 현상을 확인했다 (환경에 따라 다를 수 있음).
- `Ex02` 를 여러 번 실행해 두 워커의 도착 시점이 매번 달라지는 것을 확인했다.
- `Ex02` 에서 `tA.join(); tB.join();` 두 줄을 잠깐 지우고 실행해, `"Both finished."` 메시지가 진행바 사이에 끼어드는 모습을 직접 봤다.
- `Ex02` 에서 `tA.start()` / `tB.start()` 를 `tA.run()` / `tB.run()` 으로 바꿔 보고, 동시 진행이 사라지고 차례대로 끝나는 것을 확인했다.

## 퀴즈 예시

- `\r` 과 `\n` 은 어떻게 다른가?
- `System.out.flush()` 는 왜 필요한가?
- `start()` 와 `run()` 의 차이는 무엇인가?
- `join()` 이 없으면 어떤 문제가 생길 수 있는가?
- 같은 코드를 여러 번 실행했을 때 결과가 매번 달라지는 이유는 무엇인가?
- `ThreadLocalRandom.current().nextInt(60)` 은 어떤 범위의 정수를 돌려주는가? `new Random()` 대신 이걸 쓰는 이유는?

## 추천 추가 실습

- `Ex01` 에서 진행 단위(현재 `1 + rng.nextInt(4)`)와 슬립 시간을 조절해 빠른 / 느린 진행바를 만들어 보기
- `Ex01` 의 진행바 모양을 `[#####.....]` 처럼 다른 문자로 꾸며 보기
- `Ex02` 의 워커 수를 3개로 늘려 보기 (`progress` 배열 크기와 `\033[NA` 의 N 만 바꾸면 된다)
- `Ex02` 에서 두 워커의 평균 속도(`avgSleep`)를 똑같이 맞춰 보고, 그래도 도착 시점이 다른지 관찰하기 (흔들림 jitter 때문에 차이가 난다)
