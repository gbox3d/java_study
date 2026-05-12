# Week 02 - 배열과 메서드

## 학습 목표
- 배열 선언/초기화와 순회
- 메서드 분리와 가변 인자 사용
- 배열 정렬 알고리즘 구현

## 예제 클래스
- 패키지: chapter02
- 클래스: Ex01, Ex02, Ex03

## 이번 주 핵심 개념
- 배열은 같은 타입의 값을 순서대로 묶어 저장하는 자료구조다.
- 메서드는 반복되는 동작을 이름 붙여 분리하는 도구다.
- 배열을 메서드에 넘기면 메서드 안에서 원본 값을 바꿀 수도 있다.
- `int... numbers` 같은 가변 인자는 "개수가 정해지지 않은 값들"을 배열처럼 받는 문법이다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter02/Ex01.java
javac -encoding UTF-8 -d out/classes app/chapter02/Ex02.java
javac -encoding UTF-8 -d out/classes app/chapter02/Ex03.java

java -cp out/classes chapter02.Ex01
java -cp out/classes chapter02.Ex02
java -cp out/classes chapter02.Ex03
```

소스 파일 하나만 바로 실행할 때:

```bash
java app/chapter02/Ex01.java
java app/chapter02/Ex02.java
java app/chapter02/Ex03.java
```

## 예제별 설명

### Ex01 - 배열 선언, 출력, 순회
대상 소스: `app/chapter02/Ex01.java`

이 예제는 배열을 만들고, 같은 배열을 서로 다른 방식으로 출력해 보는 코드다.

```java
int[] scores = {90, 85, 95};
```

- `int[]`는 정수 배열 타입이다.
- `{90, 85, 95}`는 배열 생성과 초기화를 한 번에 수행한다.
- `scores[0]`, `scores[1]`, `scores[2]`에는 각각 90, 85, 95가 들어 있다.

이 다음 줄이 중요한 비교 포인트다.

```java
System.out.println("array object: " + scores);
System.out.println("array values: " + Arrays.toString(scores));
```

- 그냥 `scores`를 출력하면 사람이 읽기 좋은 값 목록이 아니라 배열 객체 정보가 출력된다.
- `Arrays.toString(scores)`를 쓰면 `[90, 85, 95]`처럼 실제 값이 보인다.
- 그래서 배열 내용을 확인할 때는 `Arrays.toString()`이 매우 자주 쓰인다.

마지막 부분은 향상된 for문이다.

```java
for (int score : scores) {
    System.out.print(score + " ");
}
```

- `scores` 배열의 값을 앞에서부터 하나씩 꺼내서 `score`에 넣는다.
- 인덱스를 직접 쓰지 않아도 되기 때문에 읽기 쉽다.
- 배열의 모든 값을 단순 순회할 때 가장 자주 쓰는 형태다.

실행 결과 예시:

```text
array object: [I@...
array values: [90, 85, 95]
90 85 95
```

이 예제에서 학생이 꼭 이해해야 할 포인트:
- 배열 변수 자체와 배열 안의 값은 다르다.
- 배열 전체 출력은 `Arrays.toString()`으로 확인한다.
- 단순 순회는 향상된 for문이 편하다.

### Ex02 - 메서드 분리와 가변 인자
대상 소스: `app/chapter02/Ex02.java`

이 예제는 여러 숫자의 합을 계산하는 기능을 `sum()` 메서드로 분리한 코드다.

```java
static int sum(int... numbers)
```

- `static`이므로 객체 생성 없이 `main()`에서 바로 호출할 수 있다.
- 반환 타입 `int`는 계산 결과가 정수라는 뜻이다.
- `int... numbers`는 가변 인자 문법이다.
- 호출할 때 `sum(1, 2, 3)`처럼 여러 개를 넘기면 메서드 안에서는 `numbers`가 배열처럼 동작한다.

메서드 내부 흐름은 단순하다.

```java
int result = 0;
for (int n : numbers) {
    result += n;
}
return result;
```

- `result`를 0으로 시작한다.
- 전달받은 숫자를 하나씩 꺼내 누적한다.
- 반복이 끝나면 최종 합계를 반환한다.

호출 코드는 아래 한 줄이다.

```java
System.out.println("sum(1, 2, 3, 4, 5) = " + sum(1, 2, 3, 4, 5));
```

- 메서드 호출 결과가 문자열 뒤에 붙어 출력된다.
- 이 구조는 이후 평균 계산, 최댓값 찾기, 점수 총합 구하기 같은 문제로 바로 확장할 수 있다.

이 예제에서 학생이 꼭 이해해야 할 포인트:
- 메서드는 "기능 이름 붙이기"다.
- 매개변수는 입력, `return`은 출력이다.
- 가변 인자는 내부적으로 배열처럼 처리된다.

### Ex03 - 배열 정렬과 버블 정렬
대상 소스: `app/chapter02/Ex03.java`

이 예제는 배열을 오름차순으로 정렬하는 가장 기본적인 버블 정렬을 직접 구현한 코드다.

정렬 대상 배열:

```java
int[] data = {8, 3, 5, 1, 9};
```

핵심은 `sortArray(int[] arr)` 메서드다.

```java
for (int i = 0; i < arr.length - 1; i++) {
    for (int j = 0; j < arr.length - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
            int tmp = arr[j];
            arr[j] = arr[j + 1];
            arr[j + 1] = tmp;
        }
    }
}
```

바깥 반복문의 의미:
- 한 바퀴가 끝날 때마다 가장 큰 값 하나가 뒤쪽으로 이동한다.
- 그래서 다음 바퀴에서는 비교 범위를 하나 줄일 수 있다.

안쪽 반복문의 의미:
- 현재 위치 `j`와 바로 다음 위치 `j + 1`을 비교한다.
- 앞의 값이 더 크면 서로 자리를 바꾼다.

교환 코드의 의미:
- 값을 바로 덮어쓰면 기존 값이 사라지므로 임시 변수 `tmp`가 필요하다.
- 이 부분은 배열 문제에서 매우 자주 나오는 기본 패턴이다.

정렬이 끝난 뒤:

```java
System.out.println(Arrays.toString(data));
```

- 원본 배열 `data` 자체가 정렬된 상태로 바뀐다.
- 메서드가 배열을 받아 내부 값을 수정했기 때문이다.

실행 결과:

```text
[1, 3, 5, 8, 9]
```

이 예제에서 학생이 꼭 이해해야 할 포인트:
- 2중 반복문이 정렬 과정에서 왜 필요한지
- 인접한 두 값을 비교하고 교환하는 방식
- 배열은 메서드에 전달된 뒤 내용이 바뀔 수 있다는 점

## 실습 체크리스트

- `Ex01`에서 배열 값을 직접 바꾸고 출력 결과를 다시 확인했다.
- `Ex02`에서 `sum()` 호출 인자 개수를 바꿔 결과를 비교했다.
- `Ex03`에서 배열 데이터를 다른 값으로 바꿔 정렬 결과를 확인했다.
- `Ex03`을 내림차순 정렬로 바꾸는 시도를 해봤다.

## 퀴즈 예시
- `scores`를 그냥 출력했을 때 값 목록이 안 보이는 이유는 무엇인가?
- `sum(int... numbers)`에서 `numbers`는 메서드 안에서 어떤 형태로 다뤄지는가?
- `sortArray(data)` 호출 후 `data`가 바뀌는 이유는 무엇인가?
- 버블 정렬에서 안쪽 반복문의 범위가 `arr.length - i - 1`인 이유는 무엇인가?

## 추천 추가 실습
- `Ex01`을 바꿔서 합계와 평균까지 출력하기
- `Ex02`에 `max(int... numbers)` 메서드 추가하기
- `Ex03`을 내림차순 정렬로 바꾸기
- `Ex03`에서 비교가 한 번도 일어나지 않으면 조기 종료하도록 개선하기
