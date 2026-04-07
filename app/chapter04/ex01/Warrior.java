package chapter04.ex01;

public class Warrior extends Character {
    private int rage; // 전사 전용 자원: 분노 게이지

    public Warrior(String name, int hp, int rage) {
        super(name, hp);  // 부모(Character) 생성자 호출
        this.rage = rage;
    }

    @Override
    public void attack() {
        System.out.println(name + "이(가) 분노(" + rage + ")를 담아 강력한 휠윈드 공격을 합니다!");
    }
}
