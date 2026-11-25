package chat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete Mediator (Mediator design pattern).
 *
 * Keeps track of every registered client by username and routes each message
 * to its recipient. When a client joins, it also tells every client who the
 * other users are, so their recipient lists stay up to date.
 */
public class ChatRoom implements ChatMediator {

    /** Registered clients by username, in the order they joined. */
    private final Map<String, ChatController> clients = new LinkedHashMap<>();

    @Override
    public void register(ChatController client) {
        String username = client.getUsername();
        if (clients.containsKey(username)) {
            throw new IllegalArgumentException("Username already in use: " + username);
        }
        clients.put(username, client);
        updateRecipientLists();
    }

    @Override
    public boolean sendMessage(String sender, String recipient, String text) {
        ChatController target = clients.get(recipient);
        if (target == null) {
            return false;                     // no such user
        }
        target.receiveMessage(sender, text);
        return true;
    }

    /** Gives every client the list of all OTHER usernames. */
    private void updateRecipientLists() {
        for (ChatController client : clients.values()) {
            List<String> others = new ArrayList<>();
            for (String username : clients.keySet()) {
                if (!username.equals(client.getUsername())) {
                    others.add(username);
                }
            }
            client.updateRecipients(others);
        }
    }
}
