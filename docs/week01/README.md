# Week 01 - 자바 기초 문법과 실행 흐름

## 학습 목표
- JVM, 컴파일(.java -> .class), 실행 흐름 이해
- 기본 입출력과 타입/변수 사용
- 조건문과 반복문 작성 능력 확보

## 예제 클래스
- 패키지: chapter01
- 클래스: Ex01, Ex02, Ex03, Ex04

## 이번 주 핵심 개념
- `main(String[] args)`는 자바 프로그램의 시작점이다.
- 변수는 값을 저장하고, 타입은 어떤 값을 저장할 수 있는지 결정한다.
- 반복문과 조건문은 프로그램 흐름을 제어하는 가장 기본 문법이다.
- 작은 예제를 통해 "입력 -> 처리 -> 출력" 구조를 반복해서 익히는 주차다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter01/Ex01.java
javac -encoding UTF-8 -d out/classes app/chapter01/Ex02.java
javac -encoding UTF-8 -d out/classes app/chapter01/Ex03.java
javac -encoding UTF-8 -d out/classes app/chapter01/Ex04.java

java -cp out/classes chapter01.Ex01
java -cp out/classes chapter01.Ex02
java -cp out/classes chapter01.Ex03
java -cp out/classes chapter01.Ex04
```

소스 파일 하나만 바로 실행할 때:

```bash
java app/chapter01/Ex01.java hello world
java app/chapter01/Ex02.java
java app/chapter01/Ex03.java
java app/chapter01/Ex04.java
```

## 예제별 설명

### Ex01 - 첫 실행과 명령행 인자
대상 소스: `app/chapter01/Ex01.java`

이 예제는 자바 프로그램의 가장 기본 형태를 보여준다.

```java
public static void main(String[] args)
```

- `main`은 JVM이 프로그램 시작점으로 찾는 메서드다.
- `String[] args`는 터미널에서 넘긴 값을 문자열 배열로 받는다.
- 예를 들어 `java app/chapter01/Ex01.java hello world`로 실행하면 `args[0]`은 `hello`, `args[1]`은 `world`가 된다.

출력 코드는 두 부분으로 나뉜다.

```java
System.out.println("Hello, Java World!");

for (int i = 0; i < args.length; i++) {
    System.out.println("args[" + i + "]: " + args[i]);
}
```

- 첫 줄은 고정 문자열 출력이다.
- 두 번째 부분은 `for`문으로 배열을 순회하며 인자 값을 하나씩 출력한다.
- `args.length`는 배열의 길이이고, `i`는 현재 인덱스다.

이 예제에서 꼭 이해해야 할 포인트:
- 프로그램은 `main()`부터 시작한다.
- 명령행 인자는 문자열 배열로 전달된다.
- 배열은 `length`를 사용해 순회한다.

### Ex02 - 변수, 형변환, 서식 출력
대상 소스: `app/chapter01/Ex02.java`

이 예제는 정수와 실수를 변수에 저장하고, 형식을 맞춰 출력하는 방법을 보여준다.

```java
int id = 2026001;
double gpa = 4.18;
```

- `int`는 정수형, `double`은 실수형이다.
- 자바는 변수 선언 시 타입을 먼저 적는다.

출력은 `System.out.printf()`를 사용한다.

```java
System.out.printf(
        Locale.US,
        "id: %d, gpa: %.2f (cast to int: %d)%n",
        id,
        gpa,
        (int) gpa
);
```

- `%d`는 정수, `%.2f`는 소수 둘째 자리까지 실수를 의미한다.
- `(int) gpa`는 강제 형변환으로, `4.18`을 `4`로 바꾼다.
- `Locale.US`는 숫자 형식이 일관되게 출력되도록 지정한 것이다.

이 예제에서 꼭 이해해야 할 포인트:
- 타입에 따라 저장 가능한 값이 달라진다.
- 실수에서 정수로 형변환하면 소수점 아래가 잘린다.
- `printf`는 출력 형식을 세밀하게 제어할 때 사용한다.

### Ex03 - 중첩 반복문으로 패턴 출력
대상 소스: `app/chapter01/Ex03.java`

이 예제는 별 피라미드를 만들면서 중첩 반복문을 연습하는 코드다.

```java
int height = 5;
```

- 피라미드 높이를 결정하는 변수다.
- 값만 바꾸면 출력 줄 수가 달라진다.

출력 구조는 반복문 세 개로 이루어진다.

```java
for (int i = 0; i < height; i++) {
    for (int s = 0; s < height - i - 1; s++) {
        System.out.print(" ");
    }
    for (int star = 0; star < i * 2 + 1; star++) {
        System.out.print("*");
    }
    System.out.println();
}
```

- 바깥 반복문 `i`는 현재 줄 번호 역할을 한다.
- 첫 번째 안쪽 반복문은 왼쪽 공백을 출력한다.
- 두 번째 안쪽 반복문은 별 개수를 계산해 출력한다.
- 마지막 `println()`은 줄바꿈이다.

이 예제에서 꼭 이해해야 할 포인트:
- 중첩 반복문은 "행과 열" 구조를 만들 때 자주 사용된다.
- 출력 순서가 곧 화면 모양을 결정한다.
- 수식 `height - i - 1`, `i * 2 + 1`이 모양을 만든다.

### Ex04 - 조건문과 제어문 비교
대상 소스: `app/chapter01/Ex04.java`

이 예제는 한 파일 안에서 `if-else`, `switch`, `continue`, `break`, 라벨드 `break`까지 한 번에 보여준다.

첫 번째 블록은 학점 계산이다.

```java
if (score >= 90) {
    grade = "A";
} else if (score >= 80) {
    grade = "B";
} else if (score >= 70) {
    grade = "C";
} else {
    grade = "D";
}
```

- 위에서 아래로 조건을 검사한다.
- 먼저 만족한 조건 하나만 실행된다.

두 번째 블록은 `switch expression`이다.

```java
String season = switch (month) {
    case 12, 1, 2 -> "winter";
    case 3, 4, 5 -> "spring";
    case 6, 7, 8 -> "summer";
    case 9, 10, 11 -> "autumn";
    default -> "invalid";
};
```

- 값 하나를 여러 경우와 비교할 때 읽기 쉽다.
- `->` 문법으로 결과 문자열을 바로 돌려준다.

세 번째 블록은 `continue`다.

```java
if (i % 2 == 0) {
    continue;
}
oddSum += i;
```

- 짝수면 아래 코드를 건너뛰고 다음 반복으로 넘어간다.
- 결과적으로 홀수만 더하게 된다.

마지막 블록은 `break`와 라벨드 `break` 비교다.

- 일반 `break`는 가장 안쪽 반복문만 종료한다.
- `break search;`는 `search:` 라벨이 붙은 바깥 반복문까지 종료한다.

이 예제에서 꼭 이해해야 할 포인트:
- 조건문은 서로 다른 흐름을 선택하게 해준다.
- `continue`는 이번 반복만 건너뛴다.
- `break`는 반복문을 끝내고, 라벨드 `break`는 바깥 반복문까지 끊을 수 있다.

## 실습 체크리스트

- `Ex01`을 인자를 바꿔 두 번 이상 실행했다.
- `Ex02`에서 숫자 값을 바꿔 형변환 결과를 확인했다.
- `Ex03`에서 `height` 값을 바꿔 출력 모양이 어떻게 달라지는지 확인했다.
- `Ex04`에서 `score`, `month` 값을 바꿔 조건문 결과를 직접 비교했다.

## 퀴즈 예시
- `args.length`는 어떤 의미인가?
- `(int) 4.18`의 결과가 왜 4인가?
- `Ex03`에서 별 개수를 `i * 2 + 1`로 계산하는 이유는 무엇인가?
- 일반 `break`와 라벨드 `break`의 차이는 무엇인가?

## 추천 추가 실습
- `Ex01`에서 인자가 없을 때 안내 문구 출력하기
- `Ex02`에서 이름, 학번, 평균 점수를 함께 출력하기
- `Ex03`을 역피라미드나 마름모로 바꾸기
- `Ex04`에서 점수 구간을 입력값에 따라 여러 번 테스트하기
