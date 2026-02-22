package exceptionapi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ex03 {
    static boolean isValidDate(String input) {
        try {
            LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String input1 = "2026-02-22";
        String input2 = "2026-02-30";

        System.out.println(input1 + " valid? " + isValidDate(input1));
        System.out.println(input2 + " valid? " + isValidDate(input2));
    }
}
