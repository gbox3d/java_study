# Week 09 - 멀티스레딩 및 동기화

## 학습 목표
- 스레드 생성과 join 사용
- synchronized 동기화 처리
- ExecutorService 스레드 풀 활용

## 예제 클래스
- 패키지: chapter09
- 클래스: ex01, ex02, ex03

## 이번 주 핵심 개념
- 스레드는 한 프로그램 안에서 여러 작업 흐름을 동시에 실행하는 단위다.
- `Runnable`은 스레드가 해야 할 일을 담는 방식이다.
- 공유 자원에 여러 스레드가 동시에 접근하면 경쟁 상태가 발생할 수 있다.
- 스레드 풀은 많은 작업을 효율적으로 처리하기 위한 관리 방식이다.
- 이 주차는 응용 성격이 강하므로 구현 완성보다 실행 흐름 이해를 우선한다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter09/ex01.java
javac -encoding UTF-8 -d out/classes app/chapter09/ex02.java
javac -encoding UTF-8 -d out/classes app/chapter09/ex03.java

java -cp out/classes chapter09.ex01
java -cp out/classes chapter09.ex02
java -cp out/classes chapter09.ex03
```

소스 파일 하나만 바로 실행할 때:

```bash
java app/chapter09/ex01.java
java app/chapter09/ex02.java
java app/chapter09/ex03.java
```

## 예제별 설명

### ex01 - 스레드 생성과 `join()`
대상 소스: `app/chapter09/ex01.java`

이 예제는 `Runnable` 구현 객체를 두 개의 스레드에서 실행한다.

```java
static class NumberTask implements Runnable
```

- `Runnable`은 "이 작업을 스레드로 실행할 수 있다"는 의미다.
- 실제 작업 내용은 `run()` 안에 들어간다.

`run()` 내부는 숫자 출력 반복이다.

```java
for (int i = 1; i <= 10; i++) {
    System.out.println(label + " -> " + i);
    Thread.sleep(30);
}
```

- `label`로 어느 스레드 출력인지 구분한다.
- `Thread.sleep(30)`은 잠깐 쉬게 해서 실행이 섞여 보이도록 만든다.

스레드 시작과 대기는 아래 코드다.

```java
t1.start();
t2.start();

t1.join();
t2.join();
```

- `start()`는 새 스레드 시작
- `join()`은 해당 스레드가 끝날 때까지 기다림
- `join()`이 없으면 메인 흐름이 먼저 끝날 수 있다

### ex02 - 공유 자원과 `synchronized`
대상 소스: `app/chapter09/ex02.java`

이 예제는 두 스레드가 같은 계좌에서 동시에 출금하려는 상황을 다룬다.

```java
private int balance = 1000;
```

- `balance`는 여러 스레드가 함께 접근하는 공유 자원이다.

핵심은 출금 메서드 선언이다.

```java
public synchronized void withdraw(int amount)
```

- 한 번에 한 스레드만 이 메서드에 들어오게 만든다.
- 이 키워드가 없으면 잔액 계산이 꼬일 가능성이 높다.

중간의 `Thread.sleep(50)`은 의도적으로 경쟁 상황을 잘 드러내기 위한 장치다.

- 실제 은행 시스템에서는 이런 지연이 네트워크/DB 처리 등으로 자연스럽게 생길 수 있다.
- 그래서 동기화가 중요하다.

### ex03 - 스레드 풀
대상 소스: `app/chapter09/ex03.java`

이 예제는 직접 스레드를 계속 생성하지 않고, 미리 준비된 작업자 풀에 작업을 맡긴다.

```java
ExecutorService pool = Executors.newFixedThreadPool(3);
```

- 동시에 최대 3개의 작업을 처리하는 스레드 풀이다.

작업 제출은 아래처럼 한다.

```java
for (int i = 1; i <= 10; i++) {
    int taskNo = i;
    pool.execute(() -> System.out.println(Thread.currentThread().getName() + " task-" + taskNo));
}
```

- 총 10개의 작업을 제출한다.
- 하지만 실제 실행 스레드는 3개만 돌아가며 재사용된다.

종료 처리는 매우 중요하다.

```java
pool.shutdown();
if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
    pool.shutdownNow();
}
```

- `shutdown()`으로 새 작업 접수 종료
- 일정 시간 기다린 뒤
- 끝나지 않으면 강제 종료 시도

## 실습 체크리스트

- `ex01`을 여러 번 실행해 출력 순서가 달라질 수 있음을 확인했다.
- `ex02`에서 두 스레드가 같은 계좌를 공유한다는 점을 설명할 수 있다.
- `ex03`에서 스레드 풀 크기를 바꿔 실행 결과를 비교해 봤다.
- `start()`, `join()`, `synchronized`의 역할을 각각 구분할 수 있다.

## 퀴즈 예시
- `start()`와 `run()`은 무엇이 다른가?
- `join()`이 없으면 어떤 문제가 생길 수 있는가?
- `synchronized`가 필요한 이유는 무엇인가?
- 스레드를 직접 만드는 방식과 스레드 풀은 어떤 차이가 있는가?

## 추천 추가 실습
- `ex01`에서 스레드 3개로 늘리기
- `ex02`에서 입금 메서드도 추가하기
- `ex03`에서 풀 크기를 1, 2, 5로 바꿔 출력 비교하기
- 작업 완료 후 `"all done"` 메시지 출력하기
