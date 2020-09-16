import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

public class Transcriber {
    private final String filepath;
    private String contents;

    public Transcriber(String filepath, String outDir) {
        this.filepath = filepath;
        this.contents = null;

        readFile();

        if (contents != null) {
            jsonDeserializer(outDir);
        }
    }

    private void readFile() {
        try {
            this.contents = Files.readString(Path.of(filepath));
        } catch (Exception ex) {
            System.err.println("Failed to open file: " + filepath);
        }
    }

    private void jsonDeserializer(String outDir) {
        JSONObject mainObj = new JSONObject(this.contents);
        JSONArray conversations = mainObj.getJSONArray("conversations");

        System.out.println("Number of conversations: " + conversations.length());

        int counter = 0;

        for (var conversation : conversations) {
            new Conversations(conversation.toString(), counter, outDir);
            counter++;
        }
    }
}
