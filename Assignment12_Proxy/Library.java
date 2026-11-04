import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The document library. It stores identifier -> document pairs, where a
 * document is either a real Document (unprotected) or a DocumentProxy
 * (protected). Both are stored as IDocument, so the library and its clients
 * treat them identically.
 */
public class Library {

    private final Map<String, IDocument> documents = new HashMap<>();

    /** Adds an unprotected document; the real object is stored as it is. */
    public void addPublicDocument(String id, LocalDate creationDate, String content) {
        documents.put(id, new Document(id, creationDate, content));
    }

    /**
     * Factory method for protected documents: it builds the real document and
     * its proxy, and stores only the proxy. The real object is never published,
     * so the content is reachable only through the proxy.
     */
    public void addProtectedDocument(String id, LocalDate creationDate, String content) {
        Document realDocument = new Document(id, creationDate, content);
        documents.put(id, new DocumentProxy(realDocument));
    }

    /** @return the document for this identifier, or null if there is none. */
    public IDocument getDocument(String id) {
        return documents.get(id);
    }

    public Collection<IDocument> getAllDocuments() {
        return documents.values();
    }
}
