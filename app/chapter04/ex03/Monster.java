package chapter04.ex03;

public class Monster implements Movable, Attackable {
    @Override
    public void move() {
        System.out.println("몬스터가 느리게 이동합니다");
    }

    @Override
    public void attack() {
        System.out.println("몬스터가 공격합니다");
    }
}
