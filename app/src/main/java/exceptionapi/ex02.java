package exceptionapi;

public class ex02 {
    static class InvalidAgeException extends RuntimeException {
        InvalidAgeException(String message) {
            super(message);
        }
    }

    static void checkAge(int age) {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("invalid age: " + age);
        }
    }

    public static void main(String[] args) {
        try {
            checkAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }
    }
}
