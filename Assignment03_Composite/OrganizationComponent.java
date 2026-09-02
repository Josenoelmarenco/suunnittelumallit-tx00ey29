/**
 * Component of the Composite pattern.
 *
 * Both leaves ({@link Employee}) and composites ({@link Department}) are
 * treated uniformly through this abstraction: the client can ask any node
 * for its name, its total salary, or its XML representation without knowing
 * whether it is a single employee or a whole department subtree.
 */
public abstract class OrganizationComponent {

    /** @return the name of this organizational unit. */
    public abstract String getName();

    /** @return the total salary of this unit (a leaf returns its own salary,
     *  a department returns the sum of everything it contains). */
    public abstract int getTotalSalary();

    /** @return the XML representation of this unit, indented by {@code level}. */
    public abstract String toXml(int level);

    // --- Composite operations -------------------------------------------
    // Leaves do not support children, so the default rejects the operation.
    // Department overrides these.

    public void add(OrganizationComponent component) {
        throw new UnsupportedOperationException(
                getName() + " cannot contain other units");
    }

    public void remove(OrganizationComponent component) {
        throw new UnsupportedOperationException(
                getName() + " cannot contain other units");
    }

    // --- Convenience "single method call" operations --------------------
    // Required by the assignment: print the total salary and the full XML
    // structure to the console with one call.

    public void printTotalSalary() {
        System.out.println("Total salary of " + getName() + ": " + getTotalSalary());
    }

    public void printXml() {
        System.out.println(toXml(0));
    }

    /** Two spaces per indentation level, used when building the XML. */
    protected String indent(int level) {
        return "  ".repeat(level);
    }
}
