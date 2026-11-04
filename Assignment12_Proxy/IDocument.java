import java.time.LocalDate;

/**
 * Subject of the Proxy pattern: the common interface shared by the real
 * document and its proxy.
 *
 * Because the library stores values of this type, a protected document and an
 * unprotected one are handled in exactly the same way by the client.
 */
public interface IDocument {

    /** Unique identifier. Always public, no access control. */
    String getId();

    /** Creation date. Always public, no access control. */
    LocalDate getCreationDate();

    /**
     * Document content.
     *
     * @param user the user requesting the content
     * @throws AccessDeniedException if the user is not allowed to read it
     */
    String getContent(User user) throws AccessDeniedException;
}
