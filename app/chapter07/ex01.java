package chapter07;

public class Ex01 {
    static class Box<T> {
        private T value;

        void set(T value) {
            this.value = value;
        }

        T get() {
            return value;
        }
    }

    public static void main(String[] args) {
        Box<String> box = new Box<>();
        box.set("generic value");
        System.out.println(box.get());
    }
}
