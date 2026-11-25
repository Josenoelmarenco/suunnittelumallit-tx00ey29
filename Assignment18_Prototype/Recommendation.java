import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete Prototype (Prototype design pattern): a list of books recommended
 * to one target audience.
 *
 * clone() makes a DEEP copy: the copy gets its own list, and that list holds
 * copies of every book. Changing the copy (its audience, its list or any of its
 * books) therefore never changes the original.
 */
public class Recommendation implements Cloneable {

    private String targetAudience;
    private List<Book> books;   // not final: clone() gives the copy a new list

    public Recommendation(String targetAudience) {
        this.targetAudience = targetAudience;
        this.books = new ArrayList<>();
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    /** Removes and returns the book at the given position (0-based). */
    public Book removeBook(int index) {
        return books.remove(index);
    }

    /** Returns the book at the given position (0-based), so it can be edited. */
    public Book getBook(int index) {
        return books.get(index);
    }

    public int getBookCount() {
        return books.size();
    }

    /** Read-only view of the books: the list can only be changed through this class. */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    /** Returns a deep copy of this recommendation. */
    @Override
    public Recommendation clone() {
        try {
            // 1) Field-by-field copy: copies targetAudience and the REFERENCE to the list.
            Recommendation copy = (Recommendation) super.clone();
            // 2) Give the copy a list of its own...
            copy.books = new ArrayList<>();
            // 3) ...filled with a copy of every book.
            for (Book book : this.books) {
                copy.books.add(book.clone());
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Recommendation implements Cloneable", e);
        }
    }

    @Override
    public String toString() {
        return targetAudience + " (" + books.size() + (books.size() == 1 ? " book)" : " books)");
    }
}
