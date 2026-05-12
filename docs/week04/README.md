# Week 04 - 객체지향 II (RPG 캐릭터 테마)

## 학습 목표
- 상속과 오버라이딩 이해
- 추상 클래스(Abstract Class)와 추상 메서드의 개념
- 다형성 기반 처리
- 인터페이스 다중 구현

## 예제 클래스
- 패키지: `chapter04.ex01`, `chapter04.ex02`, `chapter04.ex03`
- 실행 클래스: `Ex01`, `Ex02`, `Ex03`

## 이번 주 핵심 개념
- **상속**은 기존 클래스의 공통 속성과 기능을 물려받아 새로운 직업(자식)을 만드는 방법이다.
- **오버라이딩**은 부모가 물려준 스킬(메서드)을 각 직업에 맞게 다시 정의하는 것이다.
- **추상 클래스**는 인스턴스로 만들 수 없는 뼈대 역할만 하며, 자식 클래스에게 특정 스킬의 구현을 강제한다.
- **다형성**은 부모 타입(Character, Enemy) 하나로 여러 자식 객체(Warrior, Mage, Slime, Dragon)를 똑같이 다루면서도 각자의 고유한 동작이 나가게 하는 마법이다.
- **인터페이스**는 "무엇을 할 수 있는가" 행동만 규약으로 정의하고, 종족과 관계없이 여러 역할을 부여한다.

## 실행 방법

예제들이 패키지 별로 분리되었기 때문에 상위 디렉토리(`app`)에서 컴파일 및 실행을 해야 합니다.

```bash
# 컴파일
javac -encoding UTF-8 -d out/classes app/chapter04/ex01/*.java
javac -encoding UTF-8 -d out/classes app/chapter04/ex02/*.java
javac -encoding UTF-8 -d out/classes app/chapter04/ex03/*.java

# 실행 (클래스패스를 out/classes로 지정 후 패키지명.클래스명 실행)
java -cp out/classes chapter04.ex01.Ex01
java -cp out/classes chapter04.ex02.Ex02
java -cp out/classes chapter04.ex03.Ex03
```

소스 파일을 바로 실행할 때 (Java 11 이상 지원 기능):
```bash
# 한 폴더 안의 같은 패키지 파일들이 상호 참조하는 경우 자바 소스 런처로 직접 실행하기 까다로울 수 있어, 
# 위의 javac, java 방식을 권장합니다.
```

---

## 예제별 설명

### Ex01 - 상속, 오버라이딩과 다형성
대상 소스: `app/chapter04/ex01/` (`Character`, `Warrior`, `Mage`, `Archer`, `Ex01`)

RPG 게임의 캐릭터 직업 파생을 통해 상속을 배웁니다.

```java
public class Character {
    protected String name;
    protected int hp;
```

- `Character`는 모든 직업의 기반이 되는 부모 클래스입니다.
- `protected`를 사용하여 패키지가 달라도 자식 클래스에서는 접근할 수 있게 합니다.

직업 클래스들은 `extends`로 상속을 받습니다.

```java
public class Warrior extends Character {
    private int rage;

    public Warrior(String name, int hp, int rage) {
        super(name, hp);
        this.rage = rage;
    }
```

- `super(name, hp)`는 부모(`Character`)의 생성자를 호출하여 공통 속성 초기화를 부모에게 맡깁니다.
- 이후 본인만의 특수 자원(`rage`)을 가집니다.

다형성은 메인 클래스에서 빛을 발합니다.
```java
Character[] party = { new Warrior(...), new Mage(...), new Archer(...) };
for(Character member : party) {
    member.attack(); 
}
```
- `Character` 타입으로 묶었지만, 실제로 `attack()`을 부르면 각 직업에 맞게 오버라이딩 된 스킬 모션이 나갑니다!

### Ex02 - 추상 클래스 (Abstract Class)
대상 소스: `app/chapter04/ex02/` (`Enemy`, `Slime`, `Dragon`, `Ex02`)

형체가 없는 몬스터를 방지하고 기능 구현을 강제합니다.

```java
public abstract class Enemy {
    // ...
    public abstract void attack();
    public abstract void takeDamage(int damage);
}
```

- `Enemy`는 추상 클래스이므로 `new Enemy()`로 소환이 불가능합니다.
- 슬라임이나 드래곤 같은 구체적인 몬스터만 소환할 수 있습니다.
- 자식 클래스는 부모의 `abstract` 메서드인 `attack()`과 `takeDamage()`를 **반드시** 오버라이딩 해야만 에러가 안 납니다.

### Ex03 - 인터페이스 다중 구현
대상 소스: `app/chapter04/ex03/` (`Movable`, `Attackable`, `Hero`, `Monster`, `Ex03`)

역할 중심의 설계, 여러 규약을 동시에 적용하는 방법입니다.

```java
public interface Movable { void move(); }
public interface Attackable { void attack(); }

public class Hero implements Movable, Attackable { ... }
public class Monster implements Movable, Attackable { ... }
```

- 인터페이스 안의 메서드는 구현부(`{}`)가 없는 껍데기입니다.
- 자바는 다중 상속(클래스 2개 동시 상속)이 불가능하지만, 인터페이스는 `,`로 여러 개를 동시에 구현(`implements`)할 수 있습니다.
- 용사와 몬스터는 적대 관계이지만 "움직이고", "공격한다"는 게임의 법칙을 똑같이 공유합니다.

---

## 실습 체크리스트

- [ ] `Ex01`에서 `Character` 배열을 순회할 때 왜 각각 다른 메세지가 나오는지 (다형성) 이해했다.
- [ ] `Ex02`에서 실수로 `new Enemy()`를 생성해보며 에러가 나는 것을 확인했다.
- [ ] `Ex02`의 `Dragon` 클래스에서 오버라이딩 된 피해 감소(`armor` 적용) 로직을 분석했다.
- [ ] `Ex03`에서 부모 클래스와 인터페이스의 역할 차이점을 명확히 설명할 수 있다.

## 퀴즈 예시
- 자식의 생성자에서 `super(name, hp)`를 호출해야 하는 이유는 무엇인가?
- `Character member = new Warrior(...)` 처럼 부모 타입으로 자식을 받는 이유는?
- 추상 클래스(`abstract class`)와 일반 클래스의 가장 큰 차이점 2가지는?
- 자바에서 클래스의 다중 상속은 막혀있지만, 인터페이스는 다중 구현이 허용되는 이유는 무엇이라 생각하는가?

## 추천 추가 실습
- `Ex01`에 새로운 직업 `Thief`(도적) 추가하여 은신 공격 구현해보기
- `Ex02`에 새로운 몬스터 `Ghost`(유령) 추가하고, 피격 시 일정 확률로 회피(피해 0)하는 로직 구현하기
- `Ex03`에 `Defendable` 인터페이스(메서드 `defend()`)를 추가해 `Hero`, `Monster`에 방어 행동 구현하기
