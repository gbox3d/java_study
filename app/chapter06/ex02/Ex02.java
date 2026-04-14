package chapter06.ex02;

public class Ex02 {
    public static void main(String[] args) {
        createAndPutExample();
        readExample();
        updateExample();
        removeAndSearchExample();
        loopExample();
        extraExample();
    }

    static void createAndPutExample() {
        printTitle("1. 생성과 등록");

        ItemManager itemManager = new ItemManager();
        itemManager.addItem(new ItemSpec("sword_001", "초보자 검", "Common", 100, 5, true));
        itemManager.addItem(new ItemSpec("staff_001", "견습 마법봉", "Rare", 300, 8, true));
        itemManager.addItem(new ItemSpec("armor_001", "가죽 갑옷", "Common", 250, 0, true));

        System.out.println("등록된 아이템 수: " + itemManager.size());
        itemManager.printAllItems();
    }

    static void readExample() {
        printTitle("2. 조회");

        ItemManager itemManager = createSampleManager();

        System.out.println("staff_001 조회: " + itemManager.getItem("staff_001"));
        System.out.println("현재 데이터 비어 있음: " + itemManager.isEmpty());
    }

    static void updateExample() {
        printTitle("3. 수정");

        ItemManager itemManager = createSampleManager();

        System.out.println("수정 전: " + itemManager.getItem("sword_001"));
        itemManager.updatePrice("sword_001", 150);
        itemManager.stopTrading("sword_001");
        System.out.println("수정 후: " + itemManager.getItem("sword_001"));
    }

    static void removeAndSearchExample() {
        printTitle("4. 삭제와 검색");

        ItemManager itemManager = createSampleManager();

        System.out.println("has armor_001: " + itemManager.hasItem("armor_001"));
        itemManager.removeItem("armor_001");
        System.out.println("remove 후 has armor_001: " + itemManager.hasItem("armor_001"));
        System.out.println("없는 코드 조회: " + itemManager.getItemOrDefault("bow_999"));
    }

    static void loopExample() {
        printTitle("5. 전체 출력");

        ItemManager itemManager = createSampleManager();
        itemManager.printAllItems();
    }

    static void extraExample() {
        printTitle("6. 실제 활용 관점");

        ItemManager itemManager = createSampleManager();

        String playerItemCode = "staff_001";
        ItemSpec equippedItem = itemManager.getItem(playerItemCode);

        System.out.println("플레이어 장착 아이템 코드: " + playerItemCode);
        System.out.println("장착 아이템 명세: " + equippedItem);
        System.out.println("-> 게임에서는 이런 식으로 코드로 아이템 데이터를 찾아 사용한다.");
    }

    static ItemManager createSampleManager() {
        ItemManager itemManager = new ItemManager();
        itemManager.addItem(new ItemSpec("sword_001", "초보자 검", "Common", 100, 5, true));
        itemManager.addItem(new ItemSpec("staff_001", "견습 마법봉", "Rare", 300, 8, true));
        itemManager.addItem(new ItemSpec("armor_001", "가죽 갑옷", "Common", 250, 0, true));
        return itemManager;
    }

    static void printTitle(String title) {
        System.out.println();
        System.out.println("[" + title + "]");
    }
}
