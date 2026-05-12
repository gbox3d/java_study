# Week 06 - 컬렉션 프레임워크

## 학습 목표
- `List`와 `Map`의 용도 차이를 RPG 예제로 체감한다
- `ArrayList`로 순서 있는 데이터(파티 슬롯)를 관리한다
- `HashMap`으로 키 기반 데이터(아이템 도감)를 관리한다
- 제네릭(`List<GameCharacter>`, `Map<String, ItemSpec>`)으로 타입 안정성을 확보한다
- `Comparator`와 `Collections`로 정렬·역순 같은 표준 연산을 호출한다

## 예제 클래스

- 메인 패키지: `chapter06`
- 하위 패키지:
  - `chapter06.ex01` — `Ex01`, `PartyManager`, `GameCharacter`
  - `chapter06.ex02` — `Ex02`, `ItemManager`, `ItemSpec`

## 이번 주 핵심 개념

- 컬렉션은 여러 데이터를 효율적으로 저장·조회·수정·삭제하기 위한 표준 자료구조 묶음이다.
- `List`는 "몇 번째 슬롯"이 의미를 가지는 순서형 컨테이너다. 인덱스 접근이 빠르고 중간 삽입/삭제는 뒤 요소 인덱스를 흔든다.
- `Map`은 키로 값을 직접 찾는 조회형 컨테이너다. 코드/ID처럼 고유한 식별자로 값을 꺼내는 데 맞는다.
- 제네릭(`<T>`)으로 어떤 타입이 담기는지 명시하면, 꺼낼 때 형변환이 필요 없고 컴파일 시점에 오류를 잡을 수 있다.
- `Set`은 이번 주 예제에는 포함되지 않는다 (필요할 때 추가 학습 항목).

## 실행 방법

```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter06/ex01/*.java
javac -encoding UTF-8 -d out/classes app/chapter06/ex02/*.java

java -cp out/classes chapter06.ex01.Ex01
java -cp out/classes chapter06.ex02.Ex02
```

`Ex01`, `Ex02`는 여러 클래스가 함께 돌아가는 패키지 예제이므로, 폴더 단위로 `javac`에 넘겨 한 번에 컴파일한 다음 실행하는 흐름을 권장한다.

## 예제별 설명

### Ex01 — `List` 기반 파티 캐릭터 관리

대상 소스: `app/chapter06/ex01/Ex01.java`, `PartyManager.java`, `GameCharacter.java`

`PartyManager`는 내부에 `List<GameCharacter> party = new ArrayList<>()`를 가진다. `Ex01`의 `main`은 6단계 시연을 순서대로 호출한다.

| 단계 | 메서드 | 보여주는 것 |
|---|---|---|
| 1. 생성과 영입 | `createAndAddExample` | `addCharacter` → 리스트 끝에 추가, `size`, `printParty` |
| 2. 조회 | `readExample` | `getCharacter(index)`, `isEmpty()` |
| 3. 중간 배치와 탈퇴 | `insertAndRemoveExample` | `insertCharacter(i, ...)`, `removeByIndex(i)`, `removeByName(name)` |
| 4. 성장과 검색 | `updateAndSearchExample` | `levelUpCharacter`, `findByName`, `containsName` |
| 5. 전체 순회 | `loopExample` | `for (int i = 0; i < party.size(); i++)` 인덱스 순회 |
| 6. 정렬과 해산 | `sortAndClearExample` | `Comparator.comparingInt(GameCharacter::getLevel).reversed()`, `Collections.reverse(party)`, `clear()` |

핵심 코드 한 조각:

```java
PartyManager partyManager = new PartyManager();
partyManager.addCharacter(new GameCharacter("warrior_01", "브론", "전사", 12, 180));
partyManager.addCharacter(new GameCharacter("mage_01",    "세리아", "마법사", 18, 90));
partyManager.addCharacter(new GameCharacter("archer_01",  "카일", "궁수", 15, 110));

partyManager.sortByLevelDesc();   // 내부적으로 Comparator.comparingInt(...).reversed()
partyManager.printParty();
```

이 예제에서 꼭 이해해야 할 포인트:
- 리스트는 **순서**가 의미다. 0번 슬롯이 바뀌면 그 뒤 인덱스가 전부 영향을 받는다.
- 같은 객체에 대한 변경(`character.levelUp()`)은 리스트 내부 참조에 그대로 반영된다 — 별도의 set 호출이 필요 없다.
- 정렬 기준은 `Comparator`로 분리해서 표현한다 (`.comparingInt(...).reversed()`).
- 검색은 직접 루프(`findByName`)로 구현되어 있다. 7주차 스트림과 비교할 좋은 사전 예제다.

### Ex02 — `Map` 기반 아이템 명세 관리

대상 소스: `app/chapter06/ex02/Ex02.java`, `ItemManager.java`, `ItemSpec.java`

`ItemManager`는 내부에 `Map<String, ItemSpec> itemTable = new HashMap<>()`를 가진다. 키는 아이템 코드 문자열, 값은 `ItemSpec` 객체다. `Ex02`의 `main`은 6단계 시연을 호출한다.

| 단계 | 메서드 | 보여주는 것 |
|---|---|---|
| 1. 생성과 등록 | `createAndPutExample` | `addItem` → 내부적으로 `put(code, spec)`, `size`, `printAllItems` |
| 2. 조회 | `readExample` | `getItem(code)` (없으면 `null`), `isEmpty()` |
| 3. 수정 | `updateExample` | `updatePrice` (값 객체의 setter 호출), `stopTrading` (`tradable = false`) |
| 4. 삭제와 검색 | `removeAndSearchExample` | `hasItem` (`containsKey`), `removeItem`, `getItemOrDefault` |
| 5. 전체 출력 | `loopExample` | `Map.Entry`로 `entrySet()` 순회 |
| 6. 실제 활용 관점 | `extraExample` | 플레이어 장착 아이템 코드 → 명세 조회 흐름 |

핵심 코드 한 조각:

```java
ItemManager itemManager = new ItemManager();
itemManager.addItem(new ItemSpec("sword_001", "초보자 검",   "Common", 100, 5, true));
itemManager.addItem(new ItemSpec("staff_001", "견습 마법봉", "Rare",   300, 8, true));
itemManager.addItem(new ItemSpec("armor_001", "가죽 갑옷",   "Common", 250, 0, true));

ItemSpec equipped = itemManager.getItem("staff_001");
System.out.println(equipped);          // ItemSpec{...}
itemManager.updatePrice("sword_001", 150);
itemManager.stopTrading("sword_001");
```

이 예제에서 꼭 이해해야 할 포인트:
- 맵은 **키로 직접 찾기**가 핵심이다. 리스트처럼 인덱스로 접근하지 않는다.
- 키가 없을 때 동작은 두 가지로 분리되어 있다 — `get`은 `null`을 돌려주고, `getOrDefault`는 폴백 값을 만들어 돌려준다.
- 값으로 객체를 넣으면, 객체의 setter 호출만으로 맵 내부 데이터가 함께 바뀐다(`updatePrice`가 동작하는 이유).
- `HashMap`은 순서를 보장하지 않는다. `printAllItems`의 출력 순서가 등록 순서와 다를 수 있다는 점을 학생들이 직접 확인해 보면 좋다.

## 실습 체크리스트

- `Ex01`에서 `insertCharacter`로 중간 슬롯에 끼워 넣은 뒤, 다른 캐릭터의 인덱스가 어떻게 바뀌는지 출력으로 확인했다.
- `Ex01`에서 `sortByLevelDesc` 호출 전후의 `printParty` 결과를 비교했다.
- `Ex02`에서 `get`과 `getOrDefault`를 모두 호출해 동작 차이를 확인했다.
- `Ex02`에서 `printAllItems` 출력 순서가 매 실행마다 일정한지(또는 그렇지 않은지) 관찰했다.
- `List`와 `Map`을 언제 구분해서 써야 하는지 한 문장으로 설명할 수 있다.

## 퀴즈 예시

- `List<GameCharacter>`에서 `<GameCharacter>`는 어떤 역할을 하는가?
- `PartyManager.removeByIndex(1)` 호출 직후 원래 2번 슬롯이었던 캐릭터의 인덱스는 무엇이 되는가?
- `HashMap`의 `get(key)`는 키가 없을 때 무엇을 반환하는가? `getOrDefault`와의 차이는?
- `Comparator.comparingInt(GameCharacter::getLevel).reversed()`는 어떤 정렬 순서를 만드는가?
- 같은 키로 `put`을 두 번 호출하면 맵의 크기는 어떻게 되는가?

## 추천 추가 실습

- `PartyManager`에 `findByRole(String role)`을 추가해 같은 직업 캐릭터만 모아 보기
- `PartyManager`에 `averageLevel()`을 추가해 파티 평균 레벨 출력
- `ItemManager`에 `mostExpensiveItem()`을 추가해 가격이 가장 높은 명세 찾기
- `ItemManager`에 `countByGrade(String grade)`를 추가해 등급별 아이템 수 세기
- 같은 데이터를 `List<ItemSpec>`로도 한 번 관리해 보고, "코드로 한 건 찾기" 비용을 `Map`과 비교해 보기
