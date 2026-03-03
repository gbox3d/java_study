# AI Work Log

## 2026-02-22

### Session Summary (before switching to `main`)
- Legacy 코드베이스 분석 요청을 받아 전체 폴더 구조를 점검함.
- `legacy`라는 실제 폴더는 없고 루트에 레거시 성격 폴더가 분산되어 있음을 확인함.
- 예제 폴더(`chapter*`, `project/*`, `javafx_sample`, `rtspsample`, `rtsp_opencv`, `maven_exam`, `hello`, `app`)를 분석해 용도/정리 방향을 도출함.
- `readme.md`를 생성해 레거시 분석, 교재용 분류, 정리 우선순위, Gradle 전환 로드맵을 작성함.
- 사용자 확인 결과 당시 브랜치는 `lagacy_2024`였고, 이후 사용자가 `main` 브랜치로 전환함.

### Current State (on `main`)
- Branch: `main`
- `ai_logs/` directory exists.
- 앞으로 대화 및 작업 내역은 이 파일(`ai_logs/work_log.md`)에 누적 기록.

### User Direction
- 교재 제작 목적.
- 레포 전체를 레거시로 보고 단계적 리팩토링 예정.
- 빌드 도구는 Maven/Gradle 중 검토 중이었고, 이후 Gradle 원형 판단 질문 진행.


### Update - Template Refactoring (main)
- 요청에 따라 현재 브랜치를 교재용 기본 템플릿으로 정리 시작.
- Gradle 멀티모듈 통합 적용:
  - 루트 `settings.gradle`에 예제 모듈 등록
  - 루트 `build.gradle` 추가(공통 repository/toolchain/test 설정)
  - 모듈별 `build.gradle` 추가: `hello`, `chapter3`, `chapter6_package`, `chapter7`, `chapter9`, `chapter10`, `project/helloJframe`, `project/uartSample`
- Swing 예제를 Gradle로 실행 가능하게 설정:
  - `chapter9` mainClass = `exam02.MainFrame`
  - `chapter10` mainClass = `exam01.MainFrame`
  - `project/helloJframe` mainClass = `HelloApp`
- Serial 예제를 Gradle로 실행 가능하게 설정:
  - `project/uartSample`에 `com.fazecast:jSerialComm:2.11.0` 의존성 추가
- Maven 예제 디렉터리 제거:
  - `maven_exam`, `javafx_sample`, `rtspsample`, `rtsp_opencv`
- 루트 `readme.md`를 템플릿 브랜치 기준 문서로 재작성.
- 검증:
  - `./gradlew projects` 성공
  - 루트 프로젝트 `java_study_template` 및 하위 모듈 인식 확인

### Update - Example Folder Reorganization
- 요청에 따라 예제 폴더를 순번 체계로 재구성.
- `hello`를 1번으로 지정하고 나머지 예제를 2~8번으로 순차 정렬.
- 신규 구조:
  - `examples/01_hello`
  - `examples/02_oop_basic`
  - `examples/03_package`
  - `examples/04_collection`
  - `examples/05_swing_basic`
  - `examples/06_swing_event`
  - `examples/07_swing_thread`
  - `examples/08_serial_uart`
- `settings.gradle`을 새 순번 모듈명으로 갱신:
  - `:ex01_hello` ~ `:ex08_serial_uart`
  - 각 모듈의 `projectDir`를 `examples/*` 경로로 매핑
- 각 예제 폴더 `README.md`를 공통 포맷으로 재작성:
  - 설명
  - 실행 방법(`./gradlew :모듈:run`)
  - 빌드(컴파일) 방법(`./gradlew :모듈:build`)
- 루트 `readme.md`도 새 구조 및 공통 명령으로 갱신.
- 검증: `./gradlew projects` 성공, `ex01`~`ex08` 모듈 인식 확인.
- 루트 문서는 관례에 맞춰 `README.md`로 정리.

### Update - app Module Removed
- 요청에 따라 `app` 모듈 제거.
- `settings.gradle`에서 `:app` include 삭제.
- 루트 `README.md`에서 `app` 설명 삭제.
- 검증: `./gradlew projects` 성공, `ex01`~`ex08` 모듈만 인식됨.
