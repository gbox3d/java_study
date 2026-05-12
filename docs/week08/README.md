# Week 08 - 표준 입출력과 파일 I/O, 직렬화

## 학습 목표

- `Scanner`로 표준 입력(키보드)을 읽고, `System.out.printf`로 서식 출력을 한다
- 문자 스트림(`FileReader`/`FileWriter`)으로 텍스트 파일을 읽고 쓴다
- 버퍼 스트림(`BufferedReader`/`BufferedWriter`)으로 줄 단위 입출력을 처리한다
- 객체 직렬화로 객체 상태를 파일에 저장·복원하고, `transient` 의미를 이해한다

## 예제 클래스

- 패키지: `chapter08`
- 클래스: `Ex01`, `Ex02`, `Ex03`, `Ex04`
- 진행 순서: **표준 I/O(Ex01) → 텍스트 파일 I/O(Ex02) → 버퍼 I/O(Ex03) → 객체 직렬화(Ex04)**

## 이번 주 핵심 개념

- "입출력"은 **프로그램과 바깥 세계가 데이터를 주고받는 일**이다. 바깥은 키보드/화면일 수도 있고, 파일일 수도 있다.
- 키보드 입력(`System.in`)과 파일 입력(`FileReader`)은 **모양이 거의 같다** — 둘 다 "스트림에서 읽기"다. 그래서 콘솔 I/O를 먼저 익히면 파일 I/O가 자연스럽다.
- 텍스트는 **문자 스트림**, 이진 데이터(객체 포함)는 **바이트 스트림** 계열을 쓴다.
- 버퍼를 끼우면 한 줄(`readLine`)이나 묶음 단위로 더 편하고 빠르게 처리할 수 있다.
- 직렬화는 객체의 메모리 상태를 파일로 저장하고 다시 복원하는 표준 기술이며, `transient` 키워드로 저장 제외 필드를 표시한다.

## 실행 방법

```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter08/*.java

# Ex01은 키보드 입력을 받는다 (대화형)
java -cp out/classes chapter08.Ex01

# 나머지는 파일 입출력 (out/files 아래에 결과 생성)
java -cp out/classes chapter08.Ex02
java -cp out/classes chapter08.Ex03
java -cp out/classes chapter08.Ex04
```

`Ex01`을 자동화 테스트하고 싶다면 표준 입력을 파이프로 넣어줄 수도 있다:

```bash
printf "홍길동\n25\n" | java -cp out/classes chapter08.Ex01
```

파일 입출력 결과물은 `out/files/` 아래에 만들어진다.

## 예제별 설명

### Ex01 — 표준 입출력 (키보드 + 서식 출력)

대상 소스: `app/chapter08/Ex01.java`

이 예제는 파일 I/O로 들어가기 전, 같은 "스트림에서 읽고 쓰기" 흐름을 **콘솔**로 먼저 경험한다.

```java
try (Scanner scanner = new Scanner(System.in)) {
    System.out.print("이름을 입력하세요: ");
    String name = scanner.nextLine();

    System.out.print("나이를 입력하세요: ");
    int age = scanner.nextInt();

    System.out.printf("입력 결과 -> 이름: %s, 나이: %d세%n", name, age);
    System.out.printf("두 자리 정렬 예시: [%5d]%n", age);
    System.out.printf("소수 서식 예시: 평균 = %.2f%n", (age + 1) / 2.0);
}
```

핵심 포인트:

- `System.in`은 키보드를 가리키는 **표준 입력 스트림**이다. `FileInputStream`이 파일을 가리키는 것과 같은 모양이라는 점이 다음 예제로 자연스럽게 이어진다.
- `Scanner`는 그 스트림에서 한 줄(`nextLine`), 정수(`nextInt`), 실수(`nextDouble`) 같은 단위로 편하게 꺼내주는 도우미다.
- `try-with-resources`로 `Scanner`를 감싸면 블록 종료 시 자동으로 닫힌다. 다른 모든 예제(Ex02~Ex04)도 같은 패턴을 쓴다.
- `System.out.printf`의 서식 지시자:
  - `%s` 문자열, `%d` 정수, `%.2f` 소수 둘째 자리, `%5d` 5자리 오른쪽 정렬, `%n` 줄바꿈

자주 하는 실수:

- `nextInt()` 뒤에 곧바로 `nextLine()`을 부르면 직전 줄바꿈이 남아 빈 문자열이 들어온다. 이 예제는 `nextInt`만 마지막에 호출해 함정을 피한다.
- `Scanner`를 `try-with-resources`로 닫으면 내부적으로 `System.in`도 닫혀 같은 프로세스에서 다시 읽을 수 없게 된다는 점을 기억한다 (대화형 도구를 만들 때 주의).

### Ex02 — 문자 단위 파일 읽기/쓰기

대상 소스: `app/chapter08/Ex02.java`

`FileWriter` / `FileReader`로 텍스트 파일을 만들고 한 글자씩 다시 읽는다.

```java
Path file = Path.of("out", "files", "week08_ex02.txt");
Files.createDirectories(file.getParent());

try (FileWriter writer = new FileWriter(file.toFile())) {
    writer.write("Hello, I/O!");
}

try (FileReader reader = new FileReader(file.toFile())) {
    int ch;
    StringBuilder sb = new StringBuilder();
    while ((ch = reader.read()) != -1) {
        sb.append((char) ch);
    }
    System.out.println(sb);
}
```

핵심 포인트:

- `Path.of("out", "files", "week08_ex02.txt")`로 경로를 안전하게 합친다 (운영체제에 맞는 구분자 자동 처리).
- `read()`가 `-1`을 돌려주면 파일 끝이다. 콘솔의 `nextLine`이 줄바꿈을 만나면 끝나는 것과 같은 "끝 신호" 패턴이다.
- Ex01의 `Scanner` 흐름과 비교해 보면, **입력 대상이 키보드에서 파일로 바뀐 것** 외에는 구조가 거의 같다는 점이 보일 것이다.

### Ex03 — 버퍼를 이용한 줄 단위 입출력

대상 소스: `app/chapter08/Ex03.java`

`BufferedWriter` / `BufferedReader`로 줄 단위 입출력을 한다.

```java
try (BufferedWriter writer = Files.newBufferedWriter(file)) {
    writer.write("line1");
    writer.newLine();
    writer.write("line2");
}

try (BufferedReader reader = Files.newBufferedReader(file)) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println("read: " + line);
    }
}
```

핵심 포인트:

- `readLine()`은 한 글자씩 읽는 Ex02 방식보다 훨씬 실용적이다. 실제 텍스트 파일 처리에서는 거의 항상 이쪽을 쓴다.
- `Files.newBufferedReader/Writer`는 `Path`에서 바로 버퍼 스트림을 만들어 주는 편의 메서드다.
- `BufferedReader`로 `System.in`을 감싸면 `BufferedReader br = new BufferedReader(new InputStreamReader(System.in))` 형태로 키보드 입력도 줄 단위로 읽을 수 있다 — Ex01의 `Scanner`보다 옛 방식이지만 동작 원리는 같다.

### Ex04 — 객체 직렬화와 `transient`

대상 소스: `app/chapter08/Ex04.java`

`User` 객체를 파일에 저장했다가 다시 읽는 직렬화 예제.

```java
static class User implements Serializable {
    private static final long serialVersionUID = 1L;
    String id;
    transient String password;
    ...
}
```

핵심 포인트:

- `Serializable`을 구현한 클래스만 `ObjectOutputStream.writeObject()`로 저장할 수 있다.
- `transient`가 붙은 필드는 직렬화 대상에서 빠진다 — 복원하면 `null`(또는 타입별 기본값)이 들어 있다.
- 텍스트 파일과 객체 파일은 다르다. 객체 파일은 **이진 데이터**라서 메모장으로 열어도 사람이 읽을 수 있는 형태가 아니다.
- 보안상 저장하면 안 되는 값(비밀번호, 세션 토큰 등)이나 매 실행마다 다시 계산해야 하는 임시 캐시 값에 `transient`를 자주 쓴다.

## 실습 체크리스트

- `Ex01`에서 다양한 이름과 나이를 입력해 `printf` 서식 출력 결과를 확인했다.
- `Ex01`에서 `%5d` 와 `%.2f` 의 정렬/소수 자릿수가 어떻게 바뀌는지 직접 값을 바꿔 봤다.
- `Ex02`, `Ex03`, `Ex04` 실행 후 `out/files/` 아래 생성 파일을 직접 확인했다.
- `Ex02`에서 저장 문자열을 바꿔 다시 읽는 실습을 했다.
- `Ex03`에서 줄 수를 늘려 여러 줄 읽기 결과를 확인했다.
- `Ex04`에서 `transient` 필드가 왜 복원되지 않는지 설명할 수 있다.
- Ex01의 콘솔 흐름과 Ex02의 파일 흐름이 "스트림에서 읽기/쓰기"라는 공통 모양으로 묶인다는 점을 한 문장으로 설명할 수 있다.

## 퀴즈 예시

- `System.in`, `System.out`, `System.err`은 각각 무엇을 가리키는가?
- `Scanner.nextInt()` 직후 `nextLine()`을 호출하면 어떤 문제가 자주 생기는가?
- `printf` 서식 `%5d`와 `%-5d`의 차이는 무엇인가?
- `Path.of()`는 왜 `"out/files/x.txt"` 같은 문자열 한 개보다 안전한가?
- `try-with-resources`를 쓰는 이유는 무엇인가?
- `read()`와 `readLine()`은 어떤 차이가 있는가?
- 직렬화 후 `password` 값이 왜 유지되지 않는가? `transient`를 빼면 어떻게 되는가?

## 추천 추가 실습

- `Ex01`에 키와 몸무게(`double`)를 추가로 받아 BMI를 `%.1f`로 출력하기
- `Ex01`에서 `nextInt()` 후 `nextLine()`을 일부러 호출해 빈 줄 함정을 직접 재현해 보기
- `Ex02`에서 파일 내용을 대문자로 변환해 출력하기
- `Ex03`에 세 번째 줄을 추가하고, 각 줄에 번호를 붙여 다시 쓰기
- `Ex04`의 `User`에 `email` 필드를 추가해 직렬화 결과를 비교하기
- 파일이 없을 때 예외 메시지를 사용자 친화적으로 바꾸기
