package collection;

import java.util.ArrayList;
import java.util.List;

public class ex01 {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");

        names.remove("Bob");

        System.out.println("contains Alice: " + names.contains("Alice"));
        System.out.println("list: " + names);
    }
}
