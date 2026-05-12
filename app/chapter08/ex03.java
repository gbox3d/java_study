package chapter08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ex03 {
    public static void main(String[] args) {
        Path file = Path.of("out", "files", "week08_ex03.txt"); // out/files/week08_ex03.txt

        try {
            Files.createDirectories(file.getParent()); // Ensure the parent directory exists
            try (BufferedWriter writer = Files.newBufferedWriter(file)) { // Try-with-resources to ensure the writer is closed
                writer.write("line1");
                writer.newLine();
                writer.write("line2");
            }

            try (BufferedReader reader = Files.newBufferedReader(file)) { // Try-with-resources to ensure the reader is closed
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
