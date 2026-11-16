
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MementoTest {

    @Test
    void testMementoStoresMessageState() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);

        Message message = new Message(
                alice, List.of(), "Hello!");

        MessageMemento snapshot = message.save();

        assertEquals("Hello!", snapshot.getContent());
        assertEquals(message.getTimestamp(),
                snapshot.getTimestamp());
    }

    @Test
    void testRestoreFromMemento() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);

        Message original = new Message(
                alice, List.of(), "Original message");

        MessageMemento snapshot = original.save();
        Message restored = original.restore(snapshot);

        assertEquals(original.getContent(),
                restored.getContent());
        assertEquals(original.getTimestamp(),
                restored.getTimestamp());
    }

    @Test
    void testUndoLastSentMessage() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);

        alice.sendMessage(List.of(bob), "Hello Bob!");
        alice.undoLastMessage();

        assertTrue(alice.getHistory().getMessages().isEmpty());
        assertTrue(bob.getHistory().getMessages().isEmpty());
    }

    @Test
    void testUndoOnlyLastMessage() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);

        alice.sendMessage(List.of(bob), "First");
        alice.sendMessage(List.of(bob), "Second");

        alice.undoLastMessage();

        assertEquals(1, bob.getHistory().getMessages().size());
        assertEquals("First",
                bob.getHistory().getLastMessage().getContent());
    }

    @Test
    void testUndoWithMultipleRecipients() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);
        User charlie = new User("Charlie", server);

        alice.sendMessage(
                List.of(bob, charlie), "Group message");

        alice.undoLastMessage();

        assertTrue(bob.getHistory().getMessages().isEmpty());
        assertTrue(charlie.getHistory().getMessages().isEmpty());
    }

    @Test
    void testUndoWhenNoMessagesExist() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);

        assertDoesNotThrow(alice::undoLastMessage);
    }
}
