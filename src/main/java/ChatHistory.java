
import java.util.ArrayList;
import java.util.List;

public class ChatHistory {
    private final List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }

    public Message getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }
}
