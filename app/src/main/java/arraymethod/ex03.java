package arraymethod;

import java.util.Arrays;

public class ex03 {
    static void sortArray(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] data = {8, 3, 5, 1, 9};
        sortArray(data);
        System.out.println(Arrays.toString(data));
    }
}
