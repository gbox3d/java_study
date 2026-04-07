package chapter04.ex02;

public class Slime extends Enemy {
    public Slime(String name, int hp) {
        super(name, hp);
    }

    // 부모(Enemy)의 추상 메서드를 강제로 오버라이딩해야 함
    @Override
    public void attack() {
        System.out.println(name + "이(가) 끈적한 점액질을 뿜으며 공격합니다!");
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        System.out.println(name + "이(가) 물컹거리며 " + damage + "의 피해를 그대로 입었습니다.");
    }
}
