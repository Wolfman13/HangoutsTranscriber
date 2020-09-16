import java.nio.file.Files;
import java.nio.file.Path;

class Transcriber {
    private static String filepath;

    public Transcriber() {

    }

    private String readFile(String filepath) {
        try {
            return Files.readString(Path.of(filepath));
        } catch (Exception ex) {
            System.err.println("Failed to open file: " + filepath);
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        new Transcriber();
    }
}
