# Assignment 02: Abstract Factory — ASCII art UI

A simple ASCII-art UI toolkit built with the **Abstract Factory** design pattern.
Two look-and-feel families (A and B); a factory produces a whole family in one style.

## How to run
```bash
javac *.java
java Main
```

## Class hierarchy
```
UIElement (abstract)  ── holds `text` + setText(); declares abstract display()
├── Button (abstract)     → ButtonA,    ButtonB
├── TextField (abstract)  → TextFieldA, TextFieldB
└── Checkbox (abstract)   → CheckboxA,  CheckboxB

UIFactory (abstract)  ── createButton / createTextField / createCheckbox
├── AFactory  → builds only style-A elements
└── BFactory  → builds only style-B elements
```

## Design note (requirement 6)
`setText()` is common to every element, so it is defined ONCE in the shared
superclass `UIElement` (together with the `text` field). The change becomes
visible the next time `display()` is called, because `display()` always reads
the current value of `text`.
