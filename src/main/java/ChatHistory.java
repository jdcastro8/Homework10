
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatHistory implements IterableByUser {
    private final List<Message> messages = new ArrayList<>();
    private final List<Message> sentMessages = new ArrayList<>();
    private final List<MessageMemento> sentSnapshots = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addSentMessage(Message message) {
        messages.add(message);
        sentMessages.add(message);
        sentSnapshots.add(message.save());
    }

    public Message getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    public Message getLastSentMessage() {
        if (sentMessages.isEmpty()) {
            return null;
        }
        return sentMessages.get(sentMessages.size() - 1);
    }

    public Message undoLastSentMessage() {
        if (sentMessages.isEmpty()) {
            return null;
        }

        int last = sentMessages.size() - 1;

        Message original = sentMessages.remove(last);
        MessageMemento snapshot = sentSnapshots.remove(last);

        Message restored = original.restore(snapshot);

        if (!restored.getContent().equals(original.getContent())
                || !restored.getTimestamp().equals(original.getTimestamp())) {
            throw new IllegalStateException("Memento restoration failed");
        }

        messages.remove(original);
        return original;
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return new searchMessagesByUser(messages, userToSearchWith);
    }
}
