package chapter04.ex03;

public class Hero implements Movable, Attackable {
    @Override
    public void move() {
        System.out.println("용사가 빠르게 이동합니다");
    }

    @Override
    public void attack() {
        System.out.println("용사가 공격합니다");
    }
}
