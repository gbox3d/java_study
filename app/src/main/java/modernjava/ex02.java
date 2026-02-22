package modernjava;

import java.util.Arrays;
import java.util.List;

public class ex02 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Apple", "Banana", "Cherry");
        list.sort((a, b) -> b.compareTo(a));
        list.forEach(System.out::println);
    }
}
