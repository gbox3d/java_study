package chapter04.ex02;

public class Ex02 {
    public static void main(String[] args) {
        // Enemy e = new Enemy("몬스터", 100); 
        // -> 에러 발생 (추상 클래스는 직접 인스턴스화 불가능)
        
        System.out.println("--- 몬스터 출현 ---");
        
        // 다형성: 추상 클래스 배열에 자식 객체 할당
        Enemy[] enemies = {
            new Slime("초록 슬라임", 30),
            new Dragon("레드 드래곤", 1000, 20)
        };

        // 몬스터들의 턴 & 용사들의 공격 시뮬레이션
        for (Enemy enemy : enemies) {
            enemy.showStatus();
            enemy.attack();
            
            System.out.println("-> 용사 파티가 30의 피해로 반격했습니다!");
            enemy.takeDamage(30); // 슬라임은 30피해, 드래곤은 방어력으로 경감됨
            
            enemy.showStatus();
            System.out.println();
        }
    }
}
