package oop1;

public class ex01 {
    static class Student {
        String name;
        int age;

        Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        void printInfo() {
            System.out.println("name=" + name + ", age=" + age);
        }
    }

    public static void main(String[] args) {
        Student student = new Student("Alice", 20);
        student.printInfo();
    }
}
