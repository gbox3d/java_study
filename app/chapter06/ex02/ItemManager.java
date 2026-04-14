package chapter06.ex02;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {
    private final Map<String, ItemSpec> itemTable = new HashMap<>();

    public void addItem(ItemSpec itemSpec) {
        itemTable.put(itemSpec.getCode(), itemSpec);
    }

    public ItemSpec getItem(String code) {
        return itemTable.get(code);
    }

    public ItemSpec getItemOrDefault(String code) {
        return itemTable.getOrDefault(code,
                new ItemSpec(code, "미등록 아이템", "None", 0, 0, false));
    }

    public boolean hasItem(String code) {
        return itemTable.containsKey(code);
    }

    public void updatePrice(String code, int newPrice) {
        ItemSpec itemSpec = itemTable.get(code);
        if (itemSpec != null) {
            itemSpec.setPrice(newPrice);
        }
    }

    public void stopTrading(String code) {
        ItemSpec itemSpec = itemTable.get(code);
        if (itemSpec != null) {
            itemSpec.setTradable(false);
        }
    }

    public void removeItem(String code) {
        itemTable.remove(code);
    }

    public int size() {
        return itemTable.size();
    }

    public boolean isEmpty() {
        return itemTable.isEmpty();
    }

    public void printAllItems() {
        for (Map.Entry<String, ItemSpec> entry : itemTable.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
