# Assignment 18 — Book recommendations (Prototype pattern)

A console program that manages lists of book recommendations, each aimed at a
target audience. A new list can be created from scratch, or by **cloning** an
existing list that is a close fit and then modifying the copy. Creating new
objects by copying existing ones is the **Prototype** design pattern.

## The pattern in this project

| Role (GoF / course slides) | Class | Responsibility |
|----------------------------|-------|----------------|
| **Prototype** | `java.lang.Cloneable` + `Object.clone()` | Java's built-in cloning mechanism (slide 10): no custom Prototype interface is needed. |
| **Concrete Prototype** | `Book` | Clones itself with `super.clone()`. |
| **Concrete Prototype** | `Recommendation` | Clones itself **deeply**: new list, and a clone of every book. |
| **Client** | `ConsoleUI` | Creates new recommendations by asking an existing one to clone itself. |

`RecommendationCatalog` stores the saved lists; every saved list can be used as
a prototype for a new one. `Main` fills the catalog with three sample lists and
starts the user interface.

## Deep copy of the recommendation

The assignment requires that `Recommendation.clone()` produces a deep copy of
the list of books, not just a copy of the list reference:

```java
@Override
public Recommendation clone() {
    try {
        Recommendation copy = (Recommendation) super.clone();   // copies the audience and the list REFERENCE
        copy.books = new ArrayList<>();                          // the copy gets a list of its own...
        for (Book book : this.books) {
            copy.books.add(book.clone());                        // ...with a copy of every book
        }
        return copy;
    } catch (CloneNotSupportedException e) {
        throw new AssertionError("Recommendation implements Cloneable", e);
    }
}
```

Each of the three lines matters:

- With **only** `super.clone()`, the original and the copy share the same list:
  adding a book to the copy also adds it to the original.
- With a new list holding the **same** `Book` objects, adding and removing is
  independent, but editing a book of the copy (for example its genre) also
  changes the book in the original.
- With a new list of **cloned** books, every change to the copy stays in the copy.

`Book.clone()` can stay a plain `super.clone()`: its fields are `String`
(immutable) and `int` (a value), so a field-by-field copy is already
independent.

## How a cloned recommendation can be modified

`Recommendation` provides:

| Method | Purpose |
|--------|---------|
| `setTargetAudience(String)` | change the audience |
| `addBook(Book)` | add a book |
| `removeBook(int)` | remove the book at a position |
| `getBook(int)` | get a book to edit it (`Book` has setters for title, author, genre and year) |
| `getBooks()` | read-only view of the books, so the list is changed only through the methods above |

In the user interface, both new and cloned lists are edited as a **draft**. A
draft is added to the catalog only when the user saves it; discarding it leaves
the catalog unchanged.

## User interface

```
MAIN MENU
  1) List saved recommendations
  2) Show a recommendation
  3) Create a new recommendation from scratch
  4) Clone an existing recommendation
  0) Exit
```

The draft editor can show the draft, change the target audience, add, remove
and edit books, save the draft as a new recommendation, or discard it.

## Sample session

Cloning "Young adult fantasy readers", changing the audience, replacing one
book and editing the genre of another, then saving (menus omitted):

```
Draft created as a copy of "Young adult fantasy readers".
New target audience: Adult fantasy readers
Removed: "The Hunger Games" by Suzanne Collins (Dystopian, 2008)
Title: The Name of the Wind
Author: Patrick Rothfuss
Genre: Fantasy
Publication year: 2007
Book added.
Which book do you want to edit? (1-3, 0 = cancel): 1
Press Enter to keep the current value.
Title [The Hobbit]: 
Author [J.R.R. Tolkien]: 
Genre [Fantasy]: Classic fantasy
Publication year [1937]: 
Book updated: "The Hobbit" by J.R.R. Tolkien (Classic fantasy, 1937)
Saved as recommendation number 4.
```

The original list is unchanged, which shows that the copy was deep:

```
Target audience: Young adult fantasy readers
  1. "The Hobbit" by J.R.R. Tolkien (Fantasy, 1937)
  2. "Harry Potter and the Philosopher's Stone" by J.K. Rowling (Fantasy, 1997)
  3. "The Hunger Games" by Suzanne Collins (Dystopian, 2008)

Target audience: Adult fantasy readers
  1. "The Hobbit" by J.R.R. Tolkien (Classic fantasy, 1937)
  2. "Harry Potter and the Philosopher's Stone" by J.K. Rowling (Fantasy, 1997)
  3. "The Name of the Wind" by Patrick Rothfuss (Fantasy, 2007)
```

## How to compile and run

```bash
javac *.java
java Main
```

## Files

- `Book.java` — a book; clones itself.
- `Recommendation.java` — a target audience and a list of books; deep clone and modification methods.
- `RecommendationCatalog.java` — the saved recommendations.
- `ConsoleUI.java` — the command-line user interface (client).
- `Main.java` — sample data and program start.
