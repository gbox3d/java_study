package chapter05;

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
        int validAge = 25;
        int invalidAge = -5;

        try {
            checkAge(validAge);
            System.out.println("valid age: " + validAge);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }

        try {
            checkAge(invalidAge);
            System.out.println("valid age: " + invalidAge);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }
    }
}
