package startup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("Current time: " + now.format(formatter));
    }
}
