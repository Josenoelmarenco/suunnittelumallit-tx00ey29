import java.util.ArrayList;
import java.util.List;

/**
 * Client (Chain of Responsibility design pattern).
 *
 * Builds the chain of handlers (building the chain in the client keeps the
 * concrete handlers loosely coupled), then submits several feedback messages to
 * the FIRST handler in the chain. The client does not know which handler will
 * actually process each message - the "one-stop shop" principle.
 */
public class Main {

    public static void main(String[] args) {
        // --- Build the chain (client's responsibility) -------------------
        FeedbackHandler chain = new CompensationClaimHandler();
        chain.setNext(new ContactRequestHandler())
             .setNext(new SuggestionHandler())
             .setNext(new GeneralFeedbackHandler());

        // --- Generate several feedback messages across all categories ----
        List<FeedbackMessage> inbox = new ArrayList<>();
        inbox.add(new FeedbackMessage(FeedbackType.COMPENSATION_CLAIM,
                "My order arrived broken, I want a refund.", "anna@example.com"));
        inbox.add(new FeedbackMessage(FeedbackType.COMPENSATION_CLAIM,
                "If this is not resolved I will start a lawsuit.", "mikko@example.com"));
        inbox.add(new FeedbackMessage(FeedbackType.CONTACT_REQUEST,
                "I have a question about my last invoice.", "sofia@example.com"));
        inbox.add(new FeedbackMessage(FeedbackType.CONTACT_REQUEST,
                "I would like a price quote for 50 units.", "buyer@company.com"));
        inbox.add(new FeedbackMessage(FeedbackType.SUGGESTION,
                "There is a security bug in the login page.", "dev@example.com"));
        inbox.add(new FeedbackMessage(FeedbackType.SUGGESTION,
                "It would be nice to have a dark mode.", "lisa@example.com"));
        inbox.add(new FeedbackMessage(FeedbackType.GENERAL_FEEDBACK,
                "Great service, I love the new design!", "happy@example.com"));
        inbox.add(new FeedbackMessage(FeedbackType.GENERAL_FEEDBACK,
                "The app feels slow and I'm disappointed.", "sad@example.com"));

        // --- Route every message through the chain -----------------------
        System.out.println("=================================================================");
        System.out.println(" Customer Feedback System - Chain of Responsibility");
        System.out.println("=================================================================");

        int i = 1;
        for (FeedbackMessage message : inbox) {
            System.out.println("\n(" + i++ + ") Incoming " + message);
            chain.handle(message);          // always submitted to the first handler
        }
    }
}
