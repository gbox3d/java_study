package basic;

import java.util.Locale;

public class ex02 {
    public static void main(String[] args) {
        int id = 2026001;
        double gpa = 4.18;

        System.out.printf(
                Locale.US,
                "id: %d, gpa: %.2f (cast to int: %d)%n",
                id,
                gpa,
                (int) gpa
        );
    }
}
