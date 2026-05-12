# _forAI Guide

기준 시점: 2026-05-12

이 디렉터리는 `java_study` (10주 자바 수업 교재) 작업을 이어받을 때 필요한 AI 작업 문맥을 정리해 두는 곳이다.
실제 소스와 실행 기준은 저장소 루트와 `inventory.md`를 우선 본다.

## 읽는 순서

1. `readme.md` (이 문서)
2. `inventory.md` — 저장소 구조, 컴파일/실행 명령, 엔트리포인트
3. `memo.md` — 수업 운영/JDK/디버깅 참고 메모
4. `dev_log.md` — 날짜별 작업 이력
5. `plan.md` — 10주 커리큘럼과 주차별 학습 목표

## 문서 역할

- `inventory.md`: 저장소에 실제로 있는 구조, 엔트리포인트, 빌드/검증 명령.
- `plan.md`: 10주 커리큘럼 (이론/실습/퀴즈 구성). 앞으로 진행할 계획.
- `memo.md`: 프로토콜, 기본값, 디버깅 교훈, 수업 운영 메모.
- `dev_log.md`: 날짜별 작업 이력과 `_forAI` 정리 내역.

## 현재 스냅샷

- 저장소 경로: `d:\works\java_study`
- 대상 플랫폼: JDK 21 LTS (Microsoft Build of OpenJDK)
- 빌드 도구: 없음 — `javac`/`java` 만으로 진행
- 커리큘럼 기준 소스: `app/chapter01/` ~ `app/chapter10/`
- 강의 슬라이드/문제: `docs/week01/` ~ `docs/week10/`, `docs/*.pptx`, `docs/question_bank_*.md`

## 유지 규칙

- 계획이 아닌 참고 정보는 `plan.md`가 아니라 `memo.md`에 둔다.
- 저장소 구조나 실행 명령이 바뀌면 `inventory.md`를 먼저 갱신한다.
- 작업 이력은 날짜를 붙여 `dev_log.md`에만 남긴다.
- 새 작업을 시작할 때는 `inventory.md`와 `memo.md`를 먼저 읽고, 실제 할 일은 `plan.md`에서 확인한다.
- 빌드 도구(Maven/Gradle) 추가는 수업 방향(`java`/`javac` 중심) 결정과 충돌하므로 사용자 확인 후에만 진행.
