package chapter04.ex01;

public class Archer extends Character {
    private int arrows; // 궁수 전용 자원: 화살 개수

    public Archer(String name, int hp, int arrows) {
        super(name, hp);
        this.arrows = arrows;
    }

    @Override
    public void attack() {
        System.out.println(name + "이(가) 남은 화살(" + arrows + "개) 중 하나를 발사하여 치명타를 입힙니다!");
    }
}
