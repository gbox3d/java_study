package exceptionapi;

public class ex01 {
    static int divide(int a, int b) {
        return a / b;
    }

    public static void main(String[] args) {
        try {
            System.out.println(divide(10, 0));
        } catch (ArithmeticException e) {
            System.out.println("error: " + e.getMessage());
        } finally {
            System.out.println("finally block executed");
        }
    }
}
