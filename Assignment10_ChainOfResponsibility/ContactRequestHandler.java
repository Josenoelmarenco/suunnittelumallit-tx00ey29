/**
 * Concrete Handler: contact requests.
 *
 * Handles CONTACT_REQUEST messages by routing them to the right department
 * based on keywords in the content, and stating the expected response time.
 */
public class ContactRequestHandler extends FeedbackHandler {

    @Override
    protected boolean canHandle(FeedbackMessage message) {
        return message.getType() == FeedbackType.CONTACT_REQUEST;
    }

    @Override
    protected void process(FeedbackMessage message) {
        String text = message.getContent().toLowerCase();
        String department;
        if (text.contains("invoice") || text.contains("payment") || text.contains("billing")) {
            department = "Billing";
        } else if (text.contains("buy") || text.contains("price") || text.contains("quote")) {
            department = "Sales";
        } else {
            department = "Customer Support";
        }
        System.out.println("[ContactRequestHandler] Routed " + message.getSenderEmail()
                + " to the " + department + " department.");
        System.out.println("   A representative will reply within 24 hours.");
    }
}
