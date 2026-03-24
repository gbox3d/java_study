# Week 04 - 객체지향 II

## 학습 목표
- 상속과 오버라이딩 이해
- 다형성 기반 처리
- 인터페이스 다중 구현

## 예제 클래스
- 패키지: chapter04
- 클래스: ex01, ex02, ex03

## 이번 주 핵심 개념
- 상속은 기존 클래스의 속성과 기능을 물려받아 확장하는 방법이다.
- 오버라이딩은 부모의 메서드를 자식이 다시 정의하는 것이다.
- 다형성은 같은 타입으로 여러 객체를 다르게 다루는 개념이다.
- 인터페이스는 "무엇을 할 수 있는가"를 규약으로 표현한다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter04/ex01.java
javac -encoding UTF-8 -d out/classes app/chapter04/ex02.java
javac -encoding UTF-8 -d out/classes app/chapter04/ex03.java

java -cp out/classes chapter04.ex01
java -cp out/classes chapter04.ex02
java -cp out/classes chapter04.ex03
```

소스 파일 하나만 바로 실행할 때:

```bash
java app/chapter04/ex01.java
java app/chapter04/ex02.java
java app/chapter04/ex03.java
```

## 예제별 설명

### ex01 - 상속과 오버라이딩
대상 소스: `app/chapter04/ex01.java`

이 예제는 `Employee`를 부모 클래스로, `Manager`를 자식 클래스로 둔 상속 구조다.

```java
static class Employee {
    protected String name;
```

- `Employee`는 공통 속성과 동작을 가진 부모 클래스다.
- `protected`는 자식 클래스에서 접근 가능하도록 한 접근 제어자다.

자식 클래스는 `extends`로 상속받는다.

```java
static class Manager extends Employee {
    Manager(String name) {
        super(name);
    }
```

- `super(name)`은 부모 생성자를 호출한다.
- 부모가 가진 `name` 초기화 책임을 그대로 재사용한다.

메서드 재정의는 이렇게 이루어진다.

```java
@Override
void work() {
    System.out.println(name + " manages team");
}
```

- 부모의 `work()`를 자식 상황에 맞게 다시 구현했다.
- 이것이 오버라이딩이다.

### ex02 - 다형성
대상 소스: `app/chapter04/ex02.java`

이 예제는 부모 타입 하나로 여러 자식 객체를 다루는 다형성 예제다.

```java
Animal[] animals = {new Dog(), new Cat()};
for (Animal animal : animals) {
    animal.sound();
}
```

- 배열 타입은 `Animal[]`이지만 실제 객체는 `Dog`, `Cat`이다.
- 같은 `sound()` 호출이라도 실제 객체에 따라 결과가 달라진다.
- 실행 시점에 어떤 메서드가 호출될지 결정되는 것이 다형성의 핵심이다.

### ex03 - 인터페이스 다중 구현
대상 소스: `app/chapter04/ex03.java`

이 예제는 클래스가 여러 인터페이스를 동시에 구현할 수 있음을 보여준다.

```java
interface Movable {
    void move();
}

interface Attackable {
    void attack();
}
```

- 인터페이스는 구현이 아니라 기능 규약이다.
- "이 메서드는 반드시 있어야 한다"는 약속 역할을 한다.

구현 클래스는 다음처럼 만든다.

```java
static class Hero implements Movable, Attackable
static class Monster implements Movable, Attackable
```

- `Hero`, `Monster`는 둘 다 이동과 공격이 가능하다.
- 그래서 두 인터페이스를 모두 구현한다.

이 예제에서 꼭 이해해야 할 포인트:
- 상속은 공통 코드를 재사용하기 좋다.
- 오버라이딩은 같은 메서드 이름으로 다른 동작을 만들게 해준다.
- 인터페이스는 역할 중심 설계에 적합하다.

## 실습 체크리스트

- `ex01`에서 부모 메서드와 자식 메서드 출력 차이를 확인했다.
- `ex02`에서 `Dog`, `Cat` 외에 새 동물 클래스를 하나 추가해 봤다.
- `ex03`에서 인터페이스가 클래스와 어떤 점이 다른지 설명할 수 있다.
- 부모 타입으로 여러 자식 객체를 다루는 이유를 말할 수 있다.

## 퀴즈 예시
- `super(name)`은 왜 필요한가?
- `Employee manager = new Manager("Chris");`가 가능한 이유는 무엇인가?
- `animal.sound()` 호출 시 어떤 메서드가 실행되는가?
- 인터페이스와 상속은 어떤 상황에서 각각 유용한가?

## 추천 추가 실습
- `Employee`에 `salary` 필드 추가하기
- `Animal`에 `name`을 넣고 출력에 반영하기
- `Defendable` 인터페이스를 추가해 `Hero`, `Monster`에 구현하기
- 부모 타입 배열로 여러 객체를 저장해 공통 처리하기
