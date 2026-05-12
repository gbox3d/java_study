# Java REST API 3시간 수업 교재 초안

## 수업 주제

**Java로 REST API 사용하기: 포켓몬 도감과 카드 뽑기 게임 만들기**

이 수업은 Java 콘솔 프로그램을 이용하여 REST API 호출, JSON 파싱, 예외 처리, 간단한 게임 로직 구현까지 경험하는 것을 목표로 한다.

---

## 전체 수업 구성

| 시간 | 내용 | 핵심 개념 |
|---:|---|---|
| 0:00 ~ 0:20 | REST API 개념 설명 | HTTP, GET, URL, JSON |
| 0:20 ~ 1:00 | 포켓몬 도감 만들기 | API 호출, JSON 파싱, 데이터 출력 |
| 1:00 ~ 1:10 | 휴식 |  |
| 1:10 ~ 2:00 | 카드 뽑기 게임 만들기 | 상태값, deck_id, 게임 로직 |
| 2:00 ~ 3:00 | 퀴즈/실습 문제 풀이 | 코드 해석, 오류 수정, 기능 추가 |

---

# 1. 수업 목표

수업이 끝나면 학생은 다음을 할 수 있어야 한다.

1. REST API가 무엇인지 설명할 수 있다.
2. Java `HttpClient`를 사용하여 외부 API에 GET 요청을 보낼 수 있다.
3. JSON 응답에서 필요한 값을 꺼낼 수 있다.
4. API 호출 중 발생하는 오류를 처리할 수 있다.
5. API 응답 데이터를 이용해 간단한 콘솔 앱과 게임을 만들 수 있다.

---

# 2. 준비물

## 개발 환경

- JDK 17 이상
- IntelliJ IDEA 또는 VS Code
- Maven 프로젝트
- 인터넷 연결

## 사용 API

### 포켓몬 도감 API

```txt
https://pokeapi.co/api/v2/pokemon/{id 또는 name}
```

예:

```txt
https://pokeapi.co/api/v2/pokemon/pikachu
https://pokeapi.co/api/v2/pokemon/25
```

### 카드 API

새 덱 생성:

```txt
https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1
```

카드 뽑기:

```txt
https://deckofcardsapi.com/api/deck/{deck_id}/draw/?count=1
```

---

# 3. REST API란?

REST API는 프로그램이 인터넷을 통해 서버의 데이터를 요청하고 응답받는 방식이다.

웹 브라우저가 웹페이지를 요청하듯이, Java 프로그램도 특정 URL에 요청을 보내고 데이터를 받을 수 있다.

예를 들어 다음 주소를 브라우저에 입력하면 포켓몬 피카츄의 정보를 JSON 형식으로 받을 수 있다.

```txt
https://pokeapi.co/api/v2/pokemon/pikachu
```

---

## 3.1 HTTP 요청과 응답

REST API에서 자주 사용하는 HTTP 메서드는 다음과 같다.

| 메서드 | 의미 | 예시 |
|---|---|---|
| GET | 데이터 조회 | 포켓몬 정보 가져오기 |
| POST | 데이터 생성 | 게시글 작성 |
| PUT | 데이터 전체 수정 | 회원 정보 수정 |
| PATCH | 데이터 일부 수정 | 닉네임만 수정 |
| DELETE | 데이터 삭제 | 게시글 삭제 |

이번 수업에서는 가장 기본적인 **GET 요청**만 사용한다.

---

## 3.2 JSON이란?

JSON은 서버와 클라이언트가 데이터를 주고받을 때 자주 사용하는 텍스트 형식이다.

예:

```json
{
  "name": "pikachu",
  "height": 4,
  "weight": 60
}
```

Java에서는 이 JSON 문자열을 파싱해서 `name`, `height`, `weight` 같은 값을 꺼낼 수 있다.

---

# 4. Maven 프로젝트 만들기

## 프로젝트 구조

```txt
java-rest-api-class/
├─ pom.xml
└─ src/
   └─ main/
      └─ java/
         └─ com/
            └─ example/
               └─ Main.java
```

---

## pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>java-rest-api-class</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>com.example.Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

# 5. 최종 예제 전체 소스

아래 코드는 포켓몬 도감과 카드 뽑기 게임을 하나의 Java 콘솔 프로그램으로 구현한 예제이다.

## Main.java

```java
package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> searchPokemon();
                case "2" -> randomPokemon();
                case "3" -> startHighLowCardGame();
                case "0" -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("잘못된 메뉴입니다.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("==============================");
        System.out.println(" Java REST API 예제 수업");
        System.out.println("==============================");
        System.out.println("1. 포켓몬 검색");
        System.out.println("2. 랜덤 포켓몬 보기");
        System.out.println("3. High & Low 카드 게임");
        System.out.println("0. 종료");
        System.out.print("메뉴 선택: ");
    }

    // ------------------------------------------------------------
    // 1. 포켓몬 도감 예제
    // ------------------------------------------------------------

    private static void searchPokemon() {
        System.out.print("포켓몬 이름 또는 번호 입력: ");
        String keyword = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

        if (keyword.isBlank()) {
            System.out.println("검색어를 입력해야 합니다.");
            return;
        }

        String url = "https://pokeapi.co/api/v2/pokemon/" + keyword;

        try {
            JsonObject pokemon = getJsonObject(url);
            printPokemonInfo(pokemon);
        } catch (IOException | InterruptedException e) {
            System.out.println("포켓몬 정보를 가져오지 못했습니다.");
            System.out.println("원인: " + e.getMessage());
        }
    }

    private static void randomPokemon() {
        int randomId = (int) (Math.random() * 151) + 1;
        String url = "https://pokeapi.co/api/v2/pokemon/" + randomId;

        try {
            JsonObject pokemon = getJsonObject(url);
            printPokemonInfo(pokemon);
        } catch (IOException | InterruptedException e) {
            System.out.println("랜덤 포켓몬 정보를 가져오지 못했습니다.");
            System.out.println("원인: " + e.getMessage());
        }
    }

    private static void printPokemonInfo(JsonObject pokemon) {
        int id = pokemon.get("id").getAsInt();
        String name = pokemon.get("name").getAsString();
        double heightMeter = pokemon.get("height").getAsInt() / 10.0;
        double weightKg = pokemon.get("weight").getAsInt() / 10.0;

        String imageUrl = pokemon
                .getAsJsonObject("sprites")
                .get("front_default")
                .getAsString();

        System.out.println();
        System.out.println("===== 포켓몬 정보 =====");
        System.out.println("번호: " + id);
        System.out.println("이름: " + name);
        System.out.println("키: " + heightMeter + "m");
        System.out.println("몸무게: " + weightKg + "kg");
        System.out.println("이미지 URL: " + imageUrl);

        System.out.print("타입: ");
        JsonArray types = pokemon.getAsJsonArray("types");
        for (int i = 0; i < types.size(); i++) {
            JsonObject typeInfo = types.get(i).getAsJsonObject();
            String typeName = typeInfo
                    .getAsJsonObject("type")
                    .get("name")
                    .getAsString();

            System.out.print(typeName);
            if (i < types.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        System.out.println("능력치:");
        JsonArray stats = pokemon.getAsJsonArray("stats");
        for (JsonElement element : stats) {
            JsonObject statInfo = element.getAsJsonObject();
            int value = statInfo.get("base_stat").getAsInt();
            String statName = statInfo
                    .getAsJsonObject("stat")
                    .get("name")
                    .getAsString();

            System.out.println("- " + statName + ": " + value);
        }
    }

    // ------------------------------------------------------------
    // 2. 카드 뽑기 게임 예제
    // ------------------------------------------------------------

    private static void startHighLowCardGame() {
        System.out.println();
        System.out.println("===== High & Low 카드 게임 =====");
        System.out.println("다음 카드가 현재 카드보다 높을지 낮을지 맞히는 게임입니다.");

        try {
            String deckId = createNewDeck();
            Card currentCard = drawOneCard(deckId);
            int score = 0;

            while (true) {
                System.out.println();
                System.out.println("현재 카드: " + currentCard.displayName());
                System.out.println("현재 점수: " + score);
                System.out.print("다음 카드는 더 높을까요? 낮을까요? (h/l/q): ");

                String choice = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

                if (choice.equals("q")) {
                    System.out.println("카드 게임을 종료합니다.");
                    return;
                }

                if (!choice.equals("h") && !choice.equals("l")) {
                    System.out.println("h, l, q 중 하나를 입력하세요.");
                    continue;
                }

                Card nextCard = drawOneCard(deckId);
                System.out.println("다음 카드: " + nextCard.displayName());

                int currentValue = convertCardValue(currentCard.value());
                int nextValue = convertCardValue(nextCard.value());

                if (nextValue == currentValue) {
                    System.out.println("같은 숫자입니다. 점수 변화 없이 계속합니다.");
                    currentCard = nextCard;
                    continue;
                }

                boolean isCorrect = choice.equals("h") && nextValue > currentValue
                        || choice.equals("l") && nextValue < currentValue;

                if (isCorrect) {
                    score++;
                    System.out.println("정답입니다! 점수 +1");
                    currentCard = nextCard;
                } else {
                    System.out.println("틀렸습니다. 게임 오버!");
                    System.out.println("최종 점수: " + score);
                    return;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("카드 API를 사용할 수 없습니다.");
            System.out.println("원인: " + e.getMessage());
        }
    }

    private static String createNewDeck() throws IOException, InterruptedException {
        String url = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";
        JsonObject result = getJsonObject(url);
        return result.get("deck_id").getAsString();
    }

    private static Card drawOneCard(String deckId) throws IOException, InterruptedException {
        String url = "https://deckofcardsapi.com/api/deck/" + deckId + "/draw/?count=1";
        JsonObject result = getJsonObject(url);

        boolean success = result.get("success").getAsBoolean();
        if (!success) {
            throw new IOException("카드를 뽑을 수 없습니다.");
        }

        JsonArray cards = result.getAsJsonArray("cards");
        if (cards.isEmpty()) {
            throw new IOException("남은 카드가 없습니다.");
        }

        JsonObject card = cards.get(0).getAsJsonObject();

        String value = card.get("value").getAsString();
        String suit = card.get("suit").getAsString();
        String code = card.get("code").getAsString();
        String image = card.get("image").getAsString();

        return new Card(value, suit, code, image);
    }

    private static int convertCardValue(String value) {
        return switch (value) {
            case "ACE" -> 14;
            case "KING" -> 13;
            case "QUEEN" -> 12;
            case "JACK" -> 11;
            default -> Integer.parseInt(value);
        };
    }

    private record Card(String value, String suit, String code, String image) {
        public String displayName() {
            return value + " of " + suit + " [" + code + "]";
        }
    }

    // ------------------------------------------------------------
    // 3. 공통 HTTP GET 함수
    // ------------------------------------------------------------

    private static JsonObject getJsonObject(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IOException("HTTP 오류 코드: " + response.statusCode());
        }

        return JsonParser.parseString(response.body()).getAsJsonObject();
    }
}
```

---

# 6. 코드 설명

## 6.1 `HttpClient`

```java
private static final HttpClient httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build();
```

Java에서 HTTP 요청을 보내기 위한 객체이다.

`connectTimeout`은 서버 연결에 너무 오래 걸릴 경우 요청을 중단하기 위한 설정이다.

---

## 6.2 GET 요청 만들기

```java
HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(Duration.ofSeconds(10))
        .GET()
        .build();
```

이 코드는 지정한 URL에 GET 요청을 보낸다는 의미이다.

---

## 6.3 응답 받기

```java
HttpResponse<String> response = httpClient.send(
        request,
        HttpResponse.BodyHandlers.ofString()
);
```

서버에서 받은 응답을 문자열로 받는다.

API 서버는 보통 JSON 문자열을 돌려준다.

---

## 6.4 HTTP 상태 코드 확인

```java
if (response.statusCode() != 200) {
    throw new IOException("HTTP 오류 코드: " + response.statusCode());
}
```

상태 코드 200은 요청이 정상 처리되었다는 뜻이다.

예를 들어 없는 포켓몬 이름을 검색하면 404 오류가 발생할 수 있다.

---

## 6.5 JSON 파싱

```java
return JsonParser.parseString(response.body()).getAsJsonObject();
```

서버에서 받은 JSON 문자열을 Java에서 다룰 수 있는 `JsonObject` 형태로 바꾼다.

---

# 7. 포켓몬 도감 코드 설명

## 7.1 포켓몬 검색 URL 만들기

```java
String url = "https://pokeapi.co/api/v2/pokemon/" + keyword;
```

사용자가 `pikachu`라고 입력하면 다음 주소로 요청을 보낸다.

```txt
https://pokeapi.co/api/v2/pokemon/pikachu
```

---

## 7.2 기본 정보 꺼내기

```java
int id = pokemon.get("id").getAsInt();
String name = pokemon.get("name").getAsString();
double heightMeter = pokemon.get("height").getAsInt() / 10.0;
double weightKg = pokemon.get("weight").getAsInt() / 10.0;
```

포켓몬 API에서 `height`는 데시미터 단위이고, `weight`는 헥토그램 단위이다.

그래서 각각 10으로 나누어 m, kg 단위로 변환한다.

---

## 7.3 중첩 JSON 접근

```java
String imageUrl = pokemon
        .getAsJsonObject("sprites")
        .get("front_default")
        .getAsString();
```

JSON 내부에 또 다른 JSON 객체가 들어 있는 구조를 중첩 JSON이라고 한다.

---

## 7.4 배열 JSON 접근

```java
JsonArray types = pokemon.getAsJsonArray("types");
```

포켓몬은 타입을 여러 개 가질 수 있다.

예를 들어 이상해씨는 `grass`, `poison` 두 가지 타입을 가진다.

---

# 8. 카드 게임 코드 설명

## 8.1 새 덱 생성

```java
String url = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";
JsonObject result = getJsonObject(url);
return result.get("deck_id").getAsString();
```

카드 API에서 새 카드 덱을 만들면 `deck_id`가 반환된다.

이 `deck_id`는 게임이 진행되는 동안 계속 사용해야 한다.

---

## 8.2 카드 한 장 뽑기

```java
String url = "https://deckofcardsapi.com/api/deck/" + deckId + "/draw/?count=1";
```

기존에 만든 덱에서 카드 한 장을 뽑는다.

---

## 8.3 카드 숫자 변환

```java
private static int convertCardValue(String value) {
    return switch (value) {
        case "ACE" -> 14;
        case "KING" -> 13;
        case "QUEEN" -> 12;
        case "JACK" -> 11;
        default -> Integer.parseInt(value);
    };
}
```

카드 API는 숫자 카드의 값은 `2`, `3`, `10`처럼 문자열로 주고, 그림 카드는 `JACK`, `QUEEN`, `KING`, `ACE`처럼 문자열로 준다.

게임에서 크기를 비교하려면 숫자로 바꾸어야 한다.

---

# 9. 수업 중 질문 예시

## 질문 1

다음 코드는 어떤 역할을 하는가?

```java
HttpResponse<String> response = httpClient.send(
        request,
        HttpResponse.BodyHandlers.ofString()
);
```

정답 예시:

서버에 HTTP 요청을 보내고, 응답 본문을 문자열로 받는다.

---

## 질문 2

다음 주소에서 `pikachu`는 어떤 역할을 하는가?

```txt
https://pokeapi.co/api/v2/pokemon/pikachu
```

정답 예시:

조회할 포켓몬의 이름이다. 이 부분을 `charmander`, `bulbasaur`, `25` 등으로 바꾸면 다른 포켓몬 정보를 가져올 수 있다.

---

## 질문 3

카드 API에서 `deck_id`를 저장해야 하는 이유는 무엇인가?

정답 예시:

같은 카드 덱에서 계속 카드를 뽑기 위해서이다. 매번 새 덱을 만들면 게임 상태가 유지되지 않는다.

---

# 10. 1시간 퀴즈/실습 문제

아래 문제는 총 60분 분량으로 구성한다.

---

## 문제 1. REST API 기본 개념 문제

다음 중 REST API에서 데이터를 조회할 때 주로 사용하는 HTTP 메서드는 무엇인가?

1. POST
2. GET
3. DELETE
4. PATCH

정답: 2

---

## 문제 2. JSON 값 읽기

다음 JSON이 있다.

```json
{
  "name": "pikachu",
  "height": 4,
  "weight": 60
}
```

`name` 값을 Java Gson 코드로 꺼내는 문장을 작성하시오.

정답 예시:

```java
String name = jsonObject.get("name").getAsString();
```

---

## 문제 3. 포켓몬 키 변환

포켓몬 API에서 `height` 값이 4로 들어왔다.

이 값은 데시미터 단위이다. 미터 단위로 출력하려면 Java 코드로 어떻게 작성해야 하는가?

정답 예시:

```java
double heightMeter = pokemon.get("height").getAsInt() / 10.0;
```

---

## 문제 4. 오류 찾기

다음 코드의 문제점을 설명하시오.

```java
String name = pokemon.get("pokemon_name").getAsString();
```

정답 예시:

PokeAPI 응답에는 `pokemon_name`이라는 필드가 없다. 포켓몬 이름은 `name` 필드에 들어 있다.

수정 코드:

```java
String name = pokemon.get("name").getAsString();
```

---

## 문제 5. 카드 숫자 변환

카드 값이 `KING`이면 13, `QUEEN`이면 12, `JACK`이면 11, `ACE`이면 14로 변환하는 이유를 설명하시오.

정답 예시:

High & Low 게임에서 카드의 크기를 비교해야 하는데, 문자열 그대로는 숫자 비교를 하기 어렵기 때문이다.

---

## 문제 6. 기능 추가 실습

포켓몬 도감에서 포켓몬의 능력치 중 `speed`만 따로 출력하도록 코드를 추가하시오.

힌트:

```java
JsonArray stats = pokemon.getAsJsonArray("stats");
```

정답 예시:

```java
JsonArray stats = pokemon.getAsJsonArray("stats");
for (JsonElement element : stats) {
    JsonObject statInfo = element.getAsJsonObject();
    String statName = statInfo
            .getAsJsonObject("stat")
            .get("name")
            .getAsString();

    if (statName.equals("speed")) {
        int speed = statInfo.get("base_stat").getAsInt();
        System.out.println("스피드: " + speed);
    }
}
```

---

## 문제 7. 코드 빈칸 채우기

아래 코드의 빈칸을 채우시오.

```java
HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .timeout(Duration.ofSeconds(10))
        .____()
        .build();
```

정답:

```java
GET
```

---

## 문제 8. 카드 게임 기능 추가

High & Low 카드 게임에서 현재 카드와 다음 카드가 같으면 어떻게 처리하는 것이 좋을지 설명하시오.

정답 예시:

승패를 결정하지 않고 점수 변화 없이 다음 라운드로 넘기는 방식이 자연스럽다.

---

## 문제 9. 실습 확장

랜덤 포켓몬 보기 기능은 현재 1번부터 151번까지만 나온다.

이를 1번부터 300번까지 나오도록 수정하시오.

수정 전:

```java
int randomId = (int) (Math.random() * 151) + 1;
```

수정 후:

```java
int randomId = (int) (Math.random() * 300) + 1;
```

---

## 문제 10. 서술형 문제

REST API를 사용하는 프로그램에서 예외 처리가 중요한 이유를 설명하시오.

정답 예시:

인터넷 연결이 끊기거나, 서버가 응답하지 않거나, 잘못된 URL을 요청할 수 있기 때문이다. 예외 처리를 하지 않으면 프로그램이 갑자기 종료될 수 있다.

---

# 11. 과제 제안

## 과제 1. 포켓몬 비교 프로그램

포켓몬 이름 두 개를 입력받고, 두 포켓몬의 공격력과 방어력을 비교하시오.

예:

```txt
첫 번째 포켓몬: pikachu
두 번째 포켓몬: charizard

공격력 비교:
pikachu: 55
charizard: 84
charizard의 공격력이 더 높습니다.
```

---

## 과제 2. 카드 5장 뽑기

카드 API에서 한 번에 5장의 카드를 뽑고, 가장 높은 카드를 출력하시오.

예:

```txt
뽑은 카드:
3 of HEARTS
KING of CLUBS
10 of SPADES
ACE of DIAMONDS
7 of HEARTS

가장 높은 카드: ACE of DIAMONDS
```

---

## 과제 3. 간단한 카드 승부 게임

컴퓨터와 사용자가 각각 카드 한 장씩 뽑는다.

더 높은 카드를 뽑은 사람이 승리한다.

```txt
사용자 카드: QUEEN of HEARTS
컴퓨터 카드: 8 of CLUBS
사용자 승리!
```

---

# 12. 수업 운영 팁

## 초반 20분

REST API를 너무 이론적으로 길게 설명하지 않는다.

브라우저에 직접 다음 URL을 입력해서 JSON이 나오는 것을 먼저 보여주는 것이 좋다.

```txt
https://pokeapi.co/api/v2/pokemon/pikachu
```

학생들은 브라우저에서 JSON이 바로 나오는 것을 보면 API의 개념을 빠르게 이해한다.

---

## 포켓몬 도감 파트

처음부터 모든 JSON을 설명하지 말고 다음 순서로 진행한다.

1. `name` 출력
2. `height`, `weight` 출력
3. `sprites.front_default` 출력
4. `types` 배열 출력
5. `stats` 배열 출력

---

## 카드 게임 파트

카드 API는 `deck_id` 개념이 핵심이다.

다음 질문을 학생들에게 던지면 좋다.

```txt
왜 카드를 뽑을 때마다 새 덱을 만들면 안 될까?
```

이 질문을 통해 상태 관리 개념을 자연스럽게 설명할 수 있다.

---

# 13. 교수자용 핵심 정리

이번 수업의 핵심은 다음 세 가지이다.

1. REST API는 URL로 서버의 데이터를 요청하는 방식이다.
2. 서버는 JSON 형태로 데이터를 돌려주는 경우가 많다.
3. Java에서는 `HttpClient`로 요청하고, Gson으로 JSON을 파싱할 수 있다.

포켓몬 도감은 검색형 API 예제이고, 카드 게임은 상태 관리가 필요한 API 예제이다.

두 예제를 연결하면 REST API의 기본 흐름과 실제 활용 방식을 자연스럽게 설명할 수 있다.

