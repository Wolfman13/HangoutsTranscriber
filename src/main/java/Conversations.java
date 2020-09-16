import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Scanner;

public class Conversations {
    private final JSONObject conversation;
    private final HashMap<Number, String> participants;
    private final Utils utils;

    public Conversations(String conversation, int conversationNumber, String outDir) {
        this.conversation = new JSONObject(conversation);
        participants = new HashMap<Number, String>();
        utils = new Utils(conversationNumber, outDir);

        setParticipants();

        // Get an array of all the messages (events) sent.
        JSONArray events = this.conversation.getJSONArray("events");
        for (var event : events) {
            getMessage(event.toString());
        }
    }

    private void setParticipants() {
        Scanner scanner = new Scanner(System.in);

        // I have started naming the variables depending on what level their "conversation"
        // can be found.
        JSONObject secondLvl = conversation.getJSONObject("conversation");
        JSONObject thirdLvl = secondLvl.getJSONObject("conversation");

        // Get an array of all the participants.
        JSONArray currentParticipants = thirdLvl.getJSONArray("current_participant");
        JSONArray participantsData = thirdLvl.getJSONArray("participant_data");

        for (int i = 0; i < currentParticipants.length(); i++) {
            // Get the gaiaID of the person (this is used to identify the person that
            // sent the message.
            JSONObject personID = new JSONObject(currentParticipants.get(i).toString());
            Number gaiaID = personID.getNumber("gaia_id");

            // Get the fallback name of the person, which will use in the text document.
            JSONObject personData = new JSONObject(participantsData.get(i).toString());
            String fallbackName = personData.getString("fallback_name");

            // Check whether or not the user wants to change the name before adding the user
            // to the hashmap.
            System.out.print(fallbackName + " ---> ");
            String username = scanner.nextLine();

            if (!username.equals("")) {
                fallbackName = username;
            }

            // Add the person to the hashmap.
            participants.put(gaiaID, fallbackName);
        }
    }

    private void getMessage(String event) {
        JSONObject eventObj = new JSONObject(event);

        // Get the name of the person that sent the message.
        JSONObject senderID = eventObj.getJSONObject("sender_id");
        String sender = participants.get(senderID.getNumber("gaia_id"));

        // Get the message.
        JSONObject chatMessage = eventObj.getJSONObject("chat_message");
        JSONObject messageContent = chatMessage.getJSONObject("message_content");
        try {
            JSONArray segments = messageContent.getJSONArray("segment");
            JSONObject message = new JSONObject(segments.get(0).toString());
            String text = message.getString("text");

            // Write to file.
            utils.appendToFile(sender, text);
        } catch (Exception e) {
            // Just ignore all the files with attachments.
        }

    }
}
