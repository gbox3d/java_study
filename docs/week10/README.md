# Week 10 - 네트워크 통신 및 채팅 서버

## 학습 목표
- UDP/TCP 소켓 기본 실습
- Echo 통신 흐름 이해
- 멀티 클라이언트 채팅 서버 구조 파악

## 예제 클래스
- 패키지: chapter10
- 클래스: Ex01, Ex02, Ex03

## 이번 주 핵심 개념
- 네트워크 통신은 다른 프로세스와 데이터를 주고받는 과정이다.
- UDP는 빠르지만 연결 보장이 없고, TCP는 연결을 맺고 안정적으로 주고받는다.
- 소켓은 네트워크 통신의 출입구 역할을 한다.
- 서버는 요청을 기다리고, 클라이언트는 서버에 접속해 메시지를 보낸다.
- 이 주차는 응용 단원으로 보고, 완성 구현보다 통신 흐름과 구조 이해를 우선한다.

## 실행 방법
```bash
mkdir -p out/classes
javac -encoding UTF-8 -d out/classes app/chapter10/Ex01.java
javac -encoding UTF-8 -d out/classes app/chapter10/Ex02.java
javac -encoding UTF-8 -d out/classes app/chapter10/Ex03.java

java -cp out/classes chapter10.Ex01
java -cp out/classes chapter10.Ex02
java -cp out/classes chapter10.Ex03
```

소스 파일 하나만 바로 실행할 때:

```bash
java app/chapter10/Ex01.java
java app/chapter10/Ex02.java
java app/chapter10/Ex03.java
```

## 예제별 설명

### Ex01 - UDP 송수신
대상 소스: `app/chapter10/Ex01.java`

이 예제는 하나의 프로그램 안에서 수신자 스레드와 송신자 소켓을 함께 만들어 UDP 통신을 보여준다.

먼저 수신 스레드를 준비한다.

```java
Thread receiver = new Thread(() -> {
    try (DatagramSocket socket = new DatagramSocket(0)) {
        portHolder[0] = socket.getLocalPort();
        ready.countDown();
```

- `new DatagramSocket(0)`은 사용 가능한 임의 포트를 자동 배정받는다.
- `getLocalPort()`로 실제 포트 번호를 확인한다.
- `CountDownLatch`는 "수신 준비 완료" 시점을 메인 스레드에 알려준다.

수신 부분은 아래 흐름이다.

```java
byte[] buffer = new byte[1024];
DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
socket.receive(packet);
```

- 패킷을 담을 버퍼를 준비한다.
- `receive()`는 패킷이 올 때까지 기다린다.

송신자는 준비 완료 후 패킷을 보낸다.

```java
DatagramPacket packet = new DatagramPacket(
        data,
        data.length,
        InetAddress.getByName("127.0.0.1"),
        portHolder[0]
);
sender.send(packet);
```

- 루프백 주소 `127.0.0.1`은 자기 자신을 의미한다.
- 수신 스레드가 열어 둔 포트로 메시지를 보낸다.

### Ex02 - TCP Echo
대상 소스: `app/chapter10/Ex02.java`

이 예제는 서버와 클라이언트를 한 프로그램 안에서 순서대로 실행해 TCP 흐름을 보여준다.

서버 쪽 핵심:

```java
try (ServerSocket serverSocket = new ServerSocket(0)) {
    portHolder[0] = serverSocket.getLocalPort();
    ready.countDown();

    try (Socket client = serverSocket.accept();
```

- `ServerSocket`이 연결 요청을 기다린다.
- `accept()`는 클라이언트가 접속할 때까지 대기한다.

데이터를 읽고 다시 보내는 부분:

```java
String msg = in.readLine();
out.println("echo: " + msg);
```

- 한 줄을 읽고
- 그대로 앞에 `"echo: "`를 붙여 되돌려준다

클라이언트 쪽 핵심:

```java
try (Socket socket = new Socket("127.0.0.1", portHolder[0]);
```

- 서버가 연 포트로 접속한다.
- 연결 후 메시지를 보내고 응답을 읽는다.

### Ex03 - 채팅 서버 구조 읽기
대상 소스: `app/chapter10/Ex03.java`

이 예제는 완성형 실행보다 "멀티 클라이언트 채팅 서버가 어떤 구조로 동작하는지"를 읽어 보는 템플릿에 가깝다.

서버 시작 메서드:

```java
static void startServer() {
    ExecutorService pool = Executors.newFixedThreadPool(10);
```

- 여러 사용자를 동시에 처리하기 위해 스레드 풀을 사용한다.

접속 대기 루프:

```java
while (!Thread.currentThread().isInterrupted()) {
    Socket socket = serverSocket.accept();
    pool.execute(new ClientHandler(socket));
}
```

- 접속이 들어오면 소켓 하나를 받는다.
- 각 클라이언트를 `ClientHandler`에 맡긴다.

전체 사용자에게 메시지를 보내는 메서드:

```java
static void broadcast(String message) {
    synchronized (CLIENTS) {
        for (PrintWriter writer : CLIENTS) {
            writer.println(message);
        }
    }
}
```

- 연결된 모든 클라이언트 출력 스트림에 메시지를 보낸다.
- 여러 스레드가 동시에 접근하므로 `synchronized`로 보호한다.

이 예제에서 꼭 이해해야 할 포인트:
- 네트워크는 결국 "소켓 생성 -> 연결/수신 대기 -> 읽기/쓰기 -> 종료" 흐름이다.
- 단일 클라이언트와 멀티 클라이언트 구조는 서버 루프와 스레드 처리 방식에서 차이가 난다.

## 실습 체크리스트

- `Ex01`을 실행해 UDP 메시지가 어떻게 전달되는지 확인했다.
- `Ex02`를 실행해 서버와 클라이언트 흐름을 순서대로 설명할 수 있다.
- `Ex03`에서 `startServer()`, `broadcast()`, `ClientHandler`의 역할을 구분할 수 있다.
- UDP와 TCP 차이를 한 문장으로 설명할 수 있다.

## 퀴즈 예시
- UDP와 TCP의 가장 큰 차이는 무엇인가?
- `ServerSocket`과 `Socket`의 역할은 어떻게 다른가?
- `CountDownLatch`를 왜 사용했는가?
- `broadcast()`에서 동기화가 필요한 이유는 무엇인가?

## 추천 추가 실습
- `Ex01`에서 메시지를 두 번 보내기
- `Ex02`에서 다른 문자열을 보내 Echo 결과 확인하기
- `Ex03`에 접속자 수 출력 로직 추가하기
- `startServer()`를 실제로 호출해 단순 채팅 서버로 확장해 보기
