package oop1;

public class ex02 {
    static class Student {
        String name;
        int age;

        Student() {
            this("Unknown", 20);
        }

        Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{name='" + name + "', age=" + age + "}";
        }
    }

    public static void main(String[] args) {
        System.out.println(new Student());
        System.out.println(new Student("Bob", 22));
    }
}
