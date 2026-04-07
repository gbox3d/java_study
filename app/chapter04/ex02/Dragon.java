package chapter04.ex02;

public class Dragon extends Enemy {
    private int armor; // 드래곤 전용 속성: 단단한 비늘 방어력

    public Dragon(String name, int hp, int armor) {
        super(name, hp);
        this.armor = armor;
    }

    @Override
    public void attack() {
        System.out.println(name + "이(가) 하늘로 솟아올라 거대한 화염 브레스를 내뿜습니다!! (콰아아아)");
    }

    @Override
    public void takeDamage(int damage) {
        // 방어력만큼 피해를 감소시켜 받음 (최소 0)
        int actualDamage = Math.max(0, damage - armor);
        hp -= actualDamage;
        System.out.println(name + "의 단단한 비늘이 공격을 튕겨냅니다! (실제 피해: " + actualDamage + ")");
    }
}
