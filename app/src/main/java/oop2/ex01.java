package oop2;

public class ex01 {
    static class Employee {
        protected String name;

        Employee(String name) {
            this.name = name;
        }

        void work() {
            System.out.println(name + " works");
        }
    }

    static class Manager extends Employee {
        Manager(String name) {
            super(name);
        }

        @Override
        void work() {
            System.out.println(name + " manages team");
        }
    }

    public static void main(String[] args) {
        Employee manager = new Manager("Chris");
        manager.work();
    }
}
