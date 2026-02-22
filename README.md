# Java Study Project

Gradle 기반 Java/Kotlin 비교 학습 프로젝트입니다.  
동일한 주차/예제를 `app`(Java)와 `app-kotlin`(Kotlin)에서 나란히 실행할 수 있습니다.

## 환경 요구 사항

- Java: JDK 21
- Gradle: Wrapper 사용 (별도 설치 불필요)

## 기본 실행

### Windows
~~~pwsh
.\gradlew.bat :app:run
.\gradlew.bat :app-kotlin:run
.\gradlew.bat :app:test
.\gradlew.bat :app-kotlin:test
~~~

### macOS / Linux
~~~bash
./gradlew :app:run
./gradlew :app-kotlin:run
./gradlew :app:test
./gradlew :app-kotlin:test
~~~

## 주차별 예제 실행

### Java 예시
~~~pwsh
.\gradlew.bat :app:runWeek01Ex01
.\gradlew.bat :app:runWeek07Ex03
~~~

### Kotlin 예시
~~~pwsh
.\gradlew.bat :app-kotlin:runWeek01Ex01
.\gradlew.bat :app-kotlin:runWeek07Ex03
~~~

태스크 이름 규칙:
- `runWeekXXExYY`
- `XX`: 01~10 (주차)
- `YY`: 01~03 (예제 번호)

모듈 지정:
- Java: `:app:runWeekXXExYY`
- Kotlin: `:app-kotlin:runWeekXXExYY`

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
