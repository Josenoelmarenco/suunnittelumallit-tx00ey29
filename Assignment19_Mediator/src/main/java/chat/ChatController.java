package chat;

import java.util.List;

/**
 * Colleague (Mediator design pattern): the controller of one chat client.
 *
 * It holds the client's application logic: it validates what the user typed,
 * sends messages through the mediator, and tells its own view what to display.
 * It knows the mediator and its own view, but no other client.
 */
public class ChatController {

    private final String username;
    private final ChatMediator mediator;
    private ChatView view;

    public ChatController(String username, ChatMediator mediator) {
        this.username = username;
        this.mediator = mediator;
    }

    /** Connects the window. Must be called before the client is registered. */
    public void setView(ChatView view) {
        this.view = view;
    }

    public String getUsername() {
        return username;
    }

    /** Called by the view when the user presses Send. */
    public void sendMessage(String recipient, String text) {
        if (recipient == null || recipient.isBlank()) {
            view.showNotice("Choose a recipient first.");
            return;
        }
        String message = text.trim();
        if (message.isEmpty()) {
            view.showNotice("Type a message first.");
            return;
        }

        boolean delivered = mediator.sendMessage(username, recipient, message);
        if (delivered) {
            view.showMessage("To " + recipient + ": " + message);
            view.clearMessageInput();
            view.showNotice("");
        } else {
            view.showNotice(recipient + " is not in the chat.");
        }
    }

    /** Called by the mediator when another user sends this user a message. */
    public void receiveMessage(String sender, String text) {
        view.showMessage("From " + sender + ": " + text);
    }

    /** Called by the mediator when the set of users changes. */
    public void updateRecipients(List<String> usernames) {
        view.setRecipients(usernames);
    }
}
