# Memo

## JDK / 개발 환경 기준

- JDK: Microsoft Build of OpenJDK **21.0.11 LTS** (Hotspot)
  - 설치: `winget install Microsoft.OpenJDK.21`
  - `JAVA_HOME = C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot\`
  - `PATH`에 `%JAVA_HOME%\bin` 포함됨 (winget이 자동 등록)
- VSCode 확장: Extension Pack for Java (`vscjava.vscode-java-pack`)
  - 자동 동반 설치: `redhat.java`, `vscjava.vscode-java-debug`, `-test`, `-dependency`, `-maven`, `-gradle`
- 인코딩: 모든 소스/콘솔 출력 UTF-8 기준. 컴파일 시 `-encoding UTF-8` 명시.

## 자바 배포판 비교 (수업 설명용)

| 배포판 | 라이선스 | 가입 필요 | 비고 |
|---|---|---|---|
| Oracle JDK | NFTC (개인 무료, 상용 일부 유료) | △ | 라이선스 헷갈림 |
| Microsoft OpenJDK | GPLv2+CE (무료) | ✕ | 본 프로젝트 선택. winget 호환 |
| Eclipse Temurin (Adoptium) | GPLv2+CE | ✕ | 가장 흔한 커뮤니티 선택 |
| Red Hat OpenJDK | GPLv2+CE | ◯ | developers.redhat.com 가입 필요 |

→ 어느 배포판이든 컴파일/실행 결과는 동일. 수업에서는 라이선스 부담 없고 가입 불필요한 것을 권장.

## 수업 운영 메모

- 첫 주에는 IDE 자동완성보다 **컴파일 오류 메시지 읽기**를 우선 가르친다.
- 예제 폴더 단위로 패키지 선언이 묶여 있으므로, 학생이 `package` 라인을 지우면 컴파일 위치/실행 클래스명이 함께 바뀌어야 함을 강조.
- 산출물 폴더는 `bin/` 또는 `out/classes/` 둘 다 `.gitignore`되어 있어 자유롭게 선택 가능. VSCode 기본은 `bin/`.
- 매주 마지막 10~15분은 퀴즈. `docs/question_bank_chapter01_06.md`에 1~6주차 문제 풀이용 문제 은행 보관.

## 자주 나오는 함정 (디버깅 참고)

- **클래스명과 파일명**: 파일명은 그 안의 `public class` 이름과 글자 그대로(대소문자 포함) 일치해야 한다. 대부분 PascalCase (`Ex01.java` / `public class Ex01`), chapter09 만 `Ex01_01` 같은 `ExNN_MM` 형태.
- **패키지 vs 실행 디렉터리**: `package chapter01;` 선언이 있으면 `-cp bin chapter01.Ex01` 형태로 실행해야 함. 직접 `java Ex01.java`를 chapter01 폴더 안에서 호출하면 패키지 불일치로 실패.
- **한글 출력 깨짐**: PowerShell 콘솔 코드페이지가 949(CP949)이면 한글 출력이 깨질 수 있음. `chcp 65001` 또는 Windows Terminal + UTF-8 셸 권장.
- **`switch`의 fall-through**: 1주차 예제 `Ex04`에서 `break` 유무에 따른 동작 차이를 반드시 라이브 코딩으로 비교.
- **배열 길이 vs 문자열 길이**: `args.length` (필드) vs `String.length()` (메서드). 2주차에 같이 보여주는 게 좋음.

## 반복 금지 (과거 실수)

- Gradle 멀티모듈로 한 번 갔다가 수업 흐름에 맞지 않아 되돌렸음 (`dev_log.md` 2026-02-22, 2026-03-24 참조). 빌드 도구 도입 제안 시 반드시 사용자 확인.
- `out/`, `bin/` 같은 산출물 폴더를 커밋한 적 있었음. 새 폴더 만들 때 `.gitignore` 확인 필수.
