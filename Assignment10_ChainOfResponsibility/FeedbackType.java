/**
 * The categories of customer feedback the system can receive.
 * Each value corresponds to one concrete handler in the chain.
 */
public enum FeedbackType {
    COMPENSATION_CLAIM,   // customer asks for a refund / compensation
    CONTACT_REQUEST,      // customer wants to be contacted by a department
    SUGGESTION,           // customer proposes an improvement / new feature
    GENERAL_FEEDBACK      // general comments, praise or complaints
}
