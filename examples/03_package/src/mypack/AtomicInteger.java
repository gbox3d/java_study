package mypack;

public class AtomicInteger {
    private int value;

    public AtomicInteger(int value) {
        this.value = value;
    }

    public void valueOf(int value) {
        this.value = value;
    }

    public String toString() {
        return Integer.toString(value);
    }

}
