/**
 * Element of the Visitor pattern: anything that can live in the file system.
 *
 * It declares no operation of its own beyond its name. Every new operation —
 * measuring, searching, exporting — is added from the outside by a visitor,
 * which is the point of the pattern: the element classes never change.
 */
public interface FileSystemElement {

    String getName();

    /**
     * Lets a visitor act on this element.
     * Each implementation calls the overload of visit() that matches its type.
     */
    void accept(FileSystemVisitor visitor);
}
