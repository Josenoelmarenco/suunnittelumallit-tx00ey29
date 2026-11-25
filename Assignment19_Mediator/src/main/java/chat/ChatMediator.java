package chat;

/**
 * Mediator (Mediator design pattern).
 *
 * The only thing a chat client knows about the rest of the chat. Clients never
 * hold references to each other: they register here and send every message
 * through this interface.
 */
public interface ChatMediator {

    /** Adds a client to the chat. Usernames must be unique. */
    void register(ChatController client);

    /**
     * Delivers a message from one user to another.
     *
     * @return true if the recipient exists and the message was delivered
     */
    boolean sendMessage(String sender, String recipient, String text);
}
