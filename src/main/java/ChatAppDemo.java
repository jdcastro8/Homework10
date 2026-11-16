
import java.util.Iterator;
import java.util.List;

public class ChatAppDemo {

    public static void main(String[] args) {
        ChatServer server = new ChatServer();

        User alice = new User("Alice", server);
        User bob = new User("Bob", server);
        User charlie = new User("Charlie", server);

        System.out.println("=================================");
        System.out.println("   CHAT APPLICATION DEMONSTRATION");
        System.out.println("=================================");

        System.out.println("\n1. REGISTER USERS");
        System.out.println("Alice registered: " + server.isRegistered(alice));
        System.out.println("Bob registered: " + server.isRegistered(bob));
        System.out.println("Charlie registered: " + server.isRegistered(charlie));

        System.out.println("\n2. MEDIATOR PATTERN - SEND MESSAGES");

        alice.sendMessage(List.of(bob), "Hello Bob!");
        bob.sendMessage(List.of(alice), "Hi Alice!");
        charlie.sendMessage(List.of(alice, bob), "Hello everyone!");

        System.out.println("Alice sent a message to Bob.");
        System.out.println("Bob replied to Alice.");
        System.out.println("Charlie sent a group message.");

        printHistory(alice);
        printHistory(bob);
        printHistory(charlie);

        System.out.println("\n3. MEDIATOR PATTERN - BLOCK USERS");

        bob.blockUser(charlie);
        charlie.sendMessage(List.of(bob), "Can you see this?");

        System.out.println("Bob blocked Charlie.");
        System.out.println("Charlie attempted to message Bob.");
        System.out.println("Bob's history after blocking:");

        printHistory(bob);

        System.out.println("\n4. MEDIATOR PATTERN - UNBLOCK USERS");

        bob.unblockUser(charlie);
        charlie.sendMessage(List.of(bob), "Now you can see this!");

        System.out.println("Bob unblocked Charlie.");
        printHistory(bob);

        System.out.println("\n5. MEMENTO PATTERN - UNDO MESSAGE");

        alice.sendMessage(List.of(bob), "This message will be undone.");

        System.out.println("Alice sent a temporary message.");
        System.out.println("Bob's history before undo:");
        printHistory(bob);

        alice.undoLastMessage();

        System.out.println("Alice undid her last sent message.");
        System.out.println("Bob's history after undo:");
        printHistory(bob);

        System.out.println("\n6. ITERATOR PATTERN - SEARCH BY USER");

        System.out.println("Alice's messages involving Bob:");
        printMessagesByUser(alice, bob);

        System.out.println("\nBob's messages involving Charlie:");
        printMessagesByUser(bob, charlie);

        System.out.println("\n7. MEDIATOR PATTERN - UNREGISTER USER");

        server.unregisterUser(charlie);

        System.out.println("Charlie registered: "
                + server.isRegistered(charlie));

        alice.sendMessage(List.of(charlie),
                "Message after unregistering.");

        System.out.println("Alice attempted to message Charlie.");
        System.out.println("Charlie's history:");
        printHistory(charlie);

        System.out.println("\n=================================");
        System.out.println("       DEMONSTRATION COMPLETE");
        System.out.println("=================================");
    }

    private static void printHistory(User user) {
        System.out.println("\n" + user.getName() + "'s chat history:");

        List<Message> messages = user.getHistory().getMessages();

        if (messages.isEmpty()) {
            System.out.println("No messages.");
            return;
        }

        for (Message message : messages) {
            System.out.println(message);
        }
    }

    private static void printMessagesByUser(User user, User searchUser) {
        Iterator<Message> iterator = user.iterator(searchUser);

        boolean found = false;

        while (iterator.hasNext()) {
            Message message = iterator.next();
            System.out.println(message);
            found = true;
        }

        if (!found) {
            System.out.println("No matching messages.");
        }
    }
}
