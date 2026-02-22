package basic;

public class ex04 {
    public static void main(String[] args) {
        // 1) if-else
        int score = 82;
        String grade;

        if (score >= 90) {
            grade = "A";
        } else if (score >= 80) {
            grade = "B";
        } else if (score >= 70) {
            grade = "C";
        } else {
            grade = "D";
        }
        System.out.println("if-else grade: " + grade);

        // 2) switch expression (Java 14+)
        int month = 2;
        String season = switch (month) {
            case 12, 1, 2 -> "winter";
            case 3, 4, 5 -> "spring";
            case 6, 7, 8 -> "summer";
            case 9, 10, 11 -> "autumn";
            default -> "invalid";
        };
        System.out.println("switch season: " + season);

        // 3) continue
        int oddSum = 0;
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue;
            }
            oddSum += i;
        }
        System.out.println("odd sum(1..10): " + oddSum);

        // 4) break vs labeled break comparison
        int plainBreakRow = -1;
        int plainBreakCol = -1;

        // break; -> inner loop(col)만 종료, outer loop(row)는 계속 진행
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                if (row * col >= 6) {
                    plainBreakRow = row;
                    plainBreakCol = col;
                    break;
                }
            }
        }
        System.out.println("plain break result: row=" + plainBreakRow + ", col=" + plainBreakCol);

        int labeledBreakRow = -1;
        int labeledBreakCol = -1;

        // break search; -> search 라벨이 붙은 outer loop(row)까지 종료
        search:
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                if (row * col >= 6) {
                    labeledBreakRow = row;
                    labeledBreakCol = col;
                    break search;
                }
            }
        }
        System.out.println("label break result: row=" + labeledBreakRow + ", col=" + labeledBreakCol);
    }
}
