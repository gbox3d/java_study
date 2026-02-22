package fileio;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ex01 {
    public static void main(String[] args) {
        Path file = Path.of("app", "build", "tmp", "week08_ex01.txt");

        try {
            Files.createDirectories(file.getParent());
            try (FileWriter writer = new FileWriter(file.toFile())) {
                writer.write("Hello, I/O!");
            }

            try (FileReader reader = new FileReader(file.toFile())) {
                int ch;
                StringBuilder sb = new StringBuilder();
                while ((ch = reader.read()) != -1) {
                    sb.append((char) ch);
                }
                System.out.println(sb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
