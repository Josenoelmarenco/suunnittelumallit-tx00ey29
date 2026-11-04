import java.time.LocalDate;

/**
 * Real subject of the Proxy pattern: the actual document holding the content.
 *
 * It performs no access control at all. A protected document is never handed
 * out directly: the library wraps it in a DocumentProxy and stores only the
 * proxy, so the content can be reached only through it.
 */
public class Document implements IDocument {

    private final String id;
    private final LocalDate creationDate;
    private final String content;

    public Document(String id, LocalDate creationDate, String content) {
        this.id = id;
        this.creationDate = creationDate;
        this.content = content;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /** Returns the content unconditionally; the user parameter is ignored. */
    @Override
    public String getContent(User user) {
        return content;
    }
}
