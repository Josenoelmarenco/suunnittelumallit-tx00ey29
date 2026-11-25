/**
 * Concrete Prototype (Prototype design pattern): a single book.
 *
 * Implements Cloneable so that Object.clone() is allowed to copy it. A book
 * only has String and int fields: a String cannot be changed once created and
 * an int is a plain value, so the field-by-field copy made by super.clone() is
 * already a fully independent copy.
 */
public class Book implements Cloneable {

    private String title;
    private String author;
    private String genre;
    private int publicationYear;

    public Book(String title, String author, String genre, int publicationYear) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    /** Returns an independent copy of this book. */
    @Override
    public Book clone() {
        try {
            return (Book) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Book implements Cloneable", e);
        }
    }

    @Override
    public String toString() {
        return "\"" + title + "\" by " + author + " (" + genre + ", " + publicationYear + ")";
    }
}
