# Week 06 - 컬렉션 프레임워크

## 학습 목표
- List/Set/Map 용도 구분
- Map 기반 데이터 처리

## 예제 클래스
- 패키지: chapter06
- 하위 패키지: chapter06.ex01.Ex01, chapter06.ex02.Ex02

## 이번 주 핵심 개념
- 컬렉션은 여러 데이터를 효율적으로 저장하고 다루는 표준 자료구조 묶음이다.
- `List`는 순서가 있고, `Map`은 키와 값의 쌍으로 저장한다.
- 제네릭을 통해 어떤 타입의 데이터가 들어가는지 명확히 할 수 있다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter06/ex01/*.java
javac -encoding UTF-8 -d out/classes app/chapter06/ex02/*.java

java -cp out/classes chapter06.ex01.Ex01
java -cp out/classes chapter06.ex02.Ex02
```

소스 파일 하나만 바로 실행할 때:

```bash
```

`ex01`, `ex02`는 여러 클래스로 구성된 패키지 예제라서 `javac`로 함께 컴파일해서 실행하는 방식을 권장한다.

## 예제별 설명

### ex01 - `List` 기반 파티 캐릭터 관리
대상 소스: `app/chapter06/ex01/Ex01.java`

이 예제는 게임 파티에 들어가는 캐릭터를 `ArrayList`로 관리하는 구조를 보여준다.

```java
partyManager.addCharacter(new GameCharacter("warrior_01", "브론", "전사", 12, 180));
partyManager.addCharacter(new GameCharacter("mage_01", "세리아", "마법사", 18, 90));
```

- 리스트에는 `GameCharacter` 객체가 순서대로 저장된다.
- 파티 슬롯처럼 순서가 중요한 데이터에 `List`가 잘 맞는다.

이 예제에서 꼭 이해해야 할 포인트:
- 리스트는 "몇 번째 위치에 있는가"가 중요하다.
- 중간 삽입과 삭제를 하면 뒤 요소들의 인덱스가 바뀐다.
- 실제 프로그램에서는 파티 편성, 인벤토리 슬롯, 대기열 같은 구조를 리스트로 자주 관리한다.

### ex02 - `Map` 기반 아이템 명세 관리
대상 소스: `app/chapter06/ex02/Ex02.java`

이 예제는 게임에서 아이템 도감이나 상점 데이터처럼, 아이템 코드로 명세를 관리하는 구조를 보여준다.

```java
itemManager.addItem(new ItemSpec("sword_001", "초보자 검", "Common", 100, 5, true));
itemManager.addItem(new ItemSpec("staff_001", "견습 마법봉", "Rare", 300, 8, true));
```

- 키는 아이템 코드다.
- 값은 `ItemSpec` 객체다.
- `Map<String, ItemSpec>` 구조로 관리한다.

이 예제에서 꼭 이해해야 할 포인트:
- 리스트는 순서대로 저장하지만, 맵은 코드 같은 키로 바로 찾는다.
- 맵의 값으로 문자열뿐 아니라 객체도 저장할 수 있다.
- 실제 프로그램에서는 맵을 "데이터 테이블"처럼 쓰는 경우가 많다.

## 실습 체크리스트

- `ex01`에서 리스트에 값을 추가/삭제한 뒤 결과를 다시 확인했다.
- `ex02`에서 새 키-값 쌍을 추가하고 출력 순서를 관찰했다.
- `List`와 `Map`을 언제 구분해서 써야 하는지 말할 수 있다.

## 퀴즈 예시
- `List<String>`에서 `String`은 어떤 역할을 하는가?
- `remove("Bob")` 이후 리스트는 어떻게 바뀌는가?
- `Map`에서 키와 값은 각각 어떤 용도로 쓰이는가?

## 추천 추가 실습
- `List<Integer>`로 점수 목록 관리하기
- `Map<String, Integer>`로 과목별 점수 저장하기
- 파티 캐릭터의 평균 레벨 구하기
- 아이템 가격이 가장 높은 명세 찾기
