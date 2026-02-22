package basic;

public class ex01 {
    public static void main(String[] args) {
        System.out.println("Hello, Java World!");

        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "]: " + args[i]);
        }
    }
}
