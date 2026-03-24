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
javac -encoding UTF-8 -d out/classes app/chapter01/ex01.java
java -cp out/classes chapter01.ex01
```

전체 예제를 한 번에 컴파일할 때:

```bash
find app -name "*.java" | xargs javac -encoding UTF-8 -d out/classes
```

## 주차별 구성

### 1주차 - 자바 시작과 기본 문법
- 참고 소스: `chapter01/ex01.java`, `chapter01/ex02.java`, `chapter01/ex03.java`, `chapter01/ex04.java`
- 이론: JVM/JDK/JRE 차이, 소스 파일과 클래스 파일, `main` 메서드, 변수, 기본 타입, 형변환, 출력, 조건문, 반복문
- 실습: 인자 출력, 별 피라미드, 학점 계산기, `switch`와 `break` 동작 비교
- 퀴즈: 타입 추론, 형변환 결과 예측, 반복문 실행 순서 추적

### 2주차 - 배열과 메서드
- 참고 소스: `chapter02/ex01.java`, `chapter02/ex02.java`, `chapter02/ex03.java`
- 이론: 배열 선언/초기화, 인덱스, 향상된 for문, 메서드 분리, 반환값, 가변 인자
- 실습: 합계/평균 구하기, 최댓값 찾기, 버블 정렬 직접 구현
- 퀴즈: 배열 순회 결과 예측, 메서드 호출 흐름, 매개변수와 반환값 구분

### 3주차 - 클래스와 객체
- 참고 소스: `chapter03/ex01.java`, `chapter03/ex02.java`, `chapter03/ex03.java`
- 이론: 클래스 설계, 필드와 메서드, 생성자, `this`, `toString`, 접근 제어자, 캡슐화
- 실습: 학생/계좌 클래스 만들기, 생성자 오버로딩, 입금/출금 로직 작성
- 퀴즈: 객체 생성 시점, `private` 접근 제한, 생성자 호출 순서

### 4주차 - 상속과 다형성
- 참고 소스: `chapter04/ex01.java`, `chapter04/ex02.java`, `chapter04/ex03.java`
- 이론: 상속, `super`, 오버라이딩, 업캐스팅, 다형성, 인터페이스
- 실습: 직원/매니저, 동물 소리, 게임 캐릭터 역할 분리
- 퀴즈: 오버라이딩 규칙, 참조 변수 타입과 실제 객체 타입, 인터페이스 장점

### 5주차 - 예외 처리와 표준 API
- 참고 소스: `chapter05/ex01.java`, `chapter05/ex02.java`, `chapter05/ex03.java`
- 이론: 예외 발생 흐름, `try-catch-finally`, 사용자 정의 예외, `LocalDate`, 포맷 검증
- 실습: 나눗셈 예외 처리, 입력값 검증 메서드, 날짜 문자열 판별기
- 퀴즈: 런타임 예외 의미, `finally` 실행 시점, 잘못된 날짜 처리 결과

### 6주차 - 컬렉션 프레임워크
- 참고 소스: `chapter06/ex01.java`, `chapter06/ex02.java`, `chapter06/ex03.java`
- 이론: `List`, `Set`, `Map` 차이, 제네릭 복습, 반복 처리, 정렬 기준 설계
- 실습: 이름 목록 관리, 단어장 만들기, 학생 점수 정렬
- 퀴즈: 컬렉션 선택 기준, `contains`와 `get` 차이, 정렬 우선순위 읽기

### 7주차 - 제네릭, 람다, 스트림
- 참고 소스: `chapter07/ex01.java`, `chapter07/ex02.java`, `chapter07/ex03.java`
- 이론: 제네릭 타입 안정성, 람다식 문법, 함수형 스타일, 스트림 파이프라인
- 실습: 제네릭 박스 작성, 리스트 정렬 람다 바꾸기, 짝수 제곱 필터링
- 퀴즈: 타입 파라미터 의미, 람다 축약 전후 비교, `filter`와 `map` 구분

### 8주차 - 파일 입출력
- 참고 소스: `chapter08/ex01.java`, `chapter08/ex02.java`, `chapter08/ex03.java`
- 이론: 문자 스트림과 바이트 스트림, 버퍼의 의미, `Path`, 파일 생성, 직렬화, `transient`
- 실습: 텍스트 파일 쓰기/읽기, 여러 줄 저장 후 복원, 객체 직렬화 비교
- 퀴즈: 스트림 선택 기준, 파일 경로 해석, 직렬화 전후 값 차이

### 9주차 - 스레드와 동기화
- 참고 소스: `chapter09/ex01.java`, `chapter09/ex02.java`, `chapter09/ex03.java`
- 이론: 프로세스와 스레드, `Runnable`, `start`, `join`, 경쟁 상태, `synchronized`, 스레드 풀
- 실습: 숫자 출력 스레드, 계좌 출금 경쟁 상황, 작업 풀 실행
- 퀴즈: `run`과 `start` 차이, 동기화 필요성, `ExecutorService` 역할

### 10주차 - 네트워크 기초
- 참고 소스: `chapter10/ex01.java`, `chapter10/ex02.java`, `chapter10/ex03.java`
- 이론: UDP/TCP 차이, 소켓 통신 기본 흐름, 서버-클라이언트 구조, 멀티 클라이언트 처리
- 실습: UDP 송수신, TCP 에코, 간단 채팅 서버 구조 읽기
- 퀴즈: 포트 개념, 연결형/비연결형 차이, 서버 루프 동작 순서

## 보강 메모

- 첫 주에는 IDE 기능보다 컴파일 오류 메시지 읽는 법을 우선 설명
- 8주차 이후에는 예외 처리와 컬렉션을 계속 복습 문제에 섞어서 누적 학습
- 네트워크와 스레드는 구현 완성보다 "코드 흐름 읽기" 비중을 높여 진행
