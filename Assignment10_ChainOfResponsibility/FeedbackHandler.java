/**
 * Handler (Chain of Responsibility design pattern).
 *
 * Abstract base class for every feedback handler. It holds the reference to the
 * next handler in the chain and provides the default handle() behavior:
 *
 *   - if this handler can handle the message, it processes it (exclusive
 *     handling: the request stops here);
 *   - otherwise it forwards the message to the next handler in the chain;
 *   - if there is no next handler, the message is reported as unhandled.
 *
 * Concrete handlers only decide *whether* they handle a message (canHandle) and
 * *how* (process). This is a parametrized handler: a single handle(message)
 * method receives the request object instead of one method per request type.
 */
public abstract class FeedbackHandler {

    private FeedbackHandler next;

    /**
     * Links the next handler and returns it, so a chain can be built fluently:
     *   first.setNext(second).setNext(third);
     */
    public FeedbackHandler setNext(FeedbackHandler next) {
        this.next = next;
        return next;
    }

    /** Default handling: process here, or pass the message along the chain. */
    public void handle(FeedbackMessage message) {
        if (canHandle(message)) {
            process(message);
        } else if (next != null) {
            next.handle(message);                 // forward to the next handler
        } else {
            System.out.println("[Unhandled] No handler could process " + message);
        }
    }

    /** True if this concrete handler is responsible for the given message. */
    protected abstract boolean canHandle(FeedbackMessage message);

    /** The concrete handling logic for messages this handler is responsible for. */
    protected abstract void process(FeedbackMessage message);
}
