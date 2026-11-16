
import java.util.*;

public class ChatServer {
    private final Set<User> users = new HashSet<>();
    private final Map<User, Set<User>> blockedUsers = new HashMap<>();

    public void registerUser(User user) {
        users.add(user);
    }

    public void unregisterUser(User user) {
        users.remove(user);
        blockedUsers.remove(user);
        for (Set<User> blocked : blockedUsers.values()) {
            blocked.remove(user);
        }
    }

    public void blockUser(User blocker, User blocked) {
        if (!users.contains(blocker) || !users.contains(blocked)) {
            return;
        }
        blockedUsers.computeIfAbsent(
                blocker, k -> new HashSet<>()).add(blocked);
    }

    public void unblockUser(User blocker, User blocked) {
        blockedUsers.getOrDefault(
                blocker, Collections.emptySet()).remove(blocked);
    }

    public void sendMessage(Message message) {
        if (!users.contains(message.getSender())) {
            return;
        }

        for (User recipient : message.getRecipients()) {
            if (!users.contains(recipient)) {
                continue;
            }

            if (blockedUsers.getOrDefault(
                            recipient, Collections.emptySet())
                    .contains(message.getSender())) {
                continue;
            }

            recipient.receiveMessage(message);
        }
    }

    public boolean isRegistered(User user) {
        return users.contains(user);
    }
}
