package modernjava;

import java.util.Arrays;
import java.util.List;

public class ex03 {
    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> result = values.stream()
                .filter(v -> v % 2 == 0)
                .map(v -> v * v)
                .toList();

        System.out.println(result);
    }
}
