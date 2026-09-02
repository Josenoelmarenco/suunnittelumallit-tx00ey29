import java.util.ArrayList;
import java.util.List;

/**
 * Composite of the Composite pattern: a department that can contain other
 * departments and employees. It handles its children uniformly through the
 * {@link OrganizationComponent} abstraction, so its own total salary and XML
 * are simply the aggregation of its children's.
 */
public class Department extends OrganizationComponent {

    private final String name;
    private final List<OrganizationComponent> children = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void add(OrganizationComponent component) {
        children.add(component);
    }

    @Override
    public void remove(OrganizationComponent component) {
        children.remove(component);
    }

    @Override
    public int getTotalSalary() {
        int total = 0;
        for (OrganizationComponent child : children) {
            total += child.getTotalSalary();
        }
        return total;
    }

    @Override
    public String toXml(int level) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(level)).append("<department name=\"").append(name).append("\">");
        for (OrganizationComponent child : children) {
            sb.append("\n").append(child.toXml(level + 1));
        }
        sb.append("\n").append(indent(level)).append("</department>");
        return sb.toString();
    }
}
