# Java Study 레거시 분석 정리

이 문서는 교재 제작 전에 기존 실습 폴더를 빠르게 정리하기 위한 현황 메모입니다.

## 기준 선언

- 현재 브랜치: `lagacy_2024`
- 이 브랜치의 레포지터리 전체를 레거시 코드베이스로 간주합니다.
- 향후 교재 제작을 위해 전체 리팩토링을 진행합니다.
- 프로젝트 관리는 최종적으로 Gradle 기반으로 통일하는 것을 목표로 합니다.

## 1) 현재 상태 요약

- 루트에 `legacy`라는 이름의 폴더는 없습니다.
- 대신 레거시로 보이는 실습 폴더가 루트에 분산되어 있습니다.
- 빌드 시스템이 혼재합니다.
  - Gradle: `app`
  - Maven: `maven_exam`, `javafx_sample`, `rtspsample`, `rtsp_opencv`
  - VSCode 단순 Java 프로젝트: `chapter*`, `hello`, `project/*`
- 일부 프로젝트에 `target/`, `bin/` 같은 산출물이 이미 포함되어 있습니다.

## 2) 폴더별 분석

### 기초 문법/객체지향

- `chapter3`
  - 생성자, 클래스 접근 범위, 메서드 오버로딩 예제
  - 파일: `App.java`, `exam01.java`, `exam02.java`, `exam03.java`

- `chapter6_package`
  - package 사용, 사용자 정의 클래스 전달(참조형 동작 체감) 예제
  - 파일: `mypack/AtomicInteger.java`, `mypack/test1.java`, `App.java`

- `chapter7`
  - `Vector<Integer>` 컬렉션 사용, `toArray()`, 참조/값 변화 체감 예제

### Swing GUI

- `chapter9`
  - JFrame 기본 생성, 버튼/레이아웃(FlowLayout) 실습

- `chapter10`
  - 이벤트 리스너(`ActionListener`) 기반 버튼 클릭 처리

- `project/helloJframe`
  - Swing + 스레드 + EDT(`SwingUtilities.invokeLater`) 학습용 샘플
  - 카운터를 별도 스레드에서 갱신하여 GUI 업데이트

### JavaFX / RTSP / 외부 라이브러리

- `javafx_sample`
  - JavaFX 화면 전환(FXML) + RTSP 재생(FFmpeg/Javacv) 시도
  - 하드코딩된 RTSP URL 존재
  - `target/` 산출물 포함

- `rtspsample`
  - JavaFX + VLCJ 기반 RTSP 재생 시도
  - `main.fxml`의 `fx:controller`와 실제 컨트롤러 클래스 구성이 맞지 않아 정리 필요

- `rtsp_opencv`
  - JavaFX + OpenCV 기반 RTSP 재생 샘플
  - `artifactId`가 `rtspsample`로 되어 있어 프로젝트 식별자 정리 필요

### 기타

- `project/uartSample`
  - `jSerialComm`를 이용한 시리얼 포트 탐색 예제
  - README에 컴파일/실행/jar 패키징 절차가 비교적 잘 정리됨

- `hello`
  - 기본 Hello World 템플릿

- `maven_exam`
  - Maven 기본 템플릿 성격
  - Java 1.7/JUnit4 기반으로 비교적 오래된 설정

- `app`
  - Gradle init 기본 샘플 (`java_study.App`)
  - 현재 루트 프로젝트 기준점으로 쓰기 가장 깔끔한 상태

## 3) 교재용 구조로 정리할 때 권장 기준

### 우선 보존(교재 본문 후보)

- `chapter3`, `chapter6_package`, `chapter7`
- `chapter9`, `chapter10`
- `project/helloJframe` (스레드/GUI 설명용)
- `project/uartSample` (외부 라이브러리 연동 예시용)

### 별도 부록/심화로 분리

- `javafx_sample`, `rtspsample`, `rtsp_opencv`
  - RTSP URL, 네이티브 라이브러리, OS 의존성이 있어 본문보다 심화 부록에 적합

### 정리/삭제 후보

- 템플릿 중복 README(대부분 동일한 VSCode 기본문구)
- 빌드 산출물: `**/target`, `**/bin` (필요 시 `.gitignore`로 관리)
- 목적이 겹치는 HelloWorld 템플릿(`hello`, `maven_exam`, `app`)은 하나만 기준 프로젝트로 선정

## 4) 빠른 정리 우선순위 (추천)

1. 레거시 폴더를 물리적으로 분리: `legacy/` 디렉터리 생성 후 기존 실습 폴더 이동
2. 교재 본문용/부록용을 분리: `core/` vs `advanced/`
3. 빌드 도구 통일(가능하면 Gradle 또는 Maven 한 가지)
4. 하드코딩 값 정리(RTSP URL, OS 경로)
5. 각 예제 README를 "학습목표/실행방법/핵심코드" 3섹션으로 통일

## 5) Gradle 전환 리팩토링 로드맵(교재 기준)

1. 루트를 Gradle 멀티모듈로 고정하고 모듈명 표준화 (`chapter03`, `chapter06-package`, `swing-ch09`, `javafx-rtsp` 등)
2. Maven 프로젝트(`maven_exam`, `javafx_sample`, `rtspsample`, `rtsp_opencv`)를 순차적으로 Gradle로 이전
3. 공통 Java Toolchain 버전(예: 17)과 테스트 프레임워크(JUnit5) 통일
4. 실행 예제는 `application` 플러그인으로 `mainClass` 명시, 교재 실행 명령을 `./gradlew :module:run` 형태로 통일
5. 빌드 산출물(`target`, `bin`) 제거 후 `.gitignore` 정책 정리
