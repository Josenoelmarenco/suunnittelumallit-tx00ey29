/**
 * Concrete element: a file with a name and a size in megabytes.
 * It holds data only; it knows nothing about what the visitors compute.
 */
public class File implements FileSystemElement {

    private final String name;
    private final int sizeInMegabytes;

    public File(String name, int sizeInMegabytes) {
        this.name = name;
        this.sizeInMegabytes = sizeInMegabytes;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getSizeInMegabytes() {
        return sizeInMegabytes;
    }

    /** @return the extension including the dot, or an empty string if there is none. */
    public String getExtension() {
        int dot = name.lastIndexOf('.');
        return dot == -1 ? "" : name.substring(dot);
    }

    @Override
    public void accept(FileSystemVisitor visitor) {
        visitor.visit(this);
    }
}
