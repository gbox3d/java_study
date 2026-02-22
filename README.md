# Java Study Template (Gradle)

교재용 예제를 Gradle 멀티모듈로 통합한 템플릿입니다.
예제는 `hello`를 1번으로 하여 순차 번호 폴더로 정리했습니다.

## 폴더 구조

- `examples/01_hello` : 1번 Hello World
- `examples/02_oop_basic` : 2번 클래스/생성자/오버로딩
- `examples/03_package` : 3번 package/사용자 정의 타입
- `examples/04_collection` : 4번 컬렉션(Vector)
- `examples/05_swing_basic` : 5번 Swing 기본 프레임
- `examples/06_swing_event` : 6번 Swing 이벤트 처리
- `examples/07_swing_thread` : 7번 Swing + 스레드/EDT
- `examples/08_serial_uart` : 8번 시리얼 통신(jSerialComm)

## 공통 명령

```bash
# 프로젝트 목록
./gradlew projects

# 전체 빌드
./gradlew build
```

## 예제 실행 명령

```bash
./gradlew :ex01_hello:run
./gradlew :ex02_oop_basic:run
./gradlew :ex03_package:run
./gradlew :ex04_collection:run
./gradlew :ex05_swing_basic:run
./gradlew :ex06_swing_event:run
./gradlew :ex07_swing_thread:run
./gradlew :ex08_serial_uart:run
```
