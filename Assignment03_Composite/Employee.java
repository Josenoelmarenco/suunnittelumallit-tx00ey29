/**
 * Leaf of the Composite pattern: a single employee with a name and a salary.
 * An employee has no children.
 */
public class Employee extends OrganizationComponent {

    private final String name;
    private final int salary;

    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public int getTotalSalary() {
        return salary;
    }

    @Override
    public String toXml(int level) {
        return indent(level)
                + "<employee name=\"" + name + "\" salary=\"" + salary + "\"/>";
    }
}
