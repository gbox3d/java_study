package oop2;

public class ex03 {
    interface Movable {
        void move();
    }

    interface Attackable {
        void attack();
    }

    static class Hero implements Movable, Attackable {
        @Override
        public void move() {
            System.out.println("Hero moves fast");
        }

        @Override
        public void attack() {
            System.out.println("Hero attacks");
        }
    }

    static class Monster implements Movable, Attackable {
        @Override
        public void move() {
            System.out.println("Monster moves slowly");
        }

        @Override
        public void attack() {
            System.out.println("Monster attacks");
        }
    }

    public static void main(String[] args) {
        Hero hero = new Hero();
        Monster monster = new Monster();

        hero.move();
        hero.attack();
        monster.move();
        monster.attack();
    }
}
