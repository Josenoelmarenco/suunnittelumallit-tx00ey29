/**
 * Concrete Handler: general feedback.
 *
 * Handles GENERAL_FEEDBACK messages by doing a simple sentiment analysis and
 * composing an appropriate response. Placed last in the chain, it also acts as
 * a natural catch-all for plain feedback.
 */
public class GeneralFeedbackHandler extends FeedbackHandler {

    @Override
    protected boolean canHandle(FeedbackMessage message) {
        return message.getType() == FeedbackType.GENERAL_FEEDBACK;
    }

    @Override
    protected void process(FeedbackMessage message) {
        String text = message.getContent().toLowerCase();

        String sentiment;
        String reply;
        if (text.contains("great") || text.contains("love") || text.contains("thanks")
                || text.contains("excellent")) {
            sentiment = "POSITIVE";
            reply = "We're thrilled you enjoyed it - thank you for the kind words!";
        } else if (text.contains("bad") || text.contains("terrible") || text.contains("slow")
                || text.contains("disappointed")) {
            sentiment = "NEGATIVE";
            reply = "We're sorry to hear that. Your feedback has been shared with our team.";
        } else {
            sentiment = "NEUTRAL";
            reply = "Thank you for taking the time to share your thoughts with us.";
        }

        System.out.println("[GeneralFeedbackHandler] Feedback analyzed (sentiment: "
                + sentiment + ").");
        System.out.println("   Reply to " + message.getSenderEmail() + ": " + reply);
    }
}
