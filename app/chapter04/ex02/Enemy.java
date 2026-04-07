package chapter04.ex02;

// 추상 클래스 (Abstract Class): 자체적으로 객체를 생성할 수 없으며, 자식 클래스의 뼈대 역할을 함
public abstract class Enemy {
    protected String name;
    protected int hp;

    public Enemy(String name, int hp) {
        this.name = name;
        this.hp = hp;
    }

    // 일반 메서드: 모든 적들이 공통으로 사용하는 기능
    public void showStatus() {
        System.out.println("[" + name + "] 현재 체력: " + hp);
    }

    // 추상 메서드: 자식 클래스에서 "반드시" 구현(Override)해야 하는 기능
    public abstract void attack();
    
    public abstract void takeDamage(int damage);
}
