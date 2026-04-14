package chapter06.ex02;

public class ItemSpec {
    private final String code;
    private final String name;
    private final String grade;
    private int price;
    private final int attackBonus;
    private boolean tradable;

    public ItemSpec(String code, String name, String grade, int price, int attackBonus, boolean tradable) {
        this.code = code;
        this.name = name;
        this.grade = grade;
        this.price = price;
        this.attackBonus = attackBonus;
        this.tradable = tradable;
    }

    public String getCode() {
        return code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTradable(boolean tradable) {
        this.tradable = tradable;
    }

    @Override
    public String toString() {
        return "ItemSpec{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", price=" + price +
                ", attackBonus=" + attackBonus +
                ", tradable=" + tradable +
                '}';
    }
}
