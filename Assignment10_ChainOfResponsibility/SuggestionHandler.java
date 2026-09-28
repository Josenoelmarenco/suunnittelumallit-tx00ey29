/**
 * Concrete Handler: development suggestions.
 *
 * Handles SUGGESTION messages by logging them and assigning a priority, so the
 * product team can triage them later.
 */
public class SuggestionHandler extends FeedbackHandler {

    private int suggestionCounter = 0;

    @Override
    protected boolean canHandle(FeedbackMessage message) {
        return message.getType() == FeedbackType.SUGGESTION;
    }

    @Override
    protected void process(FeedbackMessage message) {
        String suggestionId = String.format("SUG-%04d", ++suggestionCounter);
        String text = message.getContent().toLowerCase();

        String priority;
        if (text.contains("bug") || text.contains("crash") || text.contains("security")) {
            priority = "HIGH";
        } else if (text.contains("please") || text.contains("would like") || text.contains("could")) {
            priority = "NORMAL";
        } else {
            priority = "LOW";
        }

        System.out.println("[SuggestionHandler] Suggestion " + suggestionId
                + " logged to the backlog (priority: " + priority + ").");
        System.out.println("   Thank-you note sent to " + message.getSenderEmail());
    }
}
