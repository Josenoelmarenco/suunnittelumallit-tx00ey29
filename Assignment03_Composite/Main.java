/**
 * Demonstrates the Composite pattern by building an organization made of
 * departments (which can contain other departments and employees) and
 * employees, then computing the total salary and printing the whole
 * hierarchy as XML — each with a single method call on the root.
 */
public class Main {

    public static void main(String[] args) {
        // Build the organization: departments nested in departments,
        // employees inside departments.
        Department company = new Department("Acme Corp");

        Department engineering = new Department("Engineering");
        Department backend     = new Department("Backend");
        Department frontend    = new Department("Frontend");
        Department sales       = new Department("Sales");

        Employee alice = new Employee("Alice", 6000);
        Employee bob   = new Employee("Bob",   5500);
        Employee carol = new Employee("Carol", 5800);
        Employee dave  = new Employee("Dave",  5000);
        Employee erin  = new Employee("Erin",  4800);
        Employee frank = new Employee("Frank", 4500);

        backend.add(alice);
        backend.add(bob);
        frontend.add(carol);

        engineering.add(backend);
        engineering.add(frontend);
        engineering.add(dave);          // an employee directly under Engineering

        sales.add(erin);
        sales.add(frank);

        company.add(engineering);
        company.add(sales);

        // Print the full structure in XML — single method call.
        System.out.println("=== Organization structure (XML) ===");
        company.printXml();

        // Print the total salary — single method call.
        System.out.println();
        company.printTotalSalary();

        // Units can be added and removed at any time, with single calls.
        System.out.println("\n--- Hiring Grace into Frontend, removing Frank from Sales ---\n");
        frontend.add(new Employee("Grace", 5200));
        sales.remove(frank);

        System.out.println("=== Organization structure (XML) ===");
        company.printXml();
        System.out.println();
        company.printTotalSalary();
    }
}
