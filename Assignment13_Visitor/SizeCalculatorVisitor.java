/**
 * Concrete visitor that measures the file system.
 *
 * It accumulates state while traversing: the running total of file sizes and
 * how many files and directories it has seen. The totals are available once
 * the traversal is finished.
 */
public class SizeCalculatorVisitor implements FileSystemVisitor {

    private int totalSizeInMegabytes = 0;
    private int fileCount = 0;
    private int directoryCount = 0;

    @Override
    public void visit(File file) {
        totalSizeInMegabytes += file.getSizeInMegabytes();
        fileCount++;
    }

    /** A directory adds nothing to the size; it is only counted. */
    @Override
    public void visit(Directory directory) {
        directoryCount++;
    }

    public int getTotalSizeInMegabytes() {
        return totalSizeInMegabytes;
    }

    public int getFileCount() {
        return fileCount;
    }

    public int getDirectoryCount() {
        return directoryCount;
    }
}
