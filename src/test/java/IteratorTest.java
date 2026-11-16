
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class IteratorTest {

    @Test
    void testIteratorFindsReceivedMessages() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);
        User charlie = new User("Charlie", server);

        alice.sendMessage(List.of(bob), "From Alice");
        charlie.sendMessage(List.of(bob), "From Charlie");

        Iterator<Message> iterator = bob.iterator(alice);

        assertTrue(iterator.hasNext());
        assertEquals("From Alice", iterator.next().getContent());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorFindsSentMessages() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);

        alice.sendMessage(List.of(bob), "Hello Bob");

        Iterator<Message> iterator = alice.iterator(bob);

        assertTrue(iterator.hasNext());
        assertEquals("Hello Bob", iterator.next().getContent());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorFiltersBySpecificUser() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);
        User charlie = new User("Charlie", server);

        bob.sendMessage(List.of(alice), "Bob message");
        charlie.sendMessage(List.of(alice), "Charlie message");
        bob.sendMessage(List.of(alice), "Bob again");

        Iterator<Message> iterator = alice.iterator(bob);
        List<String> results = new ArrayList<>();

        while (iterator.hasNext()) {
            results.add(iterator.next().getContent());
        }

        assertEquals(List.of("Bob message", "Bob again"), results);
    }

    @Test
    void testIteratorWithNoMatchingMessages() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);
        User charlie = new User("Charlie", server);

        alice.sendMessage(List.of(bob), "Hello");

        Iterator<Message> iterator = alice.iterator(charlie);

        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorNextThrowsWhenEmpty() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);

        Iterator<Message> iterator = alice.iterator(bob);

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testChatHistoryImplementsIterableByUser() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);

        assertInstanceOf(IterableByUser.class, alice.getHistory());
        assertInstanceOf(IterableByUser.class, alice);
    }

    @Test
    void testIteratorAfterUndo() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);

        alice.sendMessage(List.of(bob), "First");
        alice.sendMessage(List.of(bob), "Second");
        alice.undoLastMessage();

        Iterator<Message> iterator = bob.iterator(alice);

        assertTrue(iterator.hasNext());
        assertEquals("First", iterator.next().getContent());
        assertFalse(iterator.hasNext());
    }
}
