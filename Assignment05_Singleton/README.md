# Singleton — Logger System

Solution for the *Singleton* design-pattern assignment.

A `Logger` that exists **exactly once** in the whole application (Singleton),
writes each message on its own line to a file, can switch files at runtime, and
closes its resources safely.

## Design

| Element | Purpose |
|---|---|
| `private static Logger instance` | the single shared instance |
| `private Logger()` | private constructor — nobody else can create one; opens the default file |
| `getInstance()` | global access point; lazily creates the instance (thread-safe) |
| `write(String)` | appends a timestamped message on a new line |
| `setFileName(String)` | closes the current file and opens a new one |
| `close()` | closes the logger and its file resources |

All file I/O is wrapped in `try-catch`, so an I/O error is reported without
crashing the program.

## Build & run

```bash
javac *.java
java Main
```

## Example output (console)

```
Same instance? true
Switching log file to 'errors.log'...
Done. Wrote to log.txt and errors.log.
```

And the files it produces:

`log.txt`
```
[2026-09-09 05:54:45] Application started
[2026-09-09 05:54:45] Loading configuration
[2026-09-09 05:54:45] Message from another reference (same singleton)
```

`errors.log`
```
[2026-09-09 05:54:45] Something went wrong here
[2026-09-09 05:54:45] ...and here too
```
