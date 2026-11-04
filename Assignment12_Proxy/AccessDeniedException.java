/**
 * Thrown by a DocumentProxy when a user requests the content of a protected
 * document they are not allowed to read.
 */
public class AccessDeniedException extends Exception {

    public AccessDeniedException(String username, String documentId) {
        super("User '" + username + "' is not allowed to read document '" + documentId + "'");
    }
}
