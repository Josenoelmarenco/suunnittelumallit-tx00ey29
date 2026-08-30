/**
 * Demonstrates the Abstract Factory pattern.
 *
 * Notice: the client code below talks ONLY to the abstract types
 * (UIFactory, Button, TextField, Checkbox). It never mentions ButtonA/ButtonB, etc.
 * Swapping 'new AFactory()' for 'new BFactory()' changes the entire look-and-feel,
 * and it is impossible to accidentally mix an A-button with a B-checkbox.
 */
public class Main {
    public static void main(String[] args) {

        // --- Pick ONE factory: the whole UI family comes from it ---
        UIFactory factory = new AFactory();   // <- change to new BFactory() to switch style

        Button    button = factory.createButton("OK");
        TextField field  = factory.createTextField("Name");
        Checkbox  check  = factory.createCheckbox("Accept terms");

        System.out.println("=== Style A ===");
        button.display();
        field.display();
        check.display();

        // --- Requirement 6: change content dynamically via setText() ---
        System.out.println("\n--- After button.setText(\"CANCEL\") ---");
        button.setText("CANCEL");
        button.display();   // the change is visible on the next display()

        // --- Show the other family for comparison ---
        System.out.println("\n=== Style B (same code, different factory) ===");
        UIFactory factoryB = new BFactory();
        factoryB.createButton("OK").display();
        factoryB.createTextField("Name").display();
        factoryB.createCheckbox("Accept terms").display();
    }
}
