package chapter04.ex03;

public class Ex03 {
    public static void main(String[] args) {
        Hero hero = new Hero();
        Monster monster = new Monster();

        hero.move();
        hero.attack();
        monster.move();
        monster.attack();
    }
}
