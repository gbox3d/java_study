# Week 07 - 모던 자바

## 학습 목표
- 제네릭 타입 안정성 이해
- 람다식으로 코드 간결화
- 스트림 파이프라인 처리

## 예제 클래스
- 패키지: chapter07
- 클래스: ex01, ex02, ex03

## 이번 주 핵심 개념
- 제네릭은 타입을 일반화해 재사용성을 높인다.
- 람다식은 "이름 없는 짧은 함수"처럼 동작을 표현하는 문법이다.
- 메서드 참조는 람다를 더 간단히 표현하는 방법이다.
- 스트림은 데이터 변환 과정을 선언형으로 연결해 쓸 수 있게 해준다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter07/ex01.java
javac -encoding UTF-8 -d out/classes app/chapter07/ex02.java
javac -encoding UTF-8 -d out/classes app/chapter07/ex03.java

java -cp out/classes chapter07.ex01
java -cp out/classes chapter07.ex02
java -cp out/classes chapter07.ex03
```

소스 파일 하나만 바로 실행할 때:

```bash
java app/chapter07/ex01.java
java app/chapter07/ex02.java
java app/chapter07/ex03.java
```

## 예제별 설명

### ex01 - 제네릭 클래스
대상 소스: `app/chapter07/ex01.java`

이 예제는 하나의 박스 클래스가 여러 타입을 담을 수 있도록 일반화한 코드다.

```java
static class Box<T> {
    private T value;
```

- `T`는 타입 파라미터다.
- 실제 사용할 때 `String`, `Integer` 같은 구체 타입으로 바뀐다.

값 저장과 반환 메서드는 단순하다.

```java
void set(T value) {
    this.value = value;
}

T get() {
    return value;
}
```

- `set()`은 값을 저장
- `get()`은 같은 타입으로 돌려준다

사용 예시는 아래와 같다.

```java
Box<String> box = new Box<>();
box.set("generic value");
System.out.println(box.get());
```

- 이 박스는 `String` 전용 박스가 된다.
- 잘못된 타입을 넣는 실수를 컴파일 단계에서 막을 수 있다.

### ex02 - 람다식과 메서드 참조
대상 소스: `app/chapter07/ex02.java`

이 예제는 문자열 리스트를 정렬하고 출력하는 과정에서 람다와 메서드 참조를 보여준다.

```java
List<String> list = Arrays.asList("Apple", "Banana", "Cherry");
list.sort((a, b) -> b.compareTo(a));
```

- `Arrays.asList()`로 리스트를 만든다.
- `(a, b) -> ...`가 람다식이다.
- `b.compareTo(a)`이므로 역순 정렬이 된다.

출력은 메서드 참조다.

```java
list.forEach(System.out::println);
```

- `x -> System.out.println(x)`를 더 짧게 쓴 형태다.
- 람다와 메서드 참조의 차이를 비교해 보기 좋다.

### ex03 - 스트림 파이프라인
대상 소스: `app/chapter07/ex03.java`

이 예제는 숫자 리스트를 조건에 맞게 걸러내고 변환하는 스트림 예제다.

```java
List<Integer> result = values.stream()
        .filter(v -> v % 2 == 0)
        .map(v -> v * v)
        .toList();
```

- `stream()`으로 스트림 시작
- `filter()`로 짝수만 남김
- `map()`으로 제곱값으로 변환
- `toList()`로 다시 리스트로 모음

이 코드는 "반복문으로 직접 처리"하던 방식을 선언형으로 바꾼 예다.

## 실습 체크리스트

- `ex01`에서 `Box<String>`을 다른 타입으로 바꿔 테스트했다.
- `ex02`에서 정렬 기준을 바꿔 결과가 어떻게 달라지는지 확인했다.
- `ex03`에서 `filter()` 조건이나 `map()` 수식을 수정해 봤다.
- 람다식과 메서드 참조의 차이를 코드 예시로 설명할 수 있다.

## 퀴즈 예시
- `Box<T>`의 `T`는 언제 실제 타입이 되는가?
- 람다식 `(a, b) -> b.compareTo(a)`는 어떤 정렬 결과를 만드는가?
- 메서드 참조 `System.out::println`은 어떤 람다와 같은가?
- 스트림에서 `filter`와 `map`은 각각 무슨 역할을 하는가?

## 추천 추가 실습
- `Box<Integer>` 예제 추가하기
- 문자열 길이순 정렬 람다 작성하기
- 10보다 큰 수만 골라서 2배로 만드는 스트림 작성하기
- 스트림 없이 같은 로직을 반복문으로 다시 구현해 비교하기
