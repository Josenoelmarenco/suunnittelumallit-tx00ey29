/**
 * Concrete Handler: compensation claims.
 *
 * Handles COMPENSATION_CLAIM messages: registers the claim, makes a simple
 * approval/rejection decision, and sends an acknowledgement to the customer.
 */
public class CompensationClaimHandler extends FeedbackHandler {

    private int claimCounter = 0;

    @Override
    protected boolean canHandle(FeedbackMessage message) {
        return message.getType() == FeedbackType.COMPENSATION_CLAIM;
    }

    @Override
    protected void process(FeedbackMessage message) {
        String claimId = String.format("CLM-%04d", ++claimCounter);
        System.out.println("[CompensationClaimHandler] Claim " + claimId
                + " registered from " + message.getSenderEmail());

        // Simple decision rule: legal-sounding claims are escalated, others approved.
        String text = message.getContent().toLowerCase();
        if (text.contains("lawsuit") || text.contains("legal") || text.contains("court")) {
            System.out.println("   Decision: ESCALATED to the legal department for review.");
        } else {
            System.out.println("   Decision: APPROVED - compensation will be processed.");
        }
        System.out.println("   Acknowledgement email sent to " + message.getSenderEmail());
    }
}
