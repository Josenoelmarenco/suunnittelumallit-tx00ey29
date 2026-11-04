/**
 * Visitor of the Visitor pattern.
 *
 * One overloaded visit() per concrete element type. Adding an operation to the
 * file system means writing a new implementation of this interface; no element
 * class has to be touched.
 */
public interface FileSystemVisitor {

    void visit(File file);

    void visit(Directory directory);
}
