
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

//Iterator Pattern

public class searchMessagesByUser implements Iterator<Message> {
    private final List<Message> messages;
    private final User userToSearchWith;
    private int position;

    public searchMessagesByUser(List<Message> messages,
                                User userToSearchWith) {
        this.messages = messages;
        this.userToSearchWith = userToSearchWith;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        while (position < messages.size()) {
            Message message = messages.get(position);

            if (message.getSender() == userToSearchWith
                    || message.getRecipients().contains(userToSearchWith)) {
                return true;
            }

            position++;
        }

        return false;
    }

    @Override
    public Message next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return messages.get(position++);
    }
}
