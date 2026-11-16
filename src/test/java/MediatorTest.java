
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MediatorTest {

    @Test
    void testUserRegistration() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);

        assertTrue(server.isRegistered(alice));
    }

    @Test
    void testSendMessage() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);

        alice.sendMessage(List.of(bob), "Hello Bob!");

        assertEquals(1, bob.getHistory().getMessages().size());
        assertEquals("Hello Bob!",
                bob.getHistory().getLastMessage().getContent());
    }

    @Test
    void testMultipleRecipients() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);
        User charlie = new User("Charlie", server);

        alice.sendMessage(List.of(bob, charlie), "Hello everyone!");

        assertEquals(1, bob.getHistory().getMessages().size());
        assertEquals(1, charlie.getHistory().getMessages().size());
    }

    @Test
    void testBlockingMessages() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);

        bob.blockUser(alice);
        alice.sendMessage(List.of(bob), "Blocked message");

        assertTrue(bob.getHistory().getMessages().isEmpty());
    }

    @Test
    void testUnblockingMessages() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);

        bob.blockUser(alice);
        bob.unblockUser(alice);

        alice.sendMessage(List.of(bob), "Hello again!");

        assertEquals(1, bob.getHistory().getMessages().size());
    }

    @Test
    void testUnregisteredUserCannotReceive() {
        ChatServer server = new ChatServer();
        User alice = new User("Alice", server);
        User bob = new User("Bob", server);

        server.unregisterUser(bob);
        alice.sendMessage(List.of(bob), "Hello!");

        assertTrue(bob.getHistory().getMessages().isEmpty());
    }
}
