package fileio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class ex03 {
    static class User implements Serializable {
        private static final long serialVersionUID = 1L;

        String id;
        transient String password;

        User(String id, String password) {
            this.id = id;
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{id='" + id + "', password='" + password + "'}";
        }
    }

    public static void main(String[] args) {
        Path file = Path.of("app", "build", "tmp", "user.dat");

        try {
            Files.createDirectories(file.getParent());

            User saved = new User("alice", "secret123");
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
                out.writeObject(saved);
            }

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.toFile()))) {
                User loaded = (User) in.readObject();
                System.out.println("saved:  " + saved);
                System.out.println("loaded: " + loaded);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
