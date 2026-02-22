package collection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ex03 {
    static class Student {
        int id;
        String name;
        int score;

        Student(int id, String name, int score) {
            this.id = id;
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Student{id=" + id + ", name='" + name + "', score=" + score + "}";
        }
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(3, "Kim", 90));
        students.add(new Student(1, "Lee", 95));
        students.add(new Student(2, "Park", 95));

        students.sort(
                Comparator.comparingInt((Student s) -> s.score).reversed()
                        .thenComparingInt(s -> s.id)
        );

        students.forEach(System.out::println);
    }
}
