import java.util.Scanner;

/**
 * Starts the Book Recommendation System with a few sample recommendations,
 * so there is something to view and clone from the first run.
 */
public class Main {

    public static void main(String[] args) {
        RecommendationCatalog catalog = createSampleCatalog();
        ConsoleUI ui = new ConsoleUI(catalog, new Scanner(System.in));
        ui.run();
    }

    private static RecommendationCatalog createSampleCatalog() {
        RecommendationCatalog catalog = new RecommendationCatalog();

        Recommendation fantasy = new Recommendation("Young adult fantasy readers");
        fantasy.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", 1937));
        fantasy.addBook(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 1997));
        fantasy.addBook(new Book("The Hunger Games", "Suzanne Collins", "Dystopian", 2008));
        catalog.save(fantasy);

        Recommendation sciFi = new Recommendation("Science fiction fans");
        sciFi.addBook(new Book("Dune", "Frank Herbert", "Science fiction", 1965));
        sciFi.addBook(new Book("Foundation", "Isaac Asimov", "Science fiction", 1951));
        sciFi.addBook(new Book("Neuromancer", "William Gibson", "Cyberpunk", 1984));
        catalog.save(sciFi);

        Recommendation programming = new Recommendation("Software engineering students");
        programming.addBook(new Book("Clean Code", "Robert C. Martin", "Programming", 2008));
        programming.addBook(new Book("Design Patterns: Elements of Reusable Object-Oriented Software",
                "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides", "Programming", 1994));
        programming.addBook(new Book("The Pragmatic Programmer", "Andrew Hunt, David Thomas", "Programming", 1999));
        catalog.save(programming);

        return catalog;
    }
}
