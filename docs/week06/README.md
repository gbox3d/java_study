# Week 06 - 컬렉션 프레임워크

## 학습 목표
- List/Set/Map 용도 구분
- Map 기반 데이터 처리
- Comparator 정렬 기준 설계

## 예제 클래스
- 패키지: chapter06
- 클래스: ex01, ex02, ex03

## 이번 주 핵심 개념
- 컬렉션은 여러 데이터를 효율적으로 저장하고 다루는 표준 자료구조 묶음이다.
- `List`는 순서가 있고, `Map`은 키와 값의 쌍으로 저장한다.
- 객체 리스트는 정렬 기준을 직접 정의해야 할 때가 많다.
- 제네릭을 통해 어떤 타입의 데이터가 들어가는지 명확히 할 수 있다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter06/ex01.java
javac -encoding UTF-8 -d out/classes app/chapter06/ex02.java
javac -encoding UTF-8 -d out/classes app/chapter06/ex03.java

java -cp out/classes chapter06.ex01
java -cp out/classes chapter06.ex02
java -cp out/classes chapter06.ex03
```

소스 파일 하나만 바로 실행할 때:

```bash
java app/chapter06/ex01.java
java app/chapter06/ex02.java
java app/chapter06/ex03.java
```

## 예제별 설명

### ex01 - `List` 기본 사용
대상 소스: `app/chapter06/ex01.java`

이 예제는 `ArrayList`를 사용해 문자열 목록을 저장하고 조작하는 코드다.

```java
List<String> names = new ArrayList<>();
```

- 인터페이스 타입은 `List`
- 실제 구현 객체는 `ArrayList`
- `String`만 저장하겠다는 뜻이 제네릭에 담겨 있다.

데이터 추가와 삭제는 아래처럼 한다.

```java
names.add("Alice");
names.add("Bob");
names.add("Charlie");

names.remove("Bob");
```

- `add()`는 요소 추가
- `remove()`는 특정 요소 제거

확인 코드는 다음과 같다.

```java
System.out.println("contains Alice: " + names.contains("Alice"));
System.out.println("list: " + names);
```

- `contains()`는 값 존재 여부를 검사한다.
- 리스트 전체를 출력하면 순서가 유지된 상태로 표시된다.

### ex02 - `Map` 기본 사용
대상 소스: `app/chapter06/ex02.java`

이 예제는 단어와 뜻처럼 "이름표로 값을 찾는" 구조를 보여준다.

```java
Map<String, String> dict = new HashMap<>();
dict.put("Network", "network");
dict.put("Thread", "thread");
```

- 키와 값 모두 문자열인 맵이다.
- `put(key, value)`로 데이터를 저장한다.

반복은 이렇게 한다.

```java
for (String key : dict.keySet()) {
    System.out.println(key + " : " + dict.get(key));
}
```

- `keySet()`으로 모든 키를 얻는다.
- `get(key)`로 해당 키의 값을 꺼낸다.

이 예제에서 꼭 이해해야 할 포인트:
- 리스트는 순서 기반
- 맵은 키 기반
- 컬렉션 종류에 따라 접근 방식이 다르다

### ex03 - 객체 리스트 정렬
대상 소스: `app/chapter06/ex03.java`

이 예제는 사용자 정의 객체 `Student`를 리스트에 넣고 정렬 기준을 만드는 코드다.

```java
static class Student {
    int id;
    String name;
    int score;
```

- 학생 객체 하나에 학번, 이름, 점수를 묶는다.
- 객체 리스트는 기본 타입 배열보다 조금 더 현실적인 예제다.

정렬 기준은 아래 부분이 핵심이다.

```java
students.sort(
        Comparator.comparingInt((Student s) -> s.score).reversed()
                .thenComparingInt(s -> s.id)
);
```

- 먼저 점수 `score` 기준으로 정렬
- `reversed()`로 점수 내림차순
- 점수가 같으면 `id` 오름차순으로 한 번 더 비교

출력은 메서드 참조를 사용한다.

```java
students.forEach(System.out::println);
```

- 리스트의 각 요소를 하나씩 출력한다.
- `toString()`이 재정의되어 있으므로 보기 좋은 형태로 출력된다.

## 실습 체크리스트

- `ex01`에서 리스트에 값을 추가/삭제한 뒤 결과를 다시 확인했다.
- `ex02`에서 새 키-값 쌍을 추가하고 출력 순서를 관찰했다.
- `ex03`에서 학생 데이터를 바꿔 정렬 우선순위가 어떻게 적용되는지 확인했다.
- `List`와 `Map`을 언제 구분해서 써야 하는지 말할 수 있다.

## 퀴즈 예시
- `List<String>`에서 `String`은 어떤 역할을 하는가?
- `remove("Bob")` 이후 리스트는 어떻게 바뀌는가?
- `Map`에서 키와 값은 각각 어떤 용도로 쓰이는가?
- `thenComparingInt()`는 왜 필요한가?

## 추천 추가 실습
- `List<Integer>`로 점수 목록 관리하기
- `Map<String, Integer>`로 과목별 점수 저장하기
- 학생 이름 기준 정렬을 추가하기
- 점수 평균과 최고 점수를 계산하기
