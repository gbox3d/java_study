package arraymethod;

public class ex02 {
    static int sum(int... numbers) {
        int result = 0;
        for (int n : numbers) {
            result += n;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("sum(1, 2, 3, 4, 5) = " + sum(1, 2, 3, 4, 5));
    }
}
