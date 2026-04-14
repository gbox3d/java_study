package chapter06.ex01;

public class GameCharacter {
    private final String id;
    private final String name;
    private final String role;
    private int level;
    private int hp;

    public GameCharacter(String id, String name, String role, int level, int hp) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.level = level;
        this.hp = hp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        level++;
        hp += 20;
    }

    @Override
    public String toString() {
        return "GameCharacter{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", level=" + level +
                ", hp=" + hp +
                '}';
    }
}
