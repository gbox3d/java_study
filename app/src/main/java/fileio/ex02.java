package fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ex02 {
    public static void main(String[] args) {
        Path file = Path.of("app", "build", "tmp", "week08_ex02.txt");

        try {
            Files.createDirectories(file.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(file)) {
                writer.write("line1");
                writer.newLine();
                writer.write("line2");
            }

            try (BufferedReader reader = Files.newBufferedReader(file)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("read: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
