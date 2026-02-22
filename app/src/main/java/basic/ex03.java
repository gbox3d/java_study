package basic;

public class ex03 {
    public static void main(String[] args) {
        int height = 5;

        for (int i = 0; i < height; i++) {
            for (int s = 0; s < height - i - 1; s++) {
                System.out.print(" ");
            }
            for (int star = 0; star < i * 2 + 1; star++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
