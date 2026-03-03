# Java Study Project

Gradle 기반 Java 학습 저장소입니다. `gradle` 브랜치의 Java/Kotlin 비교 예제(`app`, `app-kotlin`)와 `main` 브랜치의 교재형 예제 모듈(`examples/*`)이 함께 포함되어 있습니다.

## 환경 요구 사항

- JDK 21
- Gradle Wrapper 사용

## 포함된 모듈

- `app`: 주차별 Java 예제
- `app-kotlin`: 동일 주제의 Kotlin 예제
- `examples/01_hello` ~ `examples/08_serial_uart`: 교재형 Java 예제

## 자주 쓰는 명령

```powershell
.\gradlew.bat projects
.\gradlew.bat build
.\gradlew.bat :app:runWeek01Ex01
.\gradlew.bat :app-kotlin:runWeek01Ex01
.\gradlew.bat :ex01_hello:run
.\gradlew.bat :ex08_serial_uart:run
```

macOS/Linux에서는 `./gradlew`를 사용하면 됩니다.

## 주차별 문서

- [Week 01](docs/week01/README.md)
- [Week 02](docs/week02/README.md)
- [Week 03](docs/week03/README.md)
- [Week 04](docs/week04/README.md)
- [Week 05](docs/week05/README.md)
- [Week 06](docs/week06/README.md)
- [Week 07](docs/week07/README.md)
- [Week 08](docs/week08/README.md)
- [Week 09](docs/week09/README.md)
- [Week 10](docs/week10/README.md)
