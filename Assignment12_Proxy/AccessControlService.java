import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Singleton that decides who may read which protected document.
 *
 * All proxies share this one instance, so permissions granted here apply
 * everywhere in the application.
 */
public class AccessControlService {

    private static final AccessControlService INSTANCE = new AccessControlService();

    /** Document identifier -> set of usernames allowed to read it. */
    private final Map<String, Set<String>> permissions = new HashMap<>();

    /** Private: nobody can create a second access control service. */
    private AccessControlService() {
    }

    public static AccessControlService getInstance() {
        return INSTANCE;
    }

    /** Grants one user the right to read one document. */
    public void grant(String documentId, String username) {
        permissions.computeIfAbsent(documentId, key -> new HashSet<>()).add(username);
    }

    /** Withdraws a previously granted right. */
    public void revoke(String documentId, String username) {
        Set<String> allowedUsers = permissions.get(documentId);
        if (allowedUsers != null) {
            allowedUsers.remove(username);
        }
    }

    /** @return true if the user may read the document. */
    public boolean isAllowed(String documentId, String username) {
        return permissions.getOrDefault(documentId, Set.of()).contains(username);
    }
}
