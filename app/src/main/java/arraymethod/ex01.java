package arraymethod;

import java.util.Arrays;

public class ex01 {
    public static void main(String[] args) {
        int[] scores = {90, 85, 95};

        System.out.println("array object: " + scores);
        System.out.println("array values: " + Arrays.toString(scores));

        for (int score : scores) {
            System.out.print(score + " ");
        }
        System.out.println();
    }
}
