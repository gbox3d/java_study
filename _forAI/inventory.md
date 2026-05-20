# Inventory

## Repository

- Name: `java_study`
- Path: `d:\works\java_study`
- Summary: 10주짜리 자바 수업 교재용 단일 저장소. 빌드 도구 없이 `javac`/`java`만으로 실습. 각 chapter 폴더가 하나의 패키지에 대응.

## Top-level structure

```
java_study/
├── .vscode/
│   └── settings.json            # JDK 21 런타임 지정, source path = app
├── _forAI/                      # AI 작업 문맥 (이 디렉터리)
├── app/
│   ├── chapter01/  ex01.java ~ ex04.java
│   ├── chapter02/  배열과 메서드
│   ├── chapter03/  클래스와 객체
│   ├── chapter04/  상속과 다형성
│   ├── chapter05/  예외 처리/표준 API
│   ├── chapter06/  컬렉션 프레임워크
│   ├── chapter07/  제네릭/람다/스트림
│   ├── chapter08/  파일 입출력
│   ├── chapter09/  스레드 + VT 터미널 제어
│   └── chapter10/  네트워크 기초
├── docs/
│   ├── week01/ ~ week10/        # 주차별 보조 문서
│   ├── Week01_JavaBasics.pptx   # ~ Week10_NetworkCommunication.pptx
│   ├── question_bank_chapter01_06.md
│   └── question_bank_chapter01_06_answers.md
├── COURSE_SUMMARY.txt
├── README.md                    # 슬라이드 묶음 개요 (영어)
├── .gitignore                   # bin/, out/, .vscode/, .metadata/, *.class
└── .gitattributes
```

## Entrypoints and key modules

- 각 `app/chapterNN/ExNN.java` 파일이 `public static void main(...)`을 가진 독립 실행 엔트리포인트.
- 패키지 선언은 폴더 이름과 동일 (`package chapter01;` 등 — 소문자 유지가 Java 패키지 관례).
- 클래스명은 PascalCase 관례를 따른다 (`Ex01`, `Ex02`, ...). chapter09 는 예제가 두 갈래(진행바 / 타이머)라 `Ex01_01` ~ `Ex02_03` 의 `ExNN_MM` 형태를 쓴다.
- chapter04/chapter06은 한 단원이 여러 클래스로 구성되어 하위 패키지(`chapter04.ex01`, `chapter06.ex01` 등)에 들어 있고, 그 안의 메인 진입점도 `Ex01.java`/`Ex02.java`/`Ex03.java`.

## Build and validation commands

### 단일 파일 컴파일·실행 (PowerShell)

```powershell
javac -encoding UTF-8 -d bin app\chapter01\Ex01.java
java -cp bin chapter01.Ex01
```

### 전체 예제 일괄 컴파일

```powershell
Get-ChildItem -Recurse -Filter *.java app | ForEach-Object { $_.FullName } | `
  Set-Content $env:TEMP\sources.txt
javac -encoding UTF-8 -d bin "@$env:TEMP\sources.txt"
```

bash/zsh 버전 (`plan.md`에 기록):

```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter01/Ex01.java
java -cp out/classes chapter01.Ex01
find app -name "*.java" | xargs javac -encoding UTF-8 -d out/classes
```

### 산출물 위치

- VSCode 기본: `bin/`
- 수업 매뉴얼 표기: `out/classes/`, 파일 I/O 실습 결과: `out/files/`
- 둘 다 `.gitignore`에 등록됨.

## Tests

- 자동화된 테스트 없음. 검증은 각 예제 main 실행 결과 육안 확인.
- 1주차 검증: `chapter01.Ex01` 실행 시 `Hello, Java World!` 출력 확인.

## Toolchain

- JDK: Microsoft Build of OpenJDK 21.0.11 LTS
  - 설치 경로: `C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot\`
  - `JAVA_HOME` 시스템 환경변수에 등록됨
  - `winget install Microsoft.OpenJDK.21`로 설치
- IDE: VSCode + Extension Pack for Java (`vscjava.vscode-java-pack`)
  - 워크스페이스 설정은 `.vscode/settings.json` 참조

## Notes

- 빌드 도구(Maven/Gradle)는 의도적으로 배제. 수업이 "컴파일 오류 읽기 → 수정 → 재실행" 흐름을 강조하기 위함.
- 과거에 Gradle 멀티모듈로 구성한 이력이 있으며, 현재는 정리됨 (`dev_log.md` 2026-03-24, 2026-02-22 항목 참조).
- 컬렉션/스레드/네트워크 단원은 표준 라이브러리만 사용. 외부 의존성 없음.
