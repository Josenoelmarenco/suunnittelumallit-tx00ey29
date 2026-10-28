# Suunnittelumallit (Design Patterns) — TX00EY29-3013

Personal assignment solutions for the **Design Patterns** course at Metropolia
University of Applied Sciences (teacher: Vesa Ollikainen).

Course materials & assignment specs: https://github.com/vesavvo/design_patterns

## Assignments

| # | Pattern | Family | Folder | Run |
|---|---------|--------|--------|-----|
| 01 | Factory Method — RPG map generator | Creational | [Assignment01_FactoryMethod](Assignment01_FactoryMethod) | `javac *.java && java Game` |
| 02 | Abstract Factory — ASCII UI toolkit | Creational | [Assignment02_AbstractFactory](Assignment02_AbstractFactory) | `javac *.java && java Main` |
| 03 | Composite — Organization structure | Structural | [Assignment03_Composite](Assignment03_Composite) | `javac *.java && java Main` |
| 04 | Observer — Weather station simulator | Behavioral | [Assignment04_Observer](Assignment04_Observer) | `javac *.java && java Main` |
| 05 | Singleton — Logger system | Creational | [Assignment05_Singleton](Assignment05_Singleton) | `javac *.java && java Main` |
| 06 | Decorator — Customizable printer | Structural | [Assignment06_Decorator](Assignment06_Decorator) | `javac *.java && java Main` |
| 07 | State — Game character development | Behavioral | [Assignment07_State](Assignment07_State) | `javac *.java && java Main` |
| 08 | Template Method — Pig dice game on a framework | Behavioral | [Assignment08_TemplateMethod](Assignment08_TemplateMethod) | `javac *.java && java Main` |
| 09 | Strategy — Sorting algorithm benchmark | Behavioral | [Assignment09_Strategy](Assignment09_Strategy) | `javac *.java && java Main` |
| 10 | Chain of Responsibility — Customer feedback system | Behavioral | [Assignment10_ChainOfResponsibility](Assignment10_ChainOfResponsibility) | `javac *.java && java Main` |
| 11 | Memento — Improved history functionality (undo/redo + history window) | Behavioral | [Assignment11_Memento](Assignment11_Memento) | Maven — see its README |

Assignments 01–10 are self-contained Java solutions (plain `javac` / `java`, no
build tool), each with its own README describing the pattern and its roles.

**Assignment 11 is the exception.** It extends the teacher's JavaFX example
(`memento.guistate`), so it keeps that package and needs the JavaFX
dependencies. It is therefore a small self-contained **Maven** project rather
than a flat set of `.java` files. Open the folder in IntelliJ IDEA (detected as
a Maven project) and run `Main`, or see its README for the command line.
