package oop2;

public class ex02 {
    static class Animal {
        void sound() {
            System.out.println("...");
        }
    }

    static class Dog extends Animal {
        @Override
        void sound() {
            System.out.println("woof");
        }
    }

    static class Cat extends Animal {
        @Override
        void sound() {
            System.out.println("meow");
        }
    }

    public static void main(String[] args) {
        Animal[] animals = {new Dog(), new Cat()};
        for (Animal animal : animals) {
            animal.sound();
        }
    }
}
