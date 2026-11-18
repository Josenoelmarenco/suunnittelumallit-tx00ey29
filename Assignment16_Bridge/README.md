# Assignment 16 — Extending a Bridge implementation (Bridge pattern)

This assignment is returned as a **PDF report**, not as a GitHub link:
[`Bridge_Report_JoseNoelMarenco.pdf`](Bridge_Report_JoseNoelMarenco.pdf).

The selected implementation is the *devices and remotes* example from
Refactoring Guru:

- Example page: https://refactoring.guru/design-patterns/bridge/java/example
- Source: https://github.com/RefactoringGuru/design-patterns-java
  (package `refactoring_guru.bridge.example`, commit `f09ed68`)

The extension adds a new device and a new remote and modifies no existing class.

| Role | New class | What it adds |
|------|-----------|--------------|
| Implementor (extension) | `devices/SmartDevice` | Interface extending `Device` with internet, app and web-page primitives. |
| Concrete Implementor | `devices/SmartTv` | A smart TV: the basic `Device` primitives plus `SmartDevice`. |
| Refined Abstraction | `remotes/SmartRemote` | Extends `AdvancedRemote` with `browseInternet(url)` and `voiceControl(command)`. |
| Client | `ExtensionDemo` | Demonstrates every new feature and remote/device combination. |
| Tests | `ExtensionTest` | 23 automated checks without a test library. |

## About the original files

The original example is © Alexander Shvets (Refactoring.Guru) and licensed under
[CC BY-NC-ND 4.0](https://creativecommons.org/licenses/by-nc-nd/4.0/). Its eight
files (`Demo`, `Device`, `Tv`, `Radio`, `Remote`, `BasicRemote`,
`AdvancedRemote`, and `OutputDemo.txt`) are used **unchanged** and are **not
committed** to this repository (see `.gitignore`). Only the new classes are
included here.

To run the project, copy the original files from the source repository into
`src/refactoring_guru/bridge/example/` (keeping their `devices/` and `remotes/`
subfolders), then:

```bash
javac -Xlint:all -d out $(find src -name "*.java")
java -cp out refactoring_guru.bridge.example.Demo            # original demo, unchanged
java -cp out refactoring_guru.bridge.example.ExtensionDemo   # demonstration
java -cp out refactoring_guru.bridge.example.ExtensionTest   # automated checks
```
