# Week 03 - 객체지향 I

## 학습 목표
- 클래스와 객체 모델링
- 생성자/this 활용
- 캡슐화와 접근 제어자 적용

## 예제 클래스
- 패키지: chapter03
- 클래스: ex01, ex02, ex03

## 이번 주 핵심 개념
- 클래스는 객체를 만들기 위한 설계도다.
- 객체는 클래스 설계도를 바탕으로 실제로 생성된 데이터 단위다.
- 생성자는 객체가 처음 만들어질 때 필요한 초기값을 넣는다.
- 캡슐화는 데이터를 안전하게 다루기 위해 외부 접근을 제어하는 방식이다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter03/ex01.java
javac -encoding UTF-8 -d out/classes app/chapter03/ex02.java
javac -encoding UTF-8 -d out/classes app/chapter03/ex03.java

java -cp out/classes chapter03.ex01
java -cp out/classes chapter03.ex02
java -cp out/classes chapter03.ex03
```

소스 파일 하나만 바로 실행할 때:

```bash
java app/chapter03/ex01.java
java app/chapter03/ex02.java
java app/chapter03/ex03.java
```

## 예제별 설명

### ex01 - 클래스와 객체의 기본 형태
대상 소스: `app/chapter03/ex01.java`

이 예제는 `Student` 클래스를 정의하고 객체를 생성하는 가장 기본적인 형태다.

```java
static class Student {
    String name;
    int age;
```

- `Student`는 학생 한 명을 표현하는 클래스다.
- `name`, `age`는 객체가 가지는 상태다.

생성자는 객체 생성 시 초기값을 넣는다.

```java
Student(String name, int age) {
    this.name = name;
    this.age = age;
}
```

- 왼쪽 `this.name`은 객체의 필드다.
- 오른쪽 `name`은 생성자 매개변수다.
- `this`를 붙여 "현재 객체의 필드"임을 구분한다.

객체 사용은 `main()`에서 확인한다.

```java
Student student = new Student("Alice", 20);
student.printInfo();
```

- `new Student(...)`가 실제 객체를 만든다.
- `student`는 객체를 가리키는 참조 변수다.
- `printInfo()`는 객체의 데이터를 출력하는 메서드다.

### ex02 - 생성자 오버로딩과 `this()`
대상 소스: `app/chapter03/ex02.java`

이 예제는 같은 클래스에서 생성자를 여러 개 둘 수 있다는 점을 보여준다.

```java
Student() {
    this("Unknown", 20);
}
```

- 매개변수가 없는 기본 생성자다.
- 내부에서 다른 생성자를 다시 호출한다.
- `this("Unknown", 20)`는 같은 클래스의 다른 생성자를 부르는 문법이다.

두 번째 생성자는 실제 초기화 작업을 담당한다.

```java
Student(String name, int age) {
    this.name = name;
    this.age = age;
}
```

출력은 `toString()` 재정의를 통해 보기 좋게 만든다.

```java
@Override
public String toString() {
    return "Student{name='" + name + "', age=" + age + "}";
}
```

- 객체를 그냥 출력해도 사람이 읽기 쉬운 문자열이 나온다.
- 자바에서 디버깅과 출력에 매우 자주 쓰는 패턴이다.

### ex03 - 캡슐화와 접근 제어자
대상 소스: `app/chapter03/ex03.java`

이 예제는 계좌 잔액을 외부에서 직접 건드리지 못하게 막는 구조를 보여준다.

```java
private int balance;
```

- `private`이므로 클래스 바깥에서는 직접 접근할 수 없다.
- 즉 `account.balance = -1000;` 같은 위험한 코드가 막힌다.

입금은 메서드를 통해서만 가능하다.

```java
void deposit(int amount) {
    if (amount > 0) {
        balance += amount;
    }
}
```

- 0보다 큰 값만 허용한다.
- 잘못된 값은 무시한다.

출금은 성공 여부를 `boolean`으로 돌려준다.

```java
boolean withdraw(int amount) {
    if (amount <= 0 || balance < amount) {
        return false;
    }
    balance -= amount;
    return true;
}
```

- 조건을 만족하지 못하면 `false`
- 출금이 완료되면 `true`
- 메서드 결과로 성공/실패를 알려주는 좋은 예다.

잔액 확인은 getter 메서드를 사용한다.

```java
int getBalance() {
    return balance;
}
```

## 실습 체크리스트

- `ex01`에서 학생 이름과 나이를 바꿔 객체 생성 결과를 확인했다.
- `ex02`에서 기본 생성자와 매개변수 생성자의 차이를 직접 실행으로 비교했다.
- `ex03`에서 입금/출금 금액을 바꿔 성공과 실패 결과를 모두 확인했다.
- `balance`를 직접 바꾸지 못하는 이유를 코드에서 설명할 수 있다.

## 퀴즈 예시
- 객체를 만들 때 `new`가 하는 일은 무엇인가?
- `this.name = name;`에서 왼쪽과 오른쪽 `name`은 무엇이 다른가?
- `this()`는 언제 사용하는가?
- 왜 `balance`를 `private`으로 두는 것이 좋은가?

## 추천 추가 실습
- `Student` 클래스에 학번 필드 추가하기
- `ex02`에 세 번째 생성자 추가하기
- `BankAccount`에 송금 메서드 만들기
- 잘못된 입금/출금 시 안내 문구 출력하기
