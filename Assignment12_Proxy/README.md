# Assignment 12 — Protected documents (Proxy pattern)

A console program that models a document library in which some documents are
freely readable and others are protected. A protected document is stored behind
a **protection proxy** that consults an access control service before releasing
the content. This is the **Proxy** design pattern.

## The pattern in this project

| Role (GoF / course slides) | Class | Responsibility |
|----------------------------|-------|----------------|
| **Subject** | `IDocument` | The common interface: `getId()`, `getCreationDate()`, `getContent(User)`. |
| **RealSubject** | `Document` | Holds the real content. Performs no access control. |
| **Proxy** | `DocumentProxy` | Implements the same interface, guards the content, forwards the rest. |
| **Client** | `Main`, `User` | Works through `IDocument` and never knows whether it holds a proxy. |

Supporting classes: `Library` (the catalogue), `AccessControlService`
(singleton) and `AccessDeniedException`.

Because `Document` and `DocumentProxy` share `IDocument`, the library stores
both kinds in one `HashMap<String, IDocument>` and the client treats them
identically — which is the point of the pattern.

## How protection works

`getId()` and `getCreationDate()` are forwarded by the proxy without any check,
since the assignment states that identifiers and creation dates are always
public. Only `getContent(User)` is guarded:

```java
@Override
public String getContent(User user) throws AccessDeniedException {
    if (!AccessControlService.getInstance().isAllowed(getId(), user.getUsername())) {
        throw new AccessDeniedException(user.getUsername(), getId());
    }
    return realDocument.getContent(user);
}
```

## Design choices

- **The real document of a protected entry is unreachable.** `Library`
  provides a factory method that builds both objects and stores **only the
  proxy**; the `Document` exists solely as a local variable captured by the
  proxy, so no caller can obtain it.

  ```java
  public void addProtectedDocument(String id, LocalDate creationDate, String content) {
      Document realDocument = new Document(id, creationDate, content);
      documents.put(id, new DocumentProxy(realDocument));
  }
  ```

- **`AccessControlService` is a singleton** with a private constructor and a
  single eagerly created instance, so every proxy consults the same permission
  table. Permissions are kept as `Map<String, Set<String>>` — document
  identifier to the set of usernames allowed to read it — which makes
  `isAllowed` a direct lookup and lets a document have many readers.

- **`AccessDeniedException` is a checked exception.** Denied access is an
  expected outcome of this system, not a programming error, so the compiler
  requires callers to deal with it explicitly.

- **`Document.getContent(User)` ignores its parameter.** The real document
  never decides anything about access; keeping the parameter is what allows it
  to implement the same interface as the proxy.

- **Permissions can be granted and revoked at run time** (`grant` / `revoke`),
  which demonstrates that the proxy asks the service on every call instead of
  caching the answer.

## Sample output

```
=== 1. Catalogue: id and creation date need no permission ===
  DOC-001   created 2026-01-15
  DOC-002   created 2026-03-02
  DOC-003   created 2026-04-20

=== 2. Unprotected document: any user may read it ===
  carol -> DOC-001: "Company handbook: welcome to Acme Corp."

=== 3. Protected document, allowed user ===
  alice -> DOC-002: "Q1 salary review: Alice 6000, Bob 5500."
  bob -> DOC-002: "Q1 salary review: Alice 6000, Bob 5500."

=== 4. Protected document, user without permission ===
  carol -> DOC-002: DENIED (User 'carol' is not allowed to read document 'DOC-002')
  bob -> DOC-003: DENIED (User 'bob' is not allowed to read document 'DOC-003')

=== 5. The creation date of a protected document is still public ===
  carol reads the date of DOC-003: 2026-04-20

=== 6. Permissions are shared: one service for every proxy ===
  carol -> DOC-003: "Merger plan with Globex, strictly confidential."
  carol -> DOC-003: DENIED (User 'carol' is not allowed to read document 'DOC-003')
```

## How to compile and run

```bash
javac *.java
java Main
```

## Files

- `IDocument.java` — the common interface (Subject).
- `Document.java` — the real document (RealSubject).
- `DocumentProxy.java` — the protection proxy.
- `AccessControlService.java` — singleton deciding who may read what.
- `AccessDeniedException.java` — thrown when access is refused.
- `Library.java` — the catalogue, with the factory method for protected documents.
- `User.java` — a user with a username.
- `Main.java` — the client, demonstrating six scenarios.
