
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private final User sender;
    private final List<User> recipients;
    private final LocalDateTime timestamp;
    private final String content;

    public Message(User sender, List<User> recipients, String content) {
        this(sender, recipients, content, LocalDateTime.now());
    }

    public Message(User sender, List<User> recipients,
                   String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.recipients = new ArrayList<>(recipients);
        this.content = content;
        this.timestamp = timestamp;
    }

    public User getSender() {
        return sender;
    }

    public List<User> getRecipients() {
        return new ArrayList<>(recipients);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return sender.getName() + ": " + content;
    }
}
