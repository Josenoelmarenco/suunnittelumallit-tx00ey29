/**
 * A single customer feedback message.
 *
 * Carries the data that travels along the chain: its category (type), the text
 * content, and the sender's email address. This is the "request" object of the
 * Chain of Responsibility pattern (a parametrized handler receives it).
 */
public class FeedbackMessage {

    private final FeedbackType type;
    private final String content;
    private final String senderEmail;

    public FeedbackMessage(FeedbackType type, String content, String senderEmail) {
        this.type = type;
        this.content = content;
        this.senderEmail = senderEmail;
    }

    public FeedbackType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    @Override
    public String toString() {
        return "[" + type + "] from " + senderEmail + ": \"" + content + "\"";
    }
}
