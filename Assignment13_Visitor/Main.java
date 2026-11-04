/**
 * Builds a small directory tree and applies both visitors to it.
 */
public class Main {

    public static void main(String[] args) {

        Directory root = buildFileSystem();

        System.out.println("=== The directory tree ===");
        printTree(root, 0);

        System.out.println();
        System.out.println("=== Visitor 1: SizeCalculatorVisitor ===");
        SizeCalculatorVisitor sizeCalculator = new SizeCalculatorVisitor();
        root.accept(sizeCalculator);
        System.out.println("  Total size:  " + sizeCalculator.getTotalSizeInMegabytes() + " MB");
        System.out.println("  Files:       " + sizeCalculator.getFileCount());
        System.out.println("  Directories: " + sizeCalculator.getDirectoryCount());

        System.out.println();
        System.out.println("=== Visitor 2: SearchVisitor, by extension ===");
        SearchVisitor textFiles = SearchVisitor.byExtension(".txt");
        root.accept(textFiles);
        printSearch(textFiles);

        System.out.println();
        System.out.println("=== Visitor 2 again, by name ===");
        SearchVisitor reports = SearchVisitor.byNameContaining("report");
        root.accept(reports);
        printSearch(reports);

        System.out.println();
        System.out.println("=== The same visitor applied to one subtree only ===");
        Directory projects = (Directory) root.getChildren().get(1);
        SizeCalculatorVisitor subtreeSize = new SizeCalculatorVisitor();
        projects.accept(subtreeSize);
        System.out.println("  '" + projects.getName() + "' alone: "
                + subtreeSize.getTotalSizeInMegabytes() + " MB in "
                + subtreeSize.getFileCount() + " files");

        System.out.println();
        System.out.println("=== State accumulates: one visitor over two trees ===");
        SizeCalculatorVisitor shared = new SizeCalculatorVisitor();
        root.getChildren().get(0).accept(shared);
        System.out.println("  after the first subtree:  " + shared.getTotalSizeInMegabytes() + " MB");
        projects.accept(shared);
        System.out.println("  after the second subtree: " + shared.getTotalSizeInMegabytes() + " MB");
    }

    /** home/ with two subtrees: documents/ and projects/ */
    private static Directory buildFileSystem() {
        Directory documents = new Directory("documents");
        documents.add(new File("notes.txt", 1))
                 .add(new File("annual_report.pdf", 12))
                 .add(new File("budget_report.txt", 3));

        Directory images = new Directory("images");
        images.add(new File("photo1.jpg", 5))
              .add(new File("photo2.jpg", 7));
        documents.add(images);

        Directory source = new Directory("src");
        source.add(new File("Main.java", 1))
              .add(new File("Library.java", 1));

        Directory projects = new Directory("projects");
        projects.add(source)
                .add(new File("readme.txt", 1))
                .add(new File("status_report.txt", 2));

        Directory root = new Directory("home");
        root.add(documents).add(projects);
        return root;
    }

    /** Prints the tree so the reader can check the visitors' numbers by hand. */
    private static void printTree(FileSystemElement element, int level) {
        String indent = "  ".repeat(level);
        if (element instanceof Directory) {
            System.out.println(indent + element.getName() + "/");
            for (FileSystemElement child : ((Directory) element).getChildren()) {
                printTree(child, level + 1);
            }
        } else {
            File file = (File) element;
            System.out.println(indent + file.getName() + "  (" + file.getSizeInMegabytes() + " MB)");
        }
    }

    private static void printSearch(SearchVisitor visitor) {
        System.out.println("  Looking for " + visitor.getDescription());
        System.out.println("  Directories scanned: " + visitor.getDirectoriesScanned());
        System.out.println("  Found " + visitor.getMatches().size() + ":");
        for (File file : visitor.getMatches()) {
            System.out.println("    " + file.getName() + "  (" + file.getSizeInMegabytes() + " MB)");
        }
    }
}
