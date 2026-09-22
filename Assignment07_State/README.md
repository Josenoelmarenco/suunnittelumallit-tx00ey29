# Assignment 07 — Game Character Development System (State pattern)

A console-based Java game that simulates a game character progressing through
four levels of proficiency. The character's level is modeled with the **State
design pattern**: the character's behavior changes as its internal state
(level) changes.

## The State pattern in this project

| Role (GoF / course slides) | Class | Responsibility |
|----------------------------|-------|----------------|
| **Context** | `GameCharacter` | Holds the data (name, XP, HP) and a reference to the current state; delegates every action to it. |
| **State** | `CharacterState` | Interface declaring the state-specific operations: `train`, `meditate`, `fight`. |
| **Concrete State** | `NoviceState`, `IntermediateState`, `ExpertState`, `MasterState` | Each implements one level and its behavior. |
| **Client** | `Main` | Talks only to the Context; runs the game loop. |

The character object never uses `if`/`switch` on the level. It simply calls
`state.train(this)`, and the current state decides what happens. Following the
course slides ("in most cases, the State subclasses are made responsible of
state changes"), **each concrete state performs its own transition**: when the
experience threshold is reached, the state calls `character.setState(next)`.

## The finite state machine

```
        train (>=100 XP)        train (>=250 XP)         fight/train (>=500 XP)
NOVICE ───────────────► INTERMEDIATE ──────────────► EXPERT ──────────────────► MASTER
  |                          |                           |                        (game over)
  └ train only               └ train, meditate           └ train, meditate, fight
```

## Levels and actions

| Level | Available actions | Advances at |
|-------|-------------------|-------------|
| **Novice** | `train` (+25 XP) | 100 XP → Intermediate |
| **Intermediate** | `train` (+25 XP), `meditate` (+20 HP) | 250 XP → Expert |
| **Expert** | `train` (+25 XP), `meditate` (+20 HP), `fight` (−35 HP, +50 XP) | 500 XP → Master |
| **Master** | — | game ends |

Trying an action that is not available in the current level prints an
explanatory message instead of doing nothing (e.g. a Novice cannot meditate).

### Extra features (beyond the required skeleton)

- **Health cap** at 100; meditation never exceeds it.
- **Fight guard**: you need at least 35 HP to fight. This makes `meditate`
  strategically necessary at the Expert level — the player must alternate
  fighting and meditating to survive and reach Master.
- A **status line** before every turn showing level, XP and HP, plus the list
  of currently available actions.
- A `quit` command and graceful handling of unknown/empty input.

## How to compile and run

```bash
javac *.java
java Main
```

Then type one of the available actions each turn (`train`, `meditate`,
`fight`, or `quit`). Example first turns:

```
=== Game Character Development System ===
Enter your character's name: Aragorn

Welcome, Aragorn! Your journey begins as a Novice.
---------------------------------------------
Status : Aragorn | Level: Novice | XP: 0 | HP: 100/100
Actions: train  (or 'quit')
> train
You train hard as a novice. (+25 XP)
```

## Files

- `CharacterState.java` — State interface.
- `NoviceState.java`, `IntermediateState.java`, `ExpertState.java`, `MasterState.java` — concrete states.
- `GameCharacter.java` — Context.
- `Main.java` — client and game loop.
