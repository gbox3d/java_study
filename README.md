# Java 수업 교재

10주 자바 수업용 예제 코드와 강의 자료 저장소입니다.
빌드 도구 없이 `javac` / `java` 명령만으로 진행해 학생이 컴파일·실행 흐름을 직접 손에 익히는 데 초점을 둡니다.

## 빠른 시작

요구 사항: **JDK 21 LTS** (이 저장소는 Microsoft Build of OpenJDK 21 기준으로 검증).

```powershell
# 단일 예제 컴파일·실행
javac -encoding UTF-8 -d bin app\chapter01\Ex01.java
java -cp bin chapter01.Ex01
```

VSCode 사용 시 `Extension Pack for Java`만 설치하면 `app/chapter01/Ex01.java`를 열고 ▶ 버튼으로 바로 실행할 수 있습니다.
워크스페이스 설정은 [.vscode/settings.json](.vscode/settings.json) 참조.

## 디렉터리 구조

```
java_study/
├── app/              # 주차별 예제 소스 (Ex01.java, Ex02.java ...)
│   ├── chapter01/    ~ chapter10/
├── docs/             # 강의 슬라이드(.pptx)와 주차별 README
│   ├── week01/       ~ week10/
│   ├── Week01_JavaBasics.pptx ~ Week10_NetworkCommunication.pptx
│   └── question_bank_chapter01_06.md (+ answers)
├── _forAI/           # AI 작업 문맥 (inventory/plan/memo/dev_log)
├── .vscode/          # VSCode 워크스페이스 설정
└── README.md
```

## 주차별 목차

| 주차 | 주제 | 예제 소스 | 보충 자료 |
|---|---|---|---|
| 1주 | 자바 시작과 기본 문법 | [app/chapter01/](app/chapter01/) (Ex01~Ex04) | [docs/week01/](docs/week01/README.md) |
| 2주 | 배열과 메서드 | [app/chapter02/](app/chapter02/) (Ex01~Ex03) | [docs/week02/](docs/week02/README.md) |
| 3주 | 클래스와 객체 | [app/chapter03/](app/chapter03/) (Ex01~Ex03) | [docs/week03/](docs/week03/README.md) |
| 4주 | 상속과 다형성 (RPG 테마) | [app/chapter04/](app/chapter04/) (ex01~ex03 패키지) | [docs/week04/](docs/week04/README.md) |
| 5주 | 예외 처리와 표준 API | [app/chapter05/](app/chapter05/) (Ex01~Ex04) | [docs/week05/](docs/week05/README.md) |
| 6주 | 컬렉션 프레임워크 | [app/chapter06/](app/chapter06/) (ex01~ex02 패키지) | [docs/week06/](docs/week06/README.md) |
| 7주 | 제네릭, 람다, 스트림 | [app/chapter07/](app/chapter07/) (Ex01~Ex03) | [docs/week07/](docs/week07/README.md) |
| 8주 | 표준 I/O와 파일 입출력 | [app/chapter08/](app/chapter08/) (Ex01~Ex04) | [docs/week08/](docs/week08/README.md) |
| 9주 | 스레드 기본 | [app/chapter09/](app/chapter09/) (Ex01=진행바 틀, Ex02=두 스레드, Ex03=스톱워치 UI) | [docs/week09/](docs/week09/README.md) |
| 10주 | 네트워크 기초 | [app/chapter10/](app/chapter10/) (Ex01~Ex03) | [docs/week10/](docs/week10/README.md) |

각 주차 폴더(`docs/weekNN/README.md`)에 학습 목표, 핵심 개념, 실행 방법, 실습 체크리스트, 퀴즈 예시가 정리되어 있습니다.

## 강의 슬라이드

`docs/Week01_JavaBasics.pptx` ~ `docs/Week10_NetworkCommunication.pptx` (10개, 한국어 + 영문 기술용어).
파워포인트 또는 LibreOffice/Google Slides에서 열어볼 수 있습니다.

## 문제 은행

- [docs/question_bank_chapter01_06.md](docs/question_bank_chapter01_06.md) — 1~6주차 문제
- [docs/question_bank_chapter01_06_answers.md](docs/question_bank_chapter01_06_answers.md) — 해설

## 코드 관례

- **클래스명/파일명**: `PascalCase` (예: `Ex01`, `PartyManager`, `ItemSpec`)
- **패키지명**: 소문자 (예: `chapter01`, `chapter04.ex01`)
- **인코딩**: UTF-8 / LF / 들여쓰기 4칸
- **산출물 폴더**: `bin/` (VSCode 기본) 또는 `out/classes/` — 둘 다 `.gitignore`됨

## 추가 자료

- [_forAI/](_forAI/) — AI/이어 받는 협업자를 위한 작업 문맥 (저장소 구조, 운영 메모, 작업 이력, 커리큘럼 계획)
- [COURSE_SUMMARY.txt](COURSE_SUMMARY.txt) — 10주 슬라이드 생성 이력과 통계
