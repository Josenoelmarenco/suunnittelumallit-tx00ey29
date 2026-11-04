import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Demonstrates the protection proxy with several users and both protected and
 * unprotected documents.
 */
public class Main {

    public static void main(String[] args) {

        Library library = new Library();

        library.addPublicDocument("DOC-001", LocalDate.of(2026, 1, 15),
                "Company handbook: welcome to Acme Corp.");
        library.addProtectedDocument("DOC-002", LocalDate.of(2026, 3, 2),
                "Q1 salary review: Alice 6000, Bob 5500.");
        library.addProtectedDocument("DOC-003", LocalDate.of(2026, 4, 20),
                "Merger plan with Globex, strictly confidential.");

        AccessControlService accessControl = AccessControlService.getInstance();
        accessControl.grant("DOC-002", "alice");
        accessControl.grant("DOC-003", "alice");
        accessControl.grant("DOC-002", "bob");

        User alice = new User("alice");
        User bob   = new User("bob");
        User carol = new User("carol");

        System.out.println("=== 1. Catalogue: id and creation date need no permission ===");
        List<IDocument> catalogue = new ArrayList<>(library.getAllDocuments());
        catalogue.sort(Comparator.comparing(IDocument::getId));
        for (IDocument document : catalogue) {
            System.out.println("  " + document.getId() + "   created " + document.getCreationDate());
        }

        System.out.println();
        System.out.println("=== 2. Unprotected document: any user may read it ===");
        read(library, "DOC-001", carol);

        System.out.println();
        System.out.println("=== 3. Protected document, allowed user ===");
        read(library, "DOC-002", alice);
        read(library, "DOC-002", bob);

        System.out.println();
        System.out.println("=== 4. Protected document, user without permission ===");
        read(library, "DOC-002", carol);
        read(library, "DOC-003", bob);

        System.out.println();
        System.out.println("=== 5. The creation date of a protected document is still public ===");
        IDocument secret = library.getDocument("DOC-003");
        System.out.println("  carol reads the date of DOC-003: " + secret.getCreationDate());

        System.out.println();
        System.out.println("=== 6. Permissions are shared: one service for every proxy ===");
        accessControl.grant("DOC-003", "carol");
        read(library, "DOC-003", carol);
        accessControl.revoke("DOC-003", "carol");
        read(library, "DOC-003", carol);
    }

    /** Asks the library for a document and prints the result of reading it. */
    private static void read(Library library, String documentId, User user) {
        IDocument document = library.getDocument(documentId);
        try {
            System.out.println("  " + user.getUsername() + " -> " + documentId
                    + ": \"" + document.getContent(user) + "\"");
        } catch (AccessDeniedException e) {
            System.out.println("  " + user.getUsername() + " -> " + documentId
                    + ": DENIED (" + e.getMessage() + ")");
        }
    }
}
