# Assignment 13 — File system handling (Visitor pattern)

A console program that models a small file system of files and directories and
applies two independent operations to it without adding a single method to the
element classes. This is the **Visitor** design pattern.

## The pattern in this project

| Role (GoF / course slides) | Class | Responsibility |
|----------------------------|-------|----------------|
| **Element** | `FileSystemElement` | Interface declaring `accept(FileSystemVisitor)`. |
| **Concrete Element** | `File`, `Directory` | Hold the data. Each `accept()` calls the `visit()` overload matching its own type. |
| **Visitor** | `FileSystemVisitor` | One overloaded `visit()` per concrete element type. |
| **Concrete Visitor** | `SizeCalculatorVisitor`, `SearchVisitor` | Each adds one operation to the whole structure. |
| **Client** | `Main` | Builds the tree and hands a visitor to its root. |

`File` and `Directory` have no `getTotalSize()` and no `search()`. Both
operations live entirely in the visitors, so adding a third one — an exporter, a
duplicate finder — means writing one new class and changing nothing else.

## How the traversal works

`Directory.accept()` lets the visitor act on the directory itself and then
passes the visitor down to every child:

```java
@Override
public void accept(FileSystemVisitor visitor) {
    visitor.visit(this);
    for (FileSystemElement child : children) {
        child.accept(visitor);
    }
}
```

The recursion therefore lives in the structure, not in the visitors. A visitor
only describes what to do with a single file and a single directory; it never
walks the tree itself, and a new visitor needs no traversal code.

## How the visitors accumulate state

Both visitors are objects with fields, and those fields grow as the traversal
proceeds.

`SizeCalculatorVisitor` keeps a running total and two counters:

```java
@Override
public void visit(File file) {
    totalSizeInMegabytes += file.getSizeInMegabytes();
    fileCount++;
}
```

`SearchVisitor` appends every match to a list:

```java
@Override
public void visit(File file) {
    if (matches(file)) {
        this.matches.add(file);
    }
}
```

Because the state lives in the visitor rather than in the elements, the same
visitor can be applied to two different subtrees and its totals simply keep
growing — the last section of the output demonstrates this.

## Design choices

- **The structure drives the traversal.** `Directory.accept()` recurses, so
  neither visitor repeats the walking logic. The alternative — letting each
  `visit(Directory)` loop over the children — would duplicate that loop in
  every visitor.
- **`SearchVisitor` supports two criteria** through the static factory methods
  `byExtension(".txt")` and `byNameContaining("report")`, matching the two
  examples given in the assignment. A private enum selects which comparison to
  apply, so one class covers both without duplicating the traversal.
- **`Directory.add()` returns `this`**, which lets the tree in `Main` be built
  with chained calls and keeps the example readable.
- **The class is named `File`, as the assignment asks.** It shadows
  `java.io.File`, so no class in this assignment imports `java.io`.
- **Directories count towards the structure, not the size.** `visit(Directory)`
  increments a counter only; a folder contributes no megabytes of its own.

## Sample output

```
=== The directory tree ===
home/
  documents/
    notes.txt  (1 MB)
    annual_report.pdf  (12 MB)
    budget_report.txt  (3 MB)
    images/
      photo1.jpg  (5 MB)
      photo2.jpg  (7 MB)
  projects/
    src/
      Main.java  (1 MB)
      Library.java  (1 MB)
    readme.txt  (1 MB)
    status_report.txt  (2 MB)

=== Visitor 1: SizeCalculatorVisitor ===
  Total size:  33 MB
  Files:       9
  Directories: 5

=== Visitor 2: SearchVisitor, by extension ===
  Looking for files with extension '.txt'
  Directories scanned: 5
  Found 4:
    notes.txt  (1 MB)
    budget_report.txt  (3 MB)
    readme.txt  (1 MB)
    status_report.txt  (2 MB)

=== Visitor 2 again, by name ===
  Looking for files whose name contains 'report'
  Directories scanned: 5
  Found 3:
    annual_report.pdf  (12 MB)
    budget_report.txt  (3 MB)
    status_report.txt  (2 MB)

=== The same visitor applied to one subtree only ===
  'projects' alone: 5 MB in 4 files

=== State accumulates: one visitor over two trees ===
  after the first subtree:  28 MB
  after the second subtree: 33 MB
```

The tree is printed first on purpose, so every number the visitors report can be
checked by hand against it.

## How to compile and run

```bash
javac *.java
java Main
```

## Files

- `FileSystemElement.java` — the Element interface.
- `File.java` — a file with a name and a size in megabytes.
- `Directory.java` — a folder holding files and subdirectories; drives the traversal.
- `FileSystemVisitor.java` — the Visitor interface, one `visit()` per element type.
- `SizeCalculatorVisitor.java` — accumulates total size and counts.
- `SearchVisitor.java` — accumulates the files matching a criterion.
- `Main.java` — builds the tree and applies both visitors.
