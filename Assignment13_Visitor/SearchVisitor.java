import java.util.ArrayList;
import java.util.List;

/**
 * Concrete visitor that collects the files matching a criterion.
 *
 * It accumulates state while traversing: every matching file is appended to an
 * internal list, which can be read once the traversal is over.
 */
public class SearchVisitor implements FileSystemVisitor {

    /** The two criteria this visitor supports. */
    private enum Criterion { EXTENSION, NAME_CONTAINS }

    private final Criterion criterion;
    private final String pattern;
    private final List<File> matches = new ArrayList<>();
    private int directoriesScanned = 0;

    private SearchVisitor(Criterion criterion, String pattern) {
        this.criterion = criterion;
        this.pattern = pattern;
    }

    /** Collects every file whose name ends with the given extension, e.g. ".txt". */
    public static SearchVisitor byExtension(String extension) {
        return new SearchVisitor(Criterion.EXTENSION, extension);
    }

    /** Collects every file whose name contains the given text. */
    public static SearchVisitor byNameContaining(String text) {
        return new SearchVisitor(Criterion.NAME_CONTAINS, text);
    }

    @Override
    public void visit(File file) {
        if (matches(file)) {
            this.matches.add(file);
        }
    }

    @Override
    public void visit(Directory directory) {
        directoriesScanned++;
    }

    private boolean matches(File file) {
        if (criterion == Criterion.EXTENSION) {
            return file.getExtension().equalsIgnoreCase(pattern);
        }
        return file.getName().toLowerCase().contains(pattern.toLowerCase());
    }

    public List<File> getMatches() {
        return matches;
    }

    public int getDirectoriesScanned() {
        return directoriesScanned;
    }

    /** Human readable description of what this visitor was looking for. */
    public String getDescription() {
        return criterion == Criterion.EXTENSION
                ? "files with extension '" + pattern + "'"
                : "files whose name contains '" + pattern + "'";
    }
}
