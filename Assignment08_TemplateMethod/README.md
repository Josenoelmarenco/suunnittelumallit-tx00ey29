# Assignment 08 — Applying a game framework (Template Method pattern)

A console implementation of **Pig**, a classic dice game, built on top of the
provided `Game` framework using the **Template Method design pattern**.

## The Template Method pattern in this project

The framework class `Game` defines the **template method** `play()`, which fixes
the skeleton of *any* turn-based game:

```java
public final void play(int numberOfPlayers) {
    initializeGame(numberOfPlayers);
    int playerInTurn = 0;
    while (!endOfGame()) {
        playSingleTurn(playerInTurn);
        playerInTurn = ++playerInTurn % numberOfPlayers;
    }
    displayWinner();
}
```

`play()` is `final`, so the overall algorithm can never be changed — only the
individual steps can. Those steps are the abstract methods that the subclass
must implement.

| Role (GoF) | Class | Responsibility |
|------------|-------|----------------|
| **Abstract class** (defines the template method) | `Game` | Provided framework, **used as is**. Holds `play()` and declares the abstract steps. |
| **Concrete class** (implements the steps) | `PigGame` | Implements `initializeGame`, `endOfGame`, `playSingleTurn`, `displayWinner`. |
| **Helper classes** (composition) | `Die`, `Player` | Extra classes the subclass composes, as the assignment allows. |
| **Client** | `Main` | Works through the `Game` type and just calls `play()`. |

`PigGame` never overrides `play()`. The framework controls the flow; the
subclass only fills in the behavior. This is the whole point of Template Method:
**"the superclass defines the invariant algorithm; subclasses supply the
variant steps."**

## The game: Pig

- On your turn you roll a die repeatedly.
- Each roll of 2–6 adds to your **turn total**.
- You may **hold** to bank the turn total into your score, or **roll again**.
- Roll a **1** and you lose the whole turn total, scoring nothing that turn.
- The first player to reach the **target score** (default 100) wins.

The end condition is detected by the subclass: when a player's banked score
reaches the target, `PigGame` records the winner and `endOfGame()` returns true,
so the framework's loop stops and `displayWinner()` runs.

## How to compile and run

```bash
javac *.java
java Main
```

Optional command-line arguments:

```bash
java Main <numberOfPlayers> <targetScore>
# examples:
java Main            # 2 players, target 100
java Main 3          # 3 players, target 100
java Main 2 50       # 2 players, target 50 (shorter game)
```

Each turn: after the first roll you are asked `Roll again? (y = roll, n = hold)`.
Type `y` to keep rolling (risking a 1) or `n` to bank your points. If the game
is run with no input available, players hold automatically, so it always
terminates.

## Files

- `Game.java` — the framework (template method). **Not modified.**
- `PigGame.java` — the concrete game implementing the four steps.
- `Die.java`, `Player.java` — composed helper classes.
- `Main.java` — the client that calls `play()`.
