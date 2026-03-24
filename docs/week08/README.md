# Week 08 - 파일 I/O 및 직렬화

## 학습 목표
- 문자/버퍼 스트림 사용
- 파일 읽기/쓰기 실습
- 객체 직렬화와 transient 확인

## 예제 클래스
- 패키지: chapter08
- 클래스: ex01, ex02, ex03

## 이번 주 핵심 개념
- 파일 입출력은 프로그램 밖의 데이터를 저장하고 읽는 작업이다.
- 문자 스트림은 텍스트, 바이트 스트림은 이진 데이터를 다룬다.
- 버퍼를 사용하면 한 줄 단위 등 더 편하게 처리할 수 있다.
- 직렬화는 객체 상태를 파일로 저장하고 다시 복원하는 기술이다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter08/ex01.java
javac -encoding UTF-8 -d out/classes app/chapter08/ex02.java
javac -encoding UTF-8 -d out/classes app/chapter08/ex03.java

java -cp out/classes chapter08.ex01
java -cp out/classes chapter08.ex02
java -cp out/classes chapter08.ex03
```

실행 결과 파일은 `out/files` 아래에 생성됩니다.

소스 파일 하나만 바로 실행할 때:

```bash
java app/chapter08/ex01.java
java app/chapter08/ex02.java
java app/chapter08/ex03.java
```

## 예제별 설명

### ex01 - 문자 단위 파일 읽기/쓰기
대상 소스: `app/chapter08/ex01.java`

이 예제는 `FileWriter`, `FileReader`를 사용해 텍스트를 파일에 저장하고 다시 읽는다.

```java
Path file = Path.of("out", "files", "week08_ex01.txt");
```

- 저장할 파일 경로를 객체로 표현한다.
- 이번 저장소에서는 실습 결과를 `out/files` 아래에 모은다.

쓰기 부분:

```java
try (FileWriter writer = new FileWriter(file.toFile())) {
    writer.write("Hello, I/O!");
}
```

- `try-with-resources` 문법이라 블록이 끝나면 자동으로 닫힌다.
- 텍스트 한 줄을 파일에 기록한다.

읽기 부분:

```java
try (FileReader reader = new FileReader(file.toFile())) {
    int ch;
    StringBuilder sb = new StringBuilder();
    while ((ch = reader.read()) != -1) {
        sb.append((char) ch);
    }
    System.out.println(sb);
}
```

- 문자 하나씩 읽는다.
- `read()`가 `-1`을 반환하면 파일 끝이다.
- 읽은 문자를 `StringBuilder`에 쌓아 최종 문자열을 만든다.

### ex02 - 버퍼를 이용한 줄 단위 입출력
대상 소스: `app/chapter08/ex02.java`

이 예제는 `BufferedWriter`, `BufferedReader`를 사용해 줄 단위 처리를 보여준다.

쓰기 부분:

```java
try (BufferedWriter writer = Files.newBufferedWriter(file)) {
    writer.write("line1");
    writer.newLine();
    writer.write("line2");
}
```

- `newLine()`을 사용해 줄바꿈을 명확히 넣는다.
- 여러 줄 텍스트를 만들기 쉽다.

읽기 부분:

```java
try (BufferedReader reader = Files.newBufferedReader(file)) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println("read: " + line);
    }
}
```

- `readLine()`은 한 줄씩 읽는다.
- 텍스트 파일 처리에서 문자 하나씩 읽는 것보다 훨씬 실용적이다.

### ex03 - 객체 직렬화와 `transient`
대상 소스: `app/chapter08/ex03.java`

이 예제는 `User` 객체를 파일에 저장했다가 다시 읽는 직렬화 예제다.

```java
static class User implements Serializable
```

- 직렬화 가능한 클래스라는 뜻이다.
- `Serializable`이 있어야 객체를 파일에 저장할 수 있다.

중요한 필드는 여기 있다.

```java
String id;
transient String password;
```

- `id`는 직렬화된다.
- `password`는 `transient`라서 저장 대상에서 제외된다.

저장과 복원 코드는 다음 흐름이다.

- `ObjectOutputStream`으로 객체 저장
- `ObjectInputStream`으로 객체 복원
- 저장 전 객체와 복원 후 객체를 비교 출력

이 예제에서 꼭 이해해야 할 포인트:
- 텍스트 파일과 객체 파일은 다르다.
- `transient`는 "이 값은 저장하지 말라"는 뜻이다.
- 민감 정보나 임시 값에 자주 사용된다.

## 실습 체크리스트

- `ex01`, `ex02`, `ex03` 실행 후 `out/files` 아래 생성 파일을 직접 확인했다.
- `ex01`에서 저장 문자열을 바꿔 다시 읽는 실습을 했다.
- `ex02`에서 줄 수를 늘려 여러 줄 읽기 결과를 확인했다.
- `ex03`에서 `transient` 필드가 저장되지 않는 이유를 설명할 수 있다.

## 퀴즈 예시
- `Path.of()`는 왜 문자열 하나보다 유용한가?
- `try-with-resources`를 쓰는 이유는 무엇인가?
- `read()`와 `readLine()`은 어떤 차이가 있는가?
- 직렬화 후 `password` 값이 왜 유지되지 않는가?

## 추천 추가 실습
- `ex01`에서 파일 내용을 대문자로 바꿔 출력하기
- `ex02`에 세 번째 줄 추가하기
- `User`에 `email` 필드를 추가해 직렬화 결과 비교하기
- 파일이 없을 때 예외 메시지를 친절하게 바꾸기
