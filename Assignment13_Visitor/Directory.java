import java.util.ArrayList;
import java.util.List;

/**
 * Concrete element: a folder that can contain files and other directories.
 *
 * The traversal lives here, not in the visitors: accept() first lets the
 * visitor act on the directory itself and then passes the visitor down to
 * every child. A new visitor therefore needs no traversal code of its own.
 */
public class Directory implements FileSystemElement {

    private final String name;
    private final List<FileSystemElement> children = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /** Adds a file or a subdirectory. Returns this, so calls can be chained. */
    public Directory add(FileSystemElement element) {
        children.add(element);
        return this;
    }

    public List<FileSystemElement> getChildren() {
        return children;
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
        for (FileSystemElement child : children) {
            child.accept(visitor);
        }
    }
}
