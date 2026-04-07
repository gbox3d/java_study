package chapter04.ex01;

public class Ex01 {
    public static void main(String[] args) {
        // 다형성: 부모 타입(Character) 배열에 다양한 자식 객체 할당
        Character[] party = {
            new Warrior("타락한 전사", 1000, 50),
            new Mage("대마법사 안토니다스", 500, 300),
            new Archer("엘프 궁수 실바나스", 700, 30)
        };
        
        System.out.println("--- 파티 전투 시작 ---");
        for (Character member : party) {
            // 동일한 메서드를 호출하지만 자식 객체에서 오버라이딩된 각자의 공격이 나갑니다
            member.attack(); 
        }
    }
}
