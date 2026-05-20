# 자바 문법 중심 커리큘럼

기준 시점: 2026-03-24

## 수업 운영

- 총 10주, 주당 3시간
- 2시간 이론 + 1시간 실습
- 빌드 도구는 제외하고 `java`, `javac`만 사용
- 기준 소스: `app/chapter01` ~ `app/chapter10`
- 실습 산출물 위치: `out/classes`, `out/files`
- 매주 마무리 10~15분 퀴즈 진행

## 수업 원칙

- 초반 6주는 자바 문법과 객체지향 기초에 집중
- 예제는 패키지 단위로 읽고, 핵심 코드는 직접 다시 타이핑
- 실습은 "컴파일 오류 읽기 -> 수정 -> 다시 실행" 흐름으로 진행
- 퀴즈는 코드 읽기 문제와 짧은 손코딩 문제를 섞어서 구성

## 공통 실습 방식

```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter01/Ex01.java
java -cp out/classes chapter01.Ex01
```

전체 예제를 한 번에 컴파일할 때:

```bash
find app -name "*.java" | xargs javac -encoding UTF-8 -d out/classes
```

## 주차별 구성

### 1주차 - 자바 시작과 기본 문법
- 참고 소스: `chapter01/Ex01.java`, `chapter01/Ex02.java`, `chapter01/Ex03.java`, `chapter01/Ex04.java`
- 이론: JVM/JDK/JRE 차이, 소스 파일과 클래스 파일, `main` 메서드, 변수, 기본 타입, 형변환, 출력, 조건문, 반복문
- 실습: 인자 출력, 별 피라미드, 학점 계산기, `switch`와 `break` 동작 비교
- 퀴즈: 타입 추론, 형변환 결과 예측, 반복문 실행 순서 추적

### 2주차 - 배열과 메서드
- 참고 소스: `chapter02/Ex01.java`, `chapter02/Ex02.java`, `chapter02/Ex03.java`
- 이론: 배열 선언/초기화, 인덱스, 향상된 for문, 메서드 분리, 반환값, 가변 인자
- 실습: 합계/평균 구하기, 최댓값 찾기, 버블 정렬 직접 구현
- 퀴즈: 배열 순회 결과 예측, 메서드 호출 흐름, 매개변수와 반환값 구분

### 3주차 - 클래스와 객체
- 참고 소스: `chapter03/Ex01.java`, `chapter03/Ex02.java`, `chapter03/Ex03.java`
- 이론: 클래스 설계, 필드와 메서드, 생성자, `this`, `toString`, 접근 제어자, 캡슐화
- 실습: 학생/계좌 클래스 만들기, 생성자 오버로딩, 입금/출금 로직 작성
- 퀴즈: 객체 생성 시점, `private` 접근 제한, 생성자 호출 순서

### 4주차 - 상속과 다형성
- 참고 소스: `chapter04/ex01/Ex01.java` (+ `Character`, `Warrior`, `Mage`, `Archer`), `chapter04/ex02/Ex02.java` (+ `Enemy`, `Slime`, `Dragon`), `chapter04/ex03/Ex03.java` (+ `Movable`, `Attackable`, `Hero`, `Monster`)
- 이론: 상속, `super`, 오버라이딩, 업캐스팅, 다형성, 추상 클래스(`abstract`), 인터페이스 다중 구현
- 실습: RPG 직업 파생(전사/마법사/궁수)으로 다형성 호출, 몬스터 추상 클래스(`Enemy`)와 구체 클래스(`Slime`/`Dragon`), `Movable`+`Attackable` 다중 구현
- 퀴즈: 오버라이딩 규칙, `Character[]`에 자식 객체를 담는 이유, `abstract class`로 `new` 못 하는 이유, 인터페이스 다중 구현이 허용되는 이유

### 5주차 - 예외 처리와 표준 API
- 참고 소스: `chapter05/Ex01.java`, `chapter05/Ex02.java`, `chapter05/Ex03.java`, `chapter05/Ex04.java`
- 이론: 예외 발생 흐름, `try-catch-finally`, 사용자 정의 예외(`extends RuntimeException`), `LocalDate.parse` + `DateTimeFormatter` 검증, `LocalDate.plusDays`/`plusMonths`와 `ChronoUnit.DAYS.between` 날짜 계산
- 실습: 나눗셈 예외 처리(`Ex01`), 사용자 정의 예외로 나이 검증(`Ex02`), 날짜 문자열 판별기(`Ex03`), `LocalDate`로 날짜 더하기/사이 일수(`Ex04`)
- 퀴즈: 런타임 예외 의미, `finally` 실행 시점, `2026-02-30`이 왜 실패하는지, `plusMonths`와 `ChronoUnit.DAYS.between` 사용 시점

### 6주차 - 컬렉션 프레임워크
- 참고 소스: `chapter06/ex01/Ex01.java` (+ `PartyManager`, `GameCharacter`), `chapter06/ex02/Ex02.java` (+ `ItemManager`, `ItemSpec`)
- 이론: `List`와 `Map`의 용도 구분, 제네릭 복습, 인덱스 기반 순회와 `entrySet()` 순회, `Comparator`로 정렬 기준 설계 (`Set`은 이번 주 예제에는 포함되지 않음)
- 실습: `ArrayList`로 RPG 파티 슬롯 관리 (영입/탈퇴/정렬/역순/해산), `HashMap`으로 아이템 도감 관리 (코드 키 조회, `getOrDefault`, `containsKey`, 가격/거래가능 수정)
- 퀴즈: 인덱스가 바뀌는 시점, `get`과 `getOrDefault` 차이, `HashMap` 순서 비보장, `Comparator.reversed()` 의미

### 7주차 - 제네릭, 람다, 스트림
- 참고 소스: `chapter07/Ex01.java`, `chapter07/Ex02.java`, `chapter07/Ex03.java`
- 이론: 제네릭 타입 안정성, 람다식 문법, 함수형 스타일, 스트림 파이프라인
- 실습: 제네릭 박스 작성, 리스트 정렬 람다 바꾸기, 짝수 제곱 필터링
- 퀴즈: 타입 파라미터 의미, 람다 축약 전후 비교, `filter`와 `map` 구분

### 8주차 - 표준 입출력과 파일 입출력
- 참고 소스: `chapter08/Ex01.java` (표준 I/O), `chapter08/Ex02.java` (문자 파일), `chapter08/Ex03.java` (버퍼 파일), `chapter08/Ex04.java` (직렬화)
- 이론: 표준 입력 스트림 `System.in`과 `Scanner`, `System.out.printf` 서식 (`%s`, `%d`, `%.2f`, `%5d`, `%n`), 문자 스트림과 바이트 스트림, 버퍼의 의미, `Path`, `try-with-resources`, 직렬화와 `transient`
- 실습: 콘솔에서 이름/나이 입력받아 서식 출력(Ex01) → 같은 흐름을 파일로 옮겨 쓰기·읽기(Ex02) → 버퍼로 줄 단위 처리(Ex03) → 객체 직렬화 비교(Ex04)
- 퀴즈: `nextInt` 후 `nextLine` 함정, `printf` 서식 의미, 스트림 선택 기준, 직렬화 전후 `transient` 필드 값 차이

### 9주차 - VT 터미널 제어와 쓰레드
- 참고 소스 (chapter09, 두 갈래):
  - `Ex01_01.java` — 진행바 기본 틀. 단일 스레드가 두 트랙을 차례로 채운다. ANSI 2줄 그리기 (`\033[2A`/`\033[2K`)
  - `Ex01_02.java` — 두 스레드 동시 진행. `start()`/`join()`, 화면 출력 `synchronized` 락
  - `Ex01_03.java` — 갱신/렌더 스레드 분리. `volatile` 진행률 + render 스레드 + `interrupt()` 종료
  - `Ex02_01.java` — 타이머. 자동으로 돌고 Enter 로 종료. 백그라운드 스레드 + `\r`/`\033[2K` 한 줄 갱신, 공유 상태 없음
  - `Ex02_02.java` — 스톱워치 REPL. start/stop/reset/quit 명령 + `volatile` 공유 상태 + 상태 머신. 고정 레이아웃 `\033[1A` (단, 타이핑 중 커서 튐 결함)
  - `Ex02_03.java` — Ex02_02 와 redraw() 만 다름. 커서 저장·복원(`ESC 7`/`ESC 8`)으로 타이핑 흔들림 해결
- 이론: VT/콘솔 갱신 (`\r`, `\033[2A`/`\033[1A`, `\033[2K`, 커서 저장·복원 `ESC 7`/`ESC 8`, `flush`), `Runnable` 람다 / 메서드 참조, `new Thread(...).start()` vs `run()` 직접 호출, `Thread.sleep` 이 호출한 스레드만 멈춘다는 점, `Scanner.nextLine()` 같은 블로킹 호출도 마찬가지, 한 자원을 공유할 때의 두 해법 (`synchronized` 락 / `volatile`), 표준 종료 절차 `interrupt() → join()` (interruption protocol)
- 실습:
  - Ex01_01 — `\033[2A` 빼고 줄이 쌓이는 것 관찰, `flush` 빼고 관찰
  - Ex01_02 — `t1.start()` 를 `t1.run()` 으로 바꿔 동시 진행이 사라지는 것 확인, `join` 빼고 메시지가 끼어드는 모습, `render_bars()` 의 `synchronized` 빼고 화면이 깨지는 모습
  - Ex01_03 — `volatile` 빼고 관찰, `renderer.interrupt()` 빼고 프로그램이 안 끝나는 것 확인
  - Ex02_01 — main 이 Enter 입력 대기로 멈춰 있어도 timer 가 계속 도는 것 관찰
  - Ex02_02 ↔ Ex02_03 — 명령을 천천히 타이핑하며 커서 흔들림 유무 비교, Ex02_03 의 `SAVE_CURSOR`/`RESTORE_CURSOR` 빼 보기
- 퀴즈: `\r`/`\n` 차이와 `\033[2A`/`\033[2K` 의 역할, `flush` 필요성, `start` 와 `run` 차이, `join` 의 역할, `Thread.sleep` 의 영향 범위, `Scanner.nextLine()` 블로킹의 영향 범위, `synchronized` 와 `volatile` 을 각각 언제 쓰는가, `interrupt` 와 `InterruptedException` 의 관계, `ESC 7`/`ESC 8` 커서 저장·복원의 원리
- VT 챕터로 재정의: 쓰레드는 Ex01_xx 에서 익히고, Ex02_xx 는 그 위에 VT 터미널 제어로 인터랙티브 화면을 입힌다. 정보통신학과 맥락에서 터미널 제어 자체가 학습 가치
- 의도적으로 단순화: 동시성 도구는 `synchronized`(Ex01_02 화면 락)와 `volatile`(Ex01_03·Ex02_02·Ex02_03 공유 변수) 두 가지만 가볍게 다룬다. `Atomic*`/`ExecutorService`/메모리 모델, raw mode·키 단위 입력·TUI 라이브러리(Jansi/JLine)는 범위 밖 — 필요해지면 별도 보강 주차로 분리
### 10주차 - 네트워크 기초
- 참고 소스: `chapter10/Ex01.java`, `chapter10/Ex02.java`, `chapter10/Ex03.java`
- 이론: UDP/TCP 차이, 소켓 통신 기본 흐름, 서버-클라이언트 구조, 멀티 클라이언트 처리
- 실습: UDP 송수신, TCP 에코, 간단 채팅 서버 구조 읽기
- 퀴즈: 포트 개념, 연결형/비연결형 차이, 서버 루프 동작 순서

## 보강 메모

- 첫 주에는 IDE 기능보다 컴파일 오류 메시지 읽는 법을 우선 설명
- 8주차 이후에는 예외 처리와 컬렉션을 계속 복습 문제에 섞어서 누적 학습
- 네트워크와 스레드는 구현 완성보다 "코드 흐름 읽기" 비중을 높여 진행
