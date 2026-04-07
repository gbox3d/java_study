package chapter04.ex01;

public class Mage extends Character {
    private int mp; // 마법사 전용 자원: 마나

    public Mage(String name, int hp, int mp) {
        super(name, hp);
        this.mp = mp;
    }

    @Override
    public void attack() {
        System.out.println(name + "이(가) 마나(" + mp + ")를 소모하여 거대한 파이어볼을 시전합니다!");
    }
}
