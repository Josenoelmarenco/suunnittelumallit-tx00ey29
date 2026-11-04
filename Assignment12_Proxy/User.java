/**
 * A user of the document library.
 *
 * In a real application, creating a user would involve authentication; here a
 * username is enough to demonstrate the pattern.
 */
public class User {

    private final String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
