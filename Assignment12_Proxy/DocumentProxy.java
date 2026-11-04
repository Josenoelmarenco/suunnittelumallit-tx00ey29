import java.time.LocalDate;

/**
 * Proxy of the Proxy pattern: a protection proxy that guards a real document.
 *
 * It implements the same interface as the real document, so it can be used
 * anywhere a document is expected. Identifier and creation date are forwarded
 * without any check; the content is released only after the access control
 * service approves the user.
 */
public class DocumentProxy implements IDocument {

    private final Document realDocument;

    public DocumentProxy(Document realDocument) {
        this.realDocument = realDocument;
    }

    @Override
    public String getId() {
        return realDocument.getId();
    }

    /** No access control: the creation date is public information. */
    @Override
    public LocalDate getCreationDate() {
        return realDocument.getCreationDate();
    }

    /**
     * Asks the access control service whether the user may read this document.
     * Only then is the request forwarded to the real document.
     */
    @Override
    public String getContent(User user) throws AccessDeniedException {
        if (!AccessControlService.getInstance().isAllowed(getId(), user.getUsername())) {
            throw new AccessDeniedException(user.getUsername(), getId());
        }
        return realDocument.getContent(user);
    }
}
