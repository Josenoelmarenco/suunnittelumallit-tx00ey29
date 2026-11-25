# Assignment 19 — Chat app (Mediator pattern)

A JavaFX chat application with three clients, Alice, Bob and Charlie, each in
its own window on the same machine. A user picks a recipient, types a message
and presses **Send**; the message appears in the recipient's window. The
clients never talk to each other directly: every message goes through a
central **mediator**. This is the **Mediator** design pattern.

## The pattern in this project

| Role (GoF / course slides) | Class | Responsibility |
|----------------------------|-------|----------------|
| **Mediator** | `ChatMediator` | Interface the clients talk to: `register(client)` and `sendMessage(sender, recipient, text)`. |
| **Concrete Mediator** | `ChatRoom` | Keeps the registered clients by username, delivers each message to its recipient, and gives every client the list of the other users whenever someone joins. |
| **Colleague** | `ChatController` | The controller of one client. Validates the input, sends through the mediator and tells its own window what to show. It knows the mediator and its own view, never another client. |

There is only one kind of colleague (every client behaves the same), so
`ChatController` is used directly without a separate abstract Colleague class.

## Separating the user interface from the logic

```
ChatWindow (Alice) ──► ChatController (Alice) ──► ChatRoom ──► ChatController (Bob) ──► ChatWindow (Bob)
   user interface          logic                  mediator          logic                 user interface
```

- `ChatWindow` is the JavaFX window. It only displays things and forwards the
  user's actions (Send button, Enter key) to its controller.
- `ChatView` is the interface the controller uses to update its window
  (`showMessage`, `showNotice`, `setRecipients`, `clearMessageInput`).
  `ChatController` therefore does not depend on JavaFX at all; `ChatWindow` is
  just one implementation of `ChatView`.
- `ChatController` holds the client's logic, one controller per client, as
  the assignment requires.

As the assignment asks, this is not MVC: there is no shared model that the
windows observe. The controllers coordinate only through the mediator.

## What happens when Alice sends a message to Bob

1. Alice presses **Send**; `ChatWindow` calls `controller.sendMessage("Bob", "Hi Bob!")`.
2. Alice's `ChatController` checks that a recipient is chosen and the message is not empty.
3. It calls `chatRoom.sendMessage("Alice", "Bob", "Hi Bob!")`.
4. `ChatRoom` looks up Bob's controller and calls `receiveMessage("Alice", "Hi Bob!")`.
5. Bob's controller asks its view to show `From Alice: Hi Bob!`.
6. Back in Alice's controller, the mediator reported the message as delivered, so it shows `To Bob: Hi Bob!` and clears the message field.

## Each window

- the username in the window title (`Chat - Alice`);
- a read-only text area with every message sent and received, with the other user's name (`To Bob: …`, `From Bob: …`);
- a combo box to choose the recipient, filled by the mediator with the other users;
- a text field for the message (Enter also sends);
- a **Send** button;
- a notice line for messages such as "Choose a recipient first."

## Design choices

- **Usernames are unique**: `ChatRoom.register` rejects a name that is already in use.
- **Recipient lists come from the mediator**: when a client registers, `ChatRoom`
  sends every client the names of the others. Adding a fourth user only means
  adding a name to `USERNAMES` in `ChatApp`; no other class changes.
- **The mediator reports whether a message was delivered**, so the sender only
  records `To …` lines for messages that actually arrived.
- **No extra threads**: JavaFX runs everything on its application thread, and
  delivering a message is a simple method call.

## Run

Like Assignments 11 and 17, this uses JavaFX, so it is a small **Maven** project.

- **IntelliJ IDEA:** open the `Assignment19_Mediator` folder (detected as a
  Maven project) and run `chat.Main`.
- **Command line:** `mvn javafx:run`

`Main` only launches `ChatApp` (the JavaFX application). Starting JavaFX from a
class that does not extend `Application` lets it run from the IDE without a
`module-info.java`. The three windows open side by side.

## Files

- `ChatMediator.java` — the mediator interface.
- `ChatRoom.java` — the concrete mediator.
- `ChatController.java` — the controller (colleague) of one client.
- `ChatView.java` — what a controller needs from its window.
- `ChatWindow.java` — the JavaFX window of one client.
- `ChatApp.java` — creates the mediator, the controllers and the windows.
- `Main.java` — launcher.
