# Composite — Organization Structure

Solution for the *Composite* design-pattern assignment.

The program models an organization as a tree of **departments** and
**employees** using the **Composite** pattern, so that a single element and a
whole subtree are handled uniformly.

## Design

| Role in the pattern | Class |
|---|---|
| Component (abstract) | `OrganizationComponent` — `getName()`, `getTotalSalary()`, `toXml()`, `add()`, `remove()` |
| Leaf                 | `Employee` — a name and a salary |
| Composite            | `Department` — a name and a list of child components (departments and/or employees) |
| Client               | `Main` — builds the hierarchy and demonstrates it |

Key points, matching the assignment requirements:

- Departments can contain other departments and employees (uniform handling via `OrganizationComponent`).
- Units can be added/removed at any time with a single call (`add()` / `remove()`).
- The total salary of the whole organization is obtained with one call (`getTotalSalary()` / `printTotalSalary()`); a department sums its children recursively.
- The whole structure is printed to the console in **XML** with one call (`printXml()`); the XML reflects the hierarchy and stores data as attributes.
- `add()` / `remove()` are rejected on a leaf (`Employee`) via `UnsupportedOperationException`.

## Build & run

```bash
javac *.java
java Main
```

## Example output

```
=== Organization structure (XML) ===
<department name="Acme Corp">
  <department name="Engineering">
    <department name="Backend">
      <employee name="Alice" salary="6000"/>
      <employee name="Bob" salary="5500"/>
    </department>
    <department name="Frontend">
      <employee name="Carol" salary="5800"/>
    </department>
    <employee name="Dave" salary="5000"/>
  </department>
  <department name="Sales">
    <employee name="Erin" salary="4800"/>
    <employee name="Frank" salary="4500"/>
  </department>
</department>

Total salary of Acme Corp: 31600
```
