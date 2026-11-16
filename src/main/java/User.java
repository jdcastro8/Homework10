
import java.util.List;

public class User {
    private final String name;
    private final ChatServer server;
    private final ChatHistory history = new ChatHistory();

    public User(String name, ChatServer server) {
        this.name = name;
        this.server = server;
        server.registerUser(this);
    }

    public String getName() {
        return name;
    }

    public void sendMessage(List<User> recipients, String content) {
        Message message = new Message(this, recipients, content);
        history.addSentMessage(message);
        server.sendMessage(message);
    }

    public void receiveMessage(Message message) {
        history.addMessage(message);
    }

    public void undoLastMessage() {
        Message message = history.undoLastSentMessage();

        if (message != null) {
            server.undoMessage(this, message);
        }
    }

    void removeReceivedMessage(Message message) {
        history.removeMessage(message);
    }

    public void blockUser(User user) {
        server.blockUser(this, user);
    }

    public void unblockUser(User user) {
        server.unblockUser(this, user);
    }

    public ChatHistory getHistory() {
        return history;
    }
}
