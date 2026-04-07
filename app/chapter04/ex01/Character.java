package chapter04.ex01;

public class Character {
    protected String name;  // protected: 자식 클래스에서 접근 가능
    protected int hp;       // 체력

    public Character(String name, int hp) {
        this.name = name;
        this.hp = hp;
    }

    public void attack() {
        System.out.println(name + "이(가) 기본 공격을 합니다.");
    }
}
