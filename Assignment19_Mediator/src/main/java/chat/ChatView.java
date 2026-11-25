package chat;

import java.util.List;

/**
 * What a chat controller needs from its window.
 *
 * The controller works against this interface only, so the application logic
 * does not depend on JavaFX: ChatWindow is the JavaFX implementation.
 */
public interface ChatView {

    /** Adds one line to the message history. */
    void showMessage(String line);

    /** Shows a short notice to the user, for example a validation message. */
    void showNotice(String notice);

    /** Replaces the list of users the message can be sent to. */
    void setRecipients(List<String> usernames);

    /** Empties the message field after a message has been sent. */
    void clearMessageInput();
}
