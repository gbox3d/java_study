package chapter05;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Ex04 {
    static void printDateCalculation(LocalDate date) {
        LocalDate after10Days = date.plusDays(10);
        LocalDate after1Month = date.plusMonths(1);
        long daysBetween = ChronoUnit.DAYS.between(date, after1Month);

        System.out.println("base date: " + date);
        System.out.println("after 10 days: " + after10Days);
        System.out.println("after 1 month: " + after1Month);
        System.out.println("days between base and after 1 month: " + daysBetween);
    }

    public static void main(String[] args) {
        LocalDate today = LocalDate.of(2026, 2, 22);
        LocalDate eventDate = LocalDate.of(2026, 3, 15);
        long daysUntilEvent = ChronoUnit.DAYS.between(today, eventDate);

        printDateCalculation(today);
        System.out.println("days until event: " + daysUntilEvent);
    }
}