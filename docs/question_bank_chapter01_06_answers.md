# Java 문제은행 정답 - Chapter 01 ~ 06

## 구성
- 단답형 10문항 정답
- 서술형 5문항 모범 답안
- 코딩 실습 5문항 예시 해설

---

## 단답형 정답

### 1번
`args`는 프로그램 실행 시 전달되는 명령행 인자를 문자열 배열로 받는 매개변수이다.

### 2번
결과는 `4`이다.

### 3번
`continue`를 만나면 현재 반복의 남은 코드를 건너뛰고 다음 반복으로 이동한다.

### 4번
`Arrays.toString()` 메서드이다.

### 5번
가변 인자 또는 varargs라고 부른다.

### 6번
현재 객체의 필드 `name`을 의미한다.

### 7번
외부에서 필드에 직접 접근하지 못하게 하여 데이터를 안전하게 보호하고, 메서드를 통해서만 제어하기 위해서이다.

### 8번
추상 클래스는 공통 뼈대와 규약을 제공하는 용도이며, 미완성 메서드를 포함할 수 있어서 직접 객체를 생성할 수 없다.

### 9번
예외 발생 여부와 관계없이 마지막에 실행된다.

### 10번
`List`는 순서 기반으로 데이터를 저장하고, `Map`은 키와 값의 쌍으로 데이터를 저장한다.

---

## 서술형 모범 답안

### 11번
일반 `for`문은 인덱스를 직접 다루기 때문에 몇 번째 요소인지 알아야 하거나, 특정 위치의 값을 수정해야 할 때 적합하다. 향상된 `for`문은 배열이나 컬렉션의 모든 값을 단순히 꺼내서 사용할 때 코드가 간단하고 읽기 쉽다. 배열 순회에서 인덱스가 중요한 이유는 값의 위치를 기준으로 접근하거나, 앞뒤 값을 비교하거나, 특정 위치에 저장된 값을 수정해야 하는 경우가 많기 때문이다.

### 12번
생성자는 객체가 만들어질 때 필요한 초기값을 넣어 객체를 올바른 상태로 시작하게 한다. 캡슐화는 필드를 `private`으로 숨기고 메서드를 통해서만 접근하게 하여 잘못된 값이 들어가는 것을 막는다. 예를 들어 잔액이나 나이 같은 값은 직접 수정하게 두면 오류가 생길 수 있으므로, 검증 로직을 가진 메서드로만 다루는 것이 안전하다.

### 13번
상속은 공통 속성과 기능을 부모 클래스에 두고, 자식 클래스가 이를 물려받는 구조이다. 오버라이딩은 부모가 가진 메서드를 자식 클래스 상황에 맞게 다시 구현하는 것이다. 다형성은 부모 타입으로 여러 자식 객체를 하나의 방식으로 다루는 개념이다. 예를 들어 `Character` 배열에 `Warrior`, `Mage`, `Archer`를 담고 `attack()`을 호출하면, 모두 같은 메서드 호출 형태를 사용하지만 실제 실행은 각 직업에 맞는 공격 방식으로 이루어진다.

### 14번
`try`는 예외가 발생할 수 있는 코드를 감싸는 블록이다. `catch`는 특정 예외가 발생했을 때 이를 처리하는 블록이다. `throw`는 예외를 직접 발생시키는 문법이다. `finally`는 예외 발생 여부와 상관없이 마지막에 실행되는 블록이다. 사용자 정의 예외를 사용하는 이유는 프로그램의 규칙 위반 상황을 더 명확한 의미로 표현하기 위해서이다. 예를 들어 나이나 점수 입력 오류를 단순한 일반 예외가 아니라 `InvalidAgeException`, `InvalidScoreException`처럼 표현하면 코드 의미가 더 분명해진다.

### 15번
게임 프로그램에서 `List`는 순서가 중요한 데이터에 적합하고, `Map`은 특정 키로 빠르게 값을 찾는 데이터에 적합하다. 파티 캐릭터 관리는 몇 번째 슬롯에 누가 배치되어 있는지가 중요하므로 `List`가 적절하다. 반면 아이템 명세 관리는 아이템 코드로 이름, 가격, 등급 같은 정보를 바로 찾아야 하므로 `Map`이 적절하다. 즉, `List`는 순서 중심, `Map`은 검색 중심 구조라고 정리할 수 있다.

---

## 코딩 실습 해설

### 16번
핵심은 `main(String[] args)`를 사용하고, `args.length`가 0인지 먼저 검사하는 것이다. 인자가 있으면 일반 `for`문으로 순회하며 `args[i]` 형식으로 출력하면 된다.

예시 코드:

```java
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("입력된 인자가 없습니다.");
            return;
        }

        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "]: " + args[i]);
        }
    }
}
```

### 17번
배열 출력에는 `Arrays.toString()`을 사용하고, 합계는 반복문으로 구한 뒤 평균은 `sum / (double) length`로 계산하면 된다.

예시 코드:

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] scores = {90, 85, 95, 100};
        int sum = 0;

        for (int score : scores) {
            sum += score;
        }

        double avg = sum / (double) scores.length;

        System.out.println("scores: " + Arrays.toString(scores));
        System.out.println("sum: " + sum);
        System.out.printf("avg: %.2f%n", avg);
    }
}
```

### 18번
부모 클래스에 공통 필드와 `attack()` 메서드를 두고, 자식 클래스가 이를 오버라이딩하면 된다. `Character[]` 배열을 사용하면 다형성을 확인할 수 있다.

예시 코드 개요:

```java
class Character {
    String name;
    String job;
    int level;

    Character(String name, String job, int level) {
        this.name = name;
        this.job = job;
        this.level = level;
    }

    void attack() {
        System.out.println(name + " attacks.");
    }
}

class Warrior extends Character {
    Warrior(String name, int level) {
        super(name, "Warrior", level);
    }

    @Override
    void attack() {
        System.out.println(name + " swings a sword.");
    }
}

class Mage extends Character {
    Mage(String name, int level) {
        super(name, "Mage", level);
    }

    @Override
    void attack() {
        System.out.println(name + " casts a fireball.");
    }
}
```

### 19번
사용자 정의 예외 클래스를 만들고, 점수 범위를 벗어나면 `throw`로 예외를 발생시키면 된다. `try-catch`를 사용해 정상 점수와 비정상 점수를 각각 확인한다.

예시 코드 개요:

```java
class InvalidScoreException extends RuntimeException {
    InvalidScoreException(String message) {
        super(message);
    }
}

static void checkScore(int score) {
    if (score < 0 || score > 100) {
        throw new InvalidScoreException("invalid score: " + score);
    }
}
```

### 20번
파티 관리는 `List`, 아이템 정보 관리는 `Map`으로 나누는 것이 핵심이다. `List`는 순서대로 캐릭터를 추가하고 삭제하는 데 사용하고, `Map`은 아이템 코드로 값을 조회하는 데 사용한다.

예시 코드 개요:

```java
List<String> party = new ArrayList<>();
party.add("브론");
party.add("세리아");
party.remove("브론");

Map<String, Integer> items = new HashMap<>();
items.put("potion", 50);
items.put("elixir", 200);

System.out.println(items.get("potion"));
System.out.println(party);
System.out.println(items);
```

채점 포인트:
- `List`와 `Map`의 역할을 구분해서 사용했는가
- 추가, 삭제, 조회 기능이 모두 들어갔는가
- 최종 결과를 출력했는가