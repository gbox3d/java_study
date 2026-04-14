package chapter06.ex01;

public class Ex01 {
    public static void main(String[] args) {
        createAndAddExample();
        readExample();
        insertAndRemoveExample();
        updateAndSearchExample();
        loopExample();
        sortAndClearExample();
    }

    static void createAndAddExample() {
        printTitle("1. 파티 생성과 영입");

        PartyManager partyManager = new PartyManager();
        partyManager.addCharacter(new GameCharacter("warrior_01", "브론", "전사", 12, 180));
        partyManager.addCharacter(new GameCharacter("mage_01", "세리아", "마법사", 18, 90));
        partyManager.addCharacter(new GameCharacter("archer_01", "카일", "궁수", 15, 110));

        System.out.println("현재 파티 인원: " + partyManager.size());
        partyManager.printParty();
    }

    static void readExample() {
        printTitle("2. 파티 조회");

        PartyManager partyManager = createSampleParty();

        System.out.println("0번 슬롯 캐릭터: " + partyManager.getCharacter(0));
        System.out.println("파티가 비어 있는가: " + partyManager.isEmpty());
    }

    static void insertAndRemoveExample() {
        printTitle("3. 중간 배치와 탈퇴");

        PartyManager partyManager = createSampleParty();

        partyManager.insertCharacter(1, new GameCharacter("priest_01", "리아", "사제", 14, 100));
        System.out.println("1번 슬롯에 리아 배치 후:");
        partyManager.printParty();

        partyManager.removeByIndex(2);
        System.out.println("2번 슬롯 캐릭터 제외 후:");
        partyManager.printParty();

        partyManager.removeByName("브론");
        System.out.println("브론 탈퇴 후:");
        partyManager.printParty();
    }

    static void updateAndSearchExample() {
        printTitle("4. 성장과 검색");

        PartyManager partyManager = createSampleParty();

        partyManager.levelUpCharacter("세리아");
        System.out.println("세리아 레벨업 후: " + partyManager.findByName("세리아"));
        System.out.println("카일이 파티에 있는가: " + partyManager.containsName("카일"));
        System.out.println("린이 파티에 있는가: " + partyManager.containsName("린"));
    }

    static void loopExample() {
        printTitle("5. 전체 순회");

        PartyManager partyManager = createSampleParty();
        partyManager.printParty();
    }

    static void sortAndClearExample() {
        printTitle("6. 정렬과 해산");

        PartyManager partyManager = createSampleParty();

        partyManager.sortByLevelDesc();
        System.out.println("레벨 높은 순 정렬:");
        partyManager.printParty();

        partyManager.reverseOrder();
        System.out.println("순서 뒤집기:");
        partyManager.printParty();

        partyManager.clear();
        System.out.println("해산 후 파티 인원: " + partyManager.size());
    }

    static PartyManager createSampleParty() {
        PartyManager partyManager = new PartyManager();
        partyManager.addCharacter(new GameCharacter("warrior_01", "브론", "전사", 12, 180));
        partyManager.addCharacter(new GameCharacter("mage_01", "세리아", "마법사", 18, 90));
        partyManager.addCharacter(new GameCharacter("archer_01", "카일", "궁수", 15, 110));
        return partyManager;
    }

    static void printTitle(String title) {
        System.out.println();
        System.out.println("[" + title + "]");
    }
}
