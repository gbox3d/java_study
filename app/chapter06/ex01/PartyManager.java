package chapter06.ex01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PartyManager {
    private final List<GameCharacter> party = new ArrayList<>();

    public void addCharacter(GameCharacter character) {
        party.add(character);
    }

    public void insertCharacter(int index, GameCharacter character) {
        party.add(index, character);
    }

    public GameCharacter getCharacter(int index) {
        return party.get(index);
    }

    public GameCharacter findByName(String name) {
        for (GameCharacter character : party) {
            if (character.getName().equals(name)) {
                return character;
            }
        }
        return null;
    }

    public boolean containsName(String name) {
        return findByName(name) != null;
    }

    public void removeByIndex(int index) {
        party.remove(index);
    }

    public boolean removeByName(String name) {
        GameCharacter character = findByName(name);
        if (character == null) {
            return false;
        }
        return party.remove(character);
    }

    public void levelUpCharacter(String name) {
        GameCharacter character = findByName(name);
        if (character != null) {
            character.levelUp();
        }
    }

    public void sortByLevelDesc() {
        party.sort(Comparator.comparingInt(GameCharacter::getLevel).reversed());
    }

    public void reverseOrder() {
        Collections.reverse(party);
    }

    public int size() {
        return party.size();
    }

    public boolean isEmpty() {
        return party.isEmpty();
    }

    public void clear() {
        party.clear();
    }

    public void printParty() {
        for (int i = 0; i < party.size(); i++) {
            System.out.println(i + "번 슬롯 -> " + party.get(i));
        }
    }
}
