package chapter08;

import java.util.Scanner;

/**
 * 표준 입출력: 키보드(System.in)에서 읽고 화면(System.out)에 쓰는 가장 기본 흐름.
 * 파일 입출력(Ex02 이후)으로 가기 전, 같은 모양의 입력/출력 흐름을 콘솔로 먼저 체험한다.
 *
 * 핵심 API:
 *   - Scanner: 표준 입력 스트림에서 한 줄/정수/실수 등을 편하게 읽는 도우미
 *   - System.out.printf: C의 printf 같은 서식 출력
 */
public class Ex01 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // 1) 문자열 한 줄 읽기
            System.out.print("이름을 입력하세요: ");
            String name = scanner.nextLine();

            // 2) 정수 한 개 읽기
            System.out.print("나이를 입력하세요: ");
            int age = scanner.nextInt();

            // 3) 서식 출력
            //    %s 문자열, %d 정수, %.2f 소수 둘째 자리, %5d 5자리 오른쪽 정렬, %n 줄바꿈
            System.out.println();
            System.out.printf("입력 결과 -> 이름: %s, 나이: %d세%n", name, age);
            System.out.printf("내년 나이: %d%n", age + 1);
            System.out.printf("이름 길이: %d 자%n", name.length());
            System.out.printf("두 자리 정렬 예시: [%5d]%n", age);
            System.out.printf("소수 서식 예시: 평균 = %.2f%n", (age + 1) / 2.0);
        }
    }
}
