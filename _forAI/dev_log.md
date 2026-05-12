# Dev Log

## 2026-05-13

### chapter09 Ex02 동기화 예제 수정 + Ex04 경마 시각화 신규

- **Ex02 수정**: synchronized 블록 안의 `sleep(50)`만으로는 락 barging 때문에 worker-1이 휩쓸어 가서 예제가 무의미했던 문제 해결.
  - for 루프 안 `withdraw()` 호출 뒤에 `Thread.sleep(20)` 추가 → 워커가 락 밖에서 잠시 양보 → 실제 교대 진입 관찰 가능
  - 두 sleep의 역할 차이(임계 구역 비용 vs 양보)를 주석으로 명시
  - docs/week09: 실행 결과 예시 박스 + sleep 두 곳 설명 + 체크리스트에 "sleep(20) 제거", "synchronized 제거" 실험 추가
- **Ex04 신규**: 스레드 4개로 진행되는 콘솔 경마 게임. 기본 라이브러리만 사용.
  - 핵심 도구: `Thread` + `Runnable` + `ThreadLocalRandom` + `AtomicIntegerArray` + `AtomicInteger` + `synchronized` + ANSI escape (`\033[NA`, `\033[K`)
  - 각 말마다 평균 속도(`avgSleep` 25~80ms)와 매 걸음 흔들림(±jitter)이 무작위 → 매 실행마다 도착 순서 다름
  - 결승선 통과 시 `nextRank.getAndIncrement()`로 순위 자동 기록
  - `synchronized (SCREEN_LOCK)`로 출력 직렬화, `AtomicIntegerArray`로 진행률 가시성
  - `System.setOut(new PrintStream(System.out, true, UTF_8))`로 Windows 콘솔 한글 깨짐 방지
- docs/week09: Ex04 섹션 추가 (구성 요소 표 / 랜덤 도착 메커니즘 / 자주 묻는 함정), 체크리스트와 추가 실습 보강
- plan.md 9주차: Ex04와 Atomic 관련 항목 추가
- 루트 README 목차: 9주차 행에 "Ex04=경마 시각화" 라벨 추가
- 검증: `javac` exit 0, 실행 결과 매번 다른 순위로 종료
- **후속 수정**: 사용자 콘솔에서 한글 깨짐 발생 → 화면 출력을 모두 영문 ASCII로 교체 (말 이름 Thunderbolt/Cocoa/Silver/Rusty, "FINISH!", "Final Results" 등). `System.setOut(UTF_8)` 한 줄은 무해해서 유지. docs/week09 샘플 출력 블록도 새 영문 출력으로 갱신.

### Ex01도 진행바 2개 버전으로 재작성

- 기존 NumberTask 기반 숫자 출력 → 두 워커가 0~100% 진행바를 동시에 채워가는 형태로 교체
- Ex04 와 같은 ANSI 갱신 트릭을 쓰지만, 의도적으로 단순하게: 평범한 `int[]` 진행률, 카운트다운/메달 없음. Ex01은 어디까지나 `start()` + `join()` 시작 예제이므로 가시성·원자성은 Ex02/Ex04로 미룸 (소스 주석으로 명시).
- 평균 속도(`avgSleepA`, `avgSleepB`)를 출발 시 무작위(20~79ms)로 정해 매 실행마다 도착 순서가 다름.
- docs/week09 Ex01 섹션 전면 재작성: NumberTask 설명 제거, 핵심 두 줄(`start()`, `join()`) 코드 발췌 + 랜덤 속도 메커니즘 + 샘플 출력 추가. 체크리스트와 추가 실습에 "join 두 줄 지우기", "start 대신 run 호출하기", "워커 수 늘리기" 실험 추가.
- 검증: `javac` exit 0, 실행 시 두 워커 모두 100% 도달 후 `"Both finished."` 출력.
- IDE 진단 또 stale (`Duplicate method` 류 거짓 경고). 파일은 정상 (83줄, runWorker/redraw 각 1회). chapter09 작업 마치고 한 번에 Java Language Server Workspace 클린 권장.

### chapter09 단순화 — 예제 2개로 축소

- 배경: 학생 이해 난이도가 너무 높아짐 (synchronized + Atomic + 스레드 풀 + 시각화 한꺼번에)
- 결정: 시각화 흐름만 남기고 동시성 고급 주제 분리
  - **삭제**: 기존 `Ex02.java` (synchronized 계좌), `Ex03.java` (ExecutorService), `Ex04.java` (4스레드 경마). git 히스토리로 복구 가능
  - **이동**: 기존 `Ex01.java` (2-스레드 진행바, 직전 단계에서 만든 것) → `Ex02.java` 로 (클래스명 `Ex01` → `Ex02`)
  - **신규 Ex01**: 진행바 기본 틀. 단일 스레드(main 하나), `\r` + `flush` + `Thread.sleep` 만 사용. ANSI escape, AtomicInteger, synchronized 모두 미사용. 이후 Ex02 에서 같은 패턴을 두 스레드에 옮기는 자연스러운 단계 구성
- docs/week09/README 전면 재작성: 2 섹션 구조 (Ex01 기본 틀 / Ex02 두 스레드). 비교표 ("Ex01 vs Ex02 차이점 한눈에") 추가. 체크리스트는 `\r`을 `\n` 으로 바꿔보기, `flush` 빼기, `join` 빼기, `start` 대신 `run` 호출하기 같은 비교 실험으로 재구성
- plan.md 9주차: 학습 목표 / 이론 / 실습 / 퀴즈 모두 간단해진 구성으로 재작성. "synchronized/Atomic/ExecutorService 는 별도 보강 주차로 분리" 명시
- 루트 README: 9주차 행 라벨 "Ex01=기본 틀, Ex02=2스레드" 로 갱신
- 검증: `javac` exit 0, Ex01/Ex02 모두 실행 정상

### Ex01 추가 단순화 — 백분율 제거

- 사용자 피드백: "pct/% 빼라, 본질에 어긋나고 장황하다. 30칸으로 직접 관리하자"
- `int progress (0..100)` → `int filled (0..TRACK_WIDTH)` 로 변경. `render(int pct)` → `render(int filled)`.
- `pct * TRACK_WIDTH / 100` 변환 사라짐, `String.format(" %3d%%", pct)` 출력 사라짐 — 진행바 양 끝 `|` `|` 만 남음
- 한 걸음 진행을 1~4(%기준) 에서 1~2(칸 기준)로 자연스럽게 줄이고, 슬립을 40~79ms 에서 60~119ms 로 살짝 늘려 페이스 유지
- docs/week09 Ex01 섹션: 코드 스니펫·샘플 출력·체크리스트 모두 새 단순 버전으로 갱신. "진행률은 퍼센트가 아니라 채워진 칸 수로 직접 관리한다" 의도 한 줄 추가.



### 루트 README.md 한글 개요로 재작성

- 기존: 영어로 작성된 슬라이드 묶음(`Week01~05`) 설명 문서. 저장소 전체 안내 역할에 맞지 않았음.
- 변경: 한글 개요 + 빠른 시작 + 디렉터리 구조 + 10주차 목차 표 + 슬라이드/문제 은행/코드 관례/추가 자료 섹션.
- 목차 표는 각 주차의 `app/chapterNN/`과 `docs/weekNN/README.md`로 직접 링크.
- 슬라이드 묶음 소개는 `COURSE_SUMMARY.txt`로 링크만 남기고 본문에서는 제외.
- chapter04/06 같은 sub-package 구조는 "(ex01~ex03 패키지)" 표기로 따로 안내.

### chapter08에 표준 입출력(Ex01) 추가, 기존 Ex01~03 → Ex02~04로 시프트

- 의도: 파일 I/O 앞에 콘솔 I/O를 두어 "스트림에서 읽고 쓰기" 패턴을 자연스럽게 도입.
- 리네임 (역순 처리):
  - `Ex03.java` (직렬화) → `Ex04.java` — `public class Ex03` → `Ex04`, `week08_ex03.txt` 라벨도 같이 시프트 (실제로는 `user.dat`)
  - `Ex02.java` (버퍼) → `Ex03.java` — 출력 파일 `week08_ex02.txt` → `week08_ex03.txt`
  - `Ex01.java` (문자 파일) → `Ex02.java` — 출력 파일 `week08_ex01.txt` → `week08_ex02.txt`
- 신규 `Ex01.java`:
  - `Scanner(System.in)`으로 이름(`nextLine`)/나이(`nextInt`) 입력
  - `System.out.printf` 서식 데모: `%s`, `%d`, `%5d`, `%.2f`, `%n`
  - `try-with-resources`로 `Scanner` 닫기 (다른 예제와 패턴 통일)
- 검증: `javac` exit 0, `Ex01`에 `printf "홍길동\n25\n"` 파이프로 stdin 주입 후 실행 exit 0. Ex02~Ex04도 전부 exit 0.
- 문서 갱신:
  - `docs/week08/README.md`: 제목·학습 목표·핵심 개념·실행 방법·예제별 설명·체크리스트·퀴즈 모두 새 4단 구성으로 재작성. 콘솔 I/O와 파일 I/O가 같은 "스트림" 모양이라는 학습 흐름 강조.
  - `_forAI/plan.md` 8주차: 4단계 예제 + `nextInt` 함정 + `printf` 서식 항목 추가
  - 루트 `README.md` 목차: 8주 행을 "표준 I/O와 파일 입출력 / Ex01~Ex04"로 갱신
- 미반영: `docs/Week08_FileIOSerialization.pptx` 슬라이드 (바이너리, 재생성 시 반영)

---

## 2026-05-12

### Java 개발 환경 세팅 + `_forAI` 표준 scaffold 재구성

- JDK 설치: `winget install Microsoft.OpenJDK.21` → `Microsoft Build of OpenJDK 21.0.11 LTS` (Hotspot).
  - `JAVA_HOME = C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot\` (Machine scope) 자동 등록 확인.
  - `PATH`에 `...\bin` 추가 확인.
- VSCode `Extension Pack for Java` 사전 설치 확인 (`vscjava.vscode-java-pack` 외 6종).
- 워크스페이스 설정 신규: `.vscode/settings.json`
  - `java.configuration.runtimes`에 JavaSE-21 등록 (default)
  - `java.project.sourcePaths = ["app"]`, `outputPath = "bin"`
  - UTF-8 / LF / 탭 4칸
- `.gitignore` 사용자 직접 수정: `.vscode/` 줄 제거 → 워크스페이스 설정이 학생들과 공유 가능해짐.
- 검증: `javac -d bin app\chapter01\Ex01.java` && `java -cp bin chapter01.Ex01 hello world`
  - 출력 정상 (`Hello, Java World!` + args).
- `_forAI` 표준 scaffold 재구성:
  - `readme.md` → scaffold 가이드 형식으로 갱신 (읽는 순서/문서 역할/현재 스냅샷)
  - `inventory.md` 신규 생성 (실제 저장소 구조, 명령, 툴체인)
  - `memo.md` 신규 생성 (JDK 비교, 수업 운영, 함정, 반복 금지)
  - `work_log.md` → `dev_log.md`로 마이그레이션, 원본 삭제
  - `plan.md` 유지 (커리큘럼 내용 그대로)

### Java 관례 적용 — 클래스명/파일명 PascalCase 일괄 정리

- 대상: chapter 01/02/03/05/07/08/09/10의 `exNN.java` 26개 (chapter04/06은 이미 PascalCase)
- 파일 rename: Windows case-insensitive FS 대응 2단계 rename (`__tmp_` 경유)
- 클래스 선언 갱신: `public class exNN` → `public class ExNN` (case-sensitive regex 비교 필수 — PS `-eq`는 기본 case-insensitive 함정에 주의)
- 패키지명은 Java 관례상 소문자 유지 (`package chapter01;`, `package chapter04.ex01;`)
- 문서 참조 일괄 교체: `_forAI/*.md`, `docs/weekNN/README.md`, `COURSE_SUMMARY.txt`
- 검증:
  - 전체 일괄 컴파일 `javac @sources.txt` → exit 0, .class 55개 생성
  - 대표 실행 (chapter01~08) → 전부 exit 0
- 미반영 (별도 작업 필요): `docs/Week*.pptx` 슬라이드 내 코드 스니펫 — 슬라이드 재생성 시 같이 반영

### docs/week06 소스-문서 정합 + 잔여 명명 정리

- 사용자 지적: chapter06 소스와 docs/week06/README.md 내용이 어긋남
- `docs/week06/README.md`를 실제 소스(`Ex01`/`PartyManager`/`GameCharacter`, `Ex02`/`ItemManager`/`ItemSpec`) 기준으로 전면 재작성:
  - `Set` 언급 삭제(소스에 없음)
  - 각 예제 6단계 시연(`createAndAddExample` ~ `sortAndClearExample`, `createAndPutExample` ~ `extraExample`)을 표로 정리
  - 실제 메서드(`insertCharacter`, `sortByLevelDesc`, `Comparator.comparingInt(...).reversed()`, `Collections.reverse`, `getOrDefault`, `containsKey`, `entrySet()` 순회 등)을 본문/퀴즈에 반영
  - 빈 ```bash``` 블록 제거, "Bob" 같은 출처 불명 예시 제거
- `_forAI/plan.md` 보정:
  - 4주차: `chapter04/ex01.java` → `chapter04/ex01/Ex01.java` 외 sub-package 구조 명시
  - 5주차: 누락되어 있던 `Ex04` (날짜 계산) 추가
  - 6주차: `ex03` 잘못된 언급 제거, 실제 RPG 테마(파티/아이템 도감)로 실습/퀴즈 갱신
- 잔여 PascalCase 명명 일괄 정리(직전 패스에서 누락된 부분):
  - `docs/week*/README.md` "클래스: exNN, ..." 줄 (week04 제외, 8개)
  - `docs/week*/README.md` 모든 `### exNN` 섹션 헤더
  - `docs/week*/README.md` 본문의 \`exNN\` 백틱 라벨 (10개 파일)
- 패키지 참조(`chapter04.ex01` 등 sub-package 이름)는 Java 관례상 소문자 유지가 맞아 건드리지 않음
- 검증: 전체 컴파일은 .java 파일에 영향 없는 문서 변경이라 별도 재컴파일 생략

---

## 2026-03-24

### Update - Vanilla Java Cleanup

- 수업 방향을 Gradle 기반 실행에서 `java`/`javac` 중심 실습으로 전환.
- 루트 `README.md`, `docs/week01`~`docs/week10`, `_forAI/plan.md`를 커맨드라인 기준으로 재작성.
- Java 문법 학습과 직접 관련 없는 Gradle 설정, 래퍼, 캐시, Kotlin 비교 예제(`app-kotlin`)를 정리.
- 파일 I/O 예제 출력 경로를 `app/build/tmp`에서 `out/files`로 변경.
- `startup/App.java` 같은 Gradle 초기 템플릿 흔적 삭제.
- 예제 폴더 구조를 `app/chapter01` ~ `app/chapter10` 형태로 단순화하고 패키지도 동일하게 정리.
- 참고: 아래의 Gradle 관련 기록은 과거 작업 이력으로 남겨 둔 내용.

---

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
