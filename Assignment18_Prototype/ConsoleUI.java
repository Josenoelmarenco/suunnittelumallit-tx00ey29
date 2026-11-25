import java.util.List;
import java.util.Scanner;

/**
 * Client (Prototype design pattern): the command-line user interface.
 *
 * The user can view the saved recommendations, create a new one from scratch,
 * or clone an existing one. Both new and cloned lists are edited as a DRAFT
 * and are added to the catalog only when the user saves them, so a discarded
 * draft leaves no trace.
 */
public class ConsoleUI {

    private final RecommendationCatalog catalog;
    private final Scanner scanner;

    public ConsoleUI(RecommendationCatalog catalog, Scanner scanner) {
        this.catalog = catalog;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("=========================================");
        System.out.println("   Book Recommendation System");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("MAIN MENU");
            System.out.println("  1) List saved recommendations");
            System.out.println("  2) Show a recommendation");
            System.out.println("  3) Create a new recommendation from scratch");
            System.out.println("  4) Clone an existing recommendation");
            System.out.println("  0) Exit");

            switch (readLine("Choose an option: ")) {
                case "1" -> listRecommendations();
                case "2" -> showRecommendation();
                case "3" -> createFromScratch();
                case "4" -> cloneExisting();
                case "0" -> running = false;
                default -> System.out.println("Unknown option, try again.");
            }
        }
        System.out.println("Goodbye!");
    }

    // --- main menu actions -------------------------------------------------

    private void listRecommendations() {
        System.out.println();
        System.out.println("Saved recommendations:");
        List<Recommendation> all = catalog.getAll();
        for (int i = 0; i < all.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + all.get(i));
        }
    }

    private void showRecommendation() {
        int index = chooseRecommendation("Which recommendation do you want to see?");
        if (index >= 0) {
            printRecommendation(catalog.get(index));
        }
    }

    private void createFromScratch() {
        String audience = readRequired("Target audience of the new list: ");
        Recommendation draft = new Recommendation(audience);
        System.out.println("New empty draft created.");
        editDraft(draft);
    }

    private void cloneExisting() {
        int index = chooseRecommendation("Which recommendation do you want to clone?");
        if (index < 0) {
            return;
        }
        Recommendation original = catalog.get(index);
        Recommendation draft = original.clone();      // Prototype: copy instead of building from scratch
        System.out.println("Draft created as a copy of \"" + original.getTargetAudience() + "\".");
        editDraft(draft);
    }

    // --- the draft editor ----------------------------------------------------

    /** Lets the user modify a draft, then save it to the catalog or discard it. */
    private void editDraft(Recommendation draft) {
        while (true) {
            System.out.println();
            System.out.println("EDITING DRAFT: " + draft);
            System.out.println("  1) Show the draft");
            System.out.println("  2) Change the target audience");
            System.out.println("  3) Add a book");
            System.out.println("  4) Remove a book");
            System.out.println("  5) Edit a book");
            System.out.println("  6) Save the draft as a new recommendation");
            System.out.println("  0) Discard the draft and go back");

            switch (readLine("Choose an option: ")) {
                case "1" -> printRecommendation(draft);
                case "2" -> {
                    draft.setTargetAudience(readRequired("New target audience: "));
                    System.out.println("Target audience changed.");
                }
                case "3" -> addBook(draft);
                case "4" -> removeBook(draft);
                case "5" -> editBook(draft);
                case "6" -> {
                    catalog.save(draft);
                    System.out.println("Saved as recommendation number " + catalog.size() + ".");
                    return;
                }
                case "0" -> {
                    System.out.println("Draft discarded.");
                    return;
                }
                default -> System.out.println("Unknown option, try again.");
            }
        }
    }

    private void addBook(Recommendation draft) {
        String title = readRequired("Title: ");
        String author = readRequired("Author: ");
        String genre = readRequired("Genre: ");
        int year = readYear("Publication year: ");
        draft.addBook(new Book(title, author, genre, year));
        System.out.println("Book added.");
    }

    private void removeBook(Recommendation draft) {
        int index = chooseBook(draft, "Which book do you want to remove?");
        if (index >= 0) {
            Book removed = draft.removeBook(index);
            System.out.println("Removed: " + removed);
        }
    }

    /** Edits one book of the draft. Pressing Enter keeps the current value. */
    private void editBook(Recommendation draft) {
        int index = chooseBook(draft, "Which book do you want to edit?");
        if (index < 0) {
            return;
        }
        Book book = draft.getBook(index);
        System.out.println("Press Enter to keep the current value.");

        String title = readLine("Title [" + book.getTitle() + "]: ");
        if (!title.isEmpty()) {
            book.setTitle(title);
        }
        String author = readLine("Author [" + book.getAuthor() + "]: ");
        if (!author.isEmpty()) {
            book.setAuthor(author);
        }
        String genre = readLine("Genre [" + book.getGenre() + "]: ");
        if (!genre.isEmpty()) {
            book.setGenre(genre);
        }
        String year = readLine("Publication year [" + book.getPublicationYear() + "]: ");
        if (!year.isEmpty()) {
            try {
                book.setPublicationYear(Integer.parseInt(year));
            } catch (NumberFormatException e) {
                System.out.println("Not a number, the year was kept.");
            }
        }
        System.out.println("Book updated: " + book);
    }

    // --- printing ------------------------------------------------------------

    private void printRecommendation(Recommendation recommendation) {
        System.out.println();
        System.out.println("Target audience: " + recommendation.getTargetAudience());
        List<Book> books = recommendation.getBooks();
        if (books.isEmpty()) {
            System.out.println("  (no books yet)");
        }
        for (int i = 0; i < books.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + books.get(i));
        }
    }

    // --- input helpers -------------------------------------------------------

    /**
     * Reads one line. If there is no more input (for example when the input
     * comes from a file), it answers "0", which means back / exit in every menu.
     */
    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println();
            return "0";
        }
        return scanner.nextLine().trim();
    }

    /** Reads a line that must not be empty. */
    private String readRequired(String prompt) {
        String text = readLine(prompt);
        while (text.isEmpty()) {
            System.out.println("This field cannot be empty.");
            text = readLine(prompt);
        }
        return text;
    }

    private int readYear(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please type the year as a number, for example 1997.");
            }
        }
    }

    /**
     * Asks for a number between 1 and max, or 0 to cancel.
     * Returns the 0-based position, or -1 if the user cancelled.
     */
    private int readChoice(String prompt, int max) {
        while (true) {
            String text = readLine(prompt + " (1-" + max + ", 0 = cancel): ");
            try {
                int number = Integer.parseInt(text);
                if (number == 0) {
                    return -1;
                }
                if (number >= 1 && number <= max) {
                    return number - 1;
                }
            } catch (NumberFormatException e) {
                // not a number: fall through and ask again
            }
            System.out.println("Please type a number between 0 and " + max + ".");
        }
    }

    private int chooseRecommendation(String question) {
        if (catalog.size() == 0) {
            System.out.println("There are no saved recommendations.");
            return -1;
        }
        listRecommendations();
        return readChoice(question, catalog.size());
    }

    private int chooseBook(Recommendation draft, String question) {
        if (draft.getBookCount() == 0) {
            System.out.println("The draft has no books.");
            return -1;
        }
        printRecommendation(draft);
        return readChoice(question, draft.getBookCount());
    }
}
