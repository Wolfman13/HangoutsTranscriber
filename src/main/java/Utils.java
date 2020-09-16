import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Utils {
    private final String path;
    private final String filename;

    public Utils(int conNumber, String outDir) {
        this.path = outDir;
        this.filename = "/Hangouts-Conversation-" + conNumber + ".txt";
    }

    public void appendToFile(String user, String message) {
        String outMessage = user + ": \t" + message + "\n\n";
        File file = new File(path + filename);

        try {
            if (file.exists()) {
                file.setWritable(true);

                BufferedWriter writer = new BufferedWriter(new FileWriter(path + filename, true));
                writer.write(outMessage);
                writer.close();
            } else {
                if (file.createNewFile()) {
                    System.out.println("Created file");

                    file.setWritable(true);

                    BufferedWriter writer = new BufferedWriter(new FileWriter(path + filename, true));
                    writer.write(outMessage);
                    writer.close();
                } else {
                    System.out.println("Failed to create file");
                }
            }

        } catch (Exception ex) {
            System.err.println("Failed to output to file: " + this.filename);
            ex.printStackTrace();
        }

    }
}
