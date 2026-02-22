# Java Study Template (Gradle)

교재용 예제를 Gradle 멀티모듈로 통합한 템플릿입니다.
예제는 `hello`를 1번으로 하여 순차 번호 폴더로 정리했습니다.

## 개발 환경 기준

- JDK: `Java 21 (LTS)`
- Gradle: Wrapper 사용 (`./gradlew`, 저장소 내 버전 고정)

## Windows PowerShell 기본 세팅

```powershell
# 현재 세션만 Java 21로 설정
$jdk = Get-ChildItem 'C:\Program Files\Eclipse Adoptium' -Directory |
  Where-Object Name -like 'jdk-21*' |
  Sort-Object LastWriteTime -Descending |
  Select-Object -First 1 -ExpandProperty FullName

$env:JAVA_HOME = $jdk
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

java -version
javac -version
```

- 영구 반영(User 환경 변수)은 다음과 같이 설정 후 터미널을 다시 열어 적용합니다.

```powershell
$jdk = Get-ChildItem 'C:\Program Files\Eclipse Adoptium' -Directory |
  Where-Object Name -like 'jdk-21*' |
  Sort-Object LastWriteTime -Descending |
  Select-Object -First 1 -ExpandProperty FullName

[Environment]::SetEnvironmentVariable('JAVA_HOME', $jdk, 'User')
[Environment]::SetEnvironmentVariable('Path', "$jdk\bin;$([Environment]::GetEnvironmentVariable('Path','User'))", 'User')
```

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

`ex05_swing_basic`는 아래처럼 분리 실행도 가능합니다.

```bash
# exam01
./gradlew :ex05_swing_basic:runExam01

# exam02
./gradlew :ex05_swing_basic:runExam02

# run 태스크에서 메인 클래스 직접 지정
./gradlew :ex05_swing_basic:run "-PappMain=exam01.mainFrame"
./gradlew :ex05_swing_basic:run "-PappMain=exam02.MainFrame"
```

PowerShell에서는 `-PappMain=exam02.MainFrame`처럼 점(`.`)이 포함된 값이 분리될 수 있어
`"-PappMain=..."`처럼 따옴표를 권장합니다. (`--%` 사용도 가능)
