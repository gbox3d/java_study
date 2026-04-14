# Week 05 - 예외 처리 및 표준 API

## 학습 목표
- try-catch-finally 흐름 이해
- 사용자 정의 예외 작성
- 날짜 파싱, 검증, 계산용 표준 API 활용

## 예제 클래스
- 패키지: chapter05
- 클래스: ex01, ex02, ex03, ex04

## 이번 주 핵심 개념
- 예외는 프로그램 실행 중 발생하는 비정상 상황을 표현한다.
- `try-catch-finally`는 예외를 제어하고 복구하는 기본 구조다.
- 직접 예외 클래스를 만들면 의미 있는 오류 처리가 가능하다.
- 표준 API를 활용하면 날짜처럼 복잡한 데이터를 직접 계산하지 않고도 안전하게 처리할 수 있다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter05/ex01.java
javac -encoding UTF-8 -d out/classes app/chapter05/ex02.java
javac -encoding UTF-8 -d out/classes app/chapter05/ex03.java
javac -encoding UTF-8 -d out/classes app/chapter05/ex04.java

java -cp out/classes chapter05.ex01
java -cp out/classes chapter05.ex02
java -cp out/classes chapter05.ex03
java -cp out/classes chapter05.ex04
```

소스 파일 하나만 바로 실행할 때:

```bash
java app/chapter05/ex01.java
java app/chapter05/ex02.java
java app/chapter05/ex03.java
java app/chapter05/ex04.java
```

## 예제별 설명

### ex01 - `try-catch-finally`
대상 소스: `app/chapter05/ex01.java`

이 예제는 0으로 나누는 상황에서 예외가 어떻게 처리되는지 보여준다.

```java
static int divide(int a, int b) {
    return a / b;
}
```

- `b`가 0이면 `ArithmeticException`이 발생한다.
- 메서드 안에서는 따로 막지 않고, 호출한 쪽에서 처리한다.

예외 처리 구조는 아래와 같다.

```java
try {
    System.out.println(divide(10, 0));
} catch (ArithmeticException e) {
    System.out.println("error: " + e.getMessage());
} finally {
    System.out.println("finally block executed");
}
```

- `try`: 문제가 발생할 수 있는 코드
- `catch`: 해당 예외가 발생했을 때 실행할 코드
- `finally`: 예외 여부와 상관없이 마지막에 실행할 코드

실행 결과 예시:

```text
error: / by zero
finally block executed
```

- 예외가 발생해도 `finally`는 끝까지 실행된다.
- 자원 정리나 종료 메시지 출력에 자주 사용한다.

### ex02 - 사용자 정의 예외
대상 소스: `app/chapter05/ex02.java`

이 예제는 "나이 값이 이상하다"는 의미를 직접 예외 클래스로 표현한다.

```java
static class InvalidAgeException extends RuntimeException
```

- 자바가 기본 제공하는 예외만 쓰는 것이 아니라
- 우리 프로그램 상황에 맞는 예외를 직접 만들 수 있다.
- 이 예제는 정상 입력과 비정상 입력을 모두 검사한다.

검증 메서드는 이렇게 동작한다.

```java
static void checkAge(int age) {
    if (age < 0 || age > 150) {
        throw new InvalidAgeException("invalid age: " + age);
    }
}
```

- 조건이 잘못되면 `throw`로 예외를 직접 발생시킨다.
- 이렇게 하면 잘못된 입력을 즉시 차단할 수 있다.

실행 결과 예시:

```text
valid age: 25
invalid age: -5
```

- 정상 값은 그대로 통과한다.
- 비정상 값은 `InvalidAgeException`으로 의미 있게 구분된다.

### ex03 - 날짜 파싱과 검증
대상 소스: `app/chapter05/ex03.java`

이 예제는 문자열이 올바른 날짜인지 검사하는 전형적인 패턴이다.

```java
LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
```

- `"2026-02-22"` 같은 문자열을 날짜로 해석한다.
- 형식이 맞지 않거나 실제 달력상 존재하지 않는 날짜면 예외가 난다.

검증 메서드는 예외를 `boolean` 값으로 바꿔준다.

```java
try {
    LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
    return true;
} catch (DateTimeParseException e) {
    return false;
}
```

- 성공하면 `true`
- 실패하면 `false`
- 사용자 입력 검증에서 매우 자주 쓰이는 구조다.

실행 결과 예시:

```text
2026-02-22 valid? true
2026-02-30 valid? false
```

- 문자열이 형식에 맞아 보여도 실제 달력에 없는 날짜면 실패한다.
- 날짜 입력 검증은 예외를 잡아 논리값으로 바꾸는 패턴이 자주 쓰인다.

### ex04 - 날짜 계산
대상 소스: `app/chapter05/ex04.java`

이 예제는 `LocalDate`를 이용해 날짜를 더하고,
두 날짜 사이 차이를 계산하는 기본 패턴을 보여준다.

기준 날짜에서 며칠이나 몇 달 뒤를 구할 때는 아래처럼 쓴다.

```java
LocalDate after10Days = date.plusDays(10);
LocalDate after1Month = date.plusMonths(1);
```

- `plusDays(10)`: 10일 뒤 날짜 계산
- `plusMonths(1)`: 1개월 뒤 날짜 계산

두 날짜 사이 차이는 `ChronoUnit`으로 계산한다.

```java
long daysUntilEvent = ChronoUnit.DAYS.between(today, eventDate);
```

- `ChronoUnit.DAYS.between(...)`: 두 날짜 사이 일 수 계산

즉, `LocalDate`는 단순 저장용이 아니라 날짜 연산까지 자연스럽게 처리할 수 있다.

실행 결과 예시:

```text
base date: 2026-02-22
after 10 days: 2026-03-04
after 1 month: 2026-03-22
days between base and after 1 month: 28
days until event: 21
```

- 날짜 계산은 월 길이와 윤년 같은 규칙이 섞여 직접 구현하면 실수하기 쉽다.
- `LocalDate`와 `ChronoUnit`을 쓰면 이런 계산을 안전하게 처리할 수 있다.

## 실습 체크리스트

- `ex01`을 실행해 예외 메시지와 `finally` 출력 순서를 확인했다.
- `ex02`에서 정상 나이와 비정상 나이를 모두 테스트했다.
- `ex03`에서 유효한 날짜와 유효하지 않은 날짜를 직접 바꿔 넣어 봤다.
- `ex04`에서 `plusDays()`, `plusMonths()` 결과가 어떻게 바뀌는지 확인했다.
- `ex04`에서 두 날짜 사이 차이를 `ChronoUnit.DAYS.between()`으로 계산해 봤다.
- `throw`, `catch`, `finally`의 역할을 각각 설명할 수 있다.

권장 확인 포인트:

- 예외는 "오류 메시지 출력"이 아니라 "비정상 상황을 코드로 표현"하는 장치라는 점을 이해한다.
- 사용자 정의 예외는 프로그램 규칙을 더 분명하게 드러내는 데 도움이 된다.
- 날짜 검증과 계산은 표준 API를 쓰는 편이 직접 문자열을 다루는 것보다 훨씬 안전하다.

## 퀴즈 예시
- `finally`는 언제 실행되는가?
- `throw`와 `catch`의 역할 차이는 무엇인가?
- 사용자 정의 예외를 만드는 이유는 무엇인가?
- `"2026-02-30"`이 왜 잘못된 날짜로 처리되는가?
- `plusDays(10)`과 `plusMonths(1)`은 어떤 상황에서 유용한가?
- 두 날짜 차이를 계산할 때 왜 `ChronoUnit.DAYS.between()`을 쓰는가?

## 추천 추가 실습
- `divide()`에서 0 나누기 전에 직접 검사하기
- `InvalidScoreException` 같은 새 예외 클래스 만들기
- 날짜 형식을 `yyyy/MM/dd`로 바꿔 보기
- 특정 생일까지 남은 날짜 수 계산하기
- 오늘 기준 시험일까지 남은 주 수 계산하기
- 예외 메시지를 사용자 친화적으로 바꾸기
