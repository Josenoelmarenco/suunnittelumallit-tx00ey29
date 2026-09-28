# Assignment 10 — Customer feedback system (Chain of Responsibility pattern)

A console program that routes different kinds of customer feedback through a
chain of handlers. Each message is submitted to the first handler; the handlers
pass it along until the one responsible for that category processes it. This is
the **Chain of Responsibility** design pattern.

## The pattern in this project

| Role (GoF / course slides) | Class | Responsibility |
|----------------------------|-------|----------------|
| **Handler** | `FeedbackHandler` | Abstract base: holds the `next` handler and the default `handle()` that either processes the message or forwards it. |
| **Concrete Handler** | `CompensationClaimHandler`, `ContactRequestHandler`, `SuggestionHandler`, `GeneralFeedbackHandler` | Each declares which message type it handles (`canHandle`) and how (`process`). |
| **Client** | `Main` | Builds the chain and submits every message to the first handler. |
| **Request** | `FeedbackMessage` + `FeedbackType` | The object that travels along the chain (type, content, sender email). |

The client always calls `chain.handle(message)` on the **first** handler and
never needs to know which handler will actually deal with it — the *one-stop
shop* principle from the course slides.

## Design choices (mapped to the slides)

- **Exclusive handling** (slide 8): each handler either fully handles the
  message or forwards it to the next one; it never does both. The base
  `handle()` implements exactly that: `canHandle → process`, else forward.
- **Parametrized handler** (slide 9): there is a single `handle(FeedbackMessage)`
  method and the request type is carried inside the message, instead of a
  separate method per type.
- **Chain built by the client** (slide 10): `Main` wires the handlers with
  `setNext(...)`. This keeps the concrete handlers loosely coupled (no handler
  hard-codes its successor).
- If no handler can process a message, the base class reports it as
  `[Unhandled]` — the slides note this is a real possibility in a chain.

## What each handler does

| Handler | Category | Behavior |
|---------|----------|----------|
| `CompensationClaimHandler` | Compensation claims | Registers a claim ID, approves it, or escalates to legal if the text mentions a lawsuit. |
| `ContactRequestHandler` | Contact requests | Routes the customer to Billing, Sales or Customer Support based on keywords. |
| `SuggestionHandler` | Suggestions | Logs the suggestion and assigns HIGH / NORMAL / LOW priority. |
| `GeneralFeedbackHandler` | General feedback | Runs a simple sentiment analysis and composes a matching reply. |

## Sample output

```
(2) Incoming [COMPENSATION_CLAIM] from mikko@example.com: "If this is not resolved I will start a lawsuit."
[CompensationClaimHandler] Claim CLM-0002 registered from mikko@example.com
   Decision: ESCALATED to the legal department for review.
   Acknowledgement email sent to mikko@example.com

(5) Incoming [SUGGESTION] from dev@example.com: "There is a security bug in the login page."
[SuggestionHandler] Suggestion SUG-0001 logged to the backlog (priority: HIGH).
   Thank-you note sent to dev@example.com
```

## How to compile and run

```bash
javac *.java
java Main
```

## Files

- `FeedbackType.java` — enum of the feedback categories.
- `FeedbackMessage.java` — the request object (type, content, sender email).
- `FeedbackHandler.java` — the abstract Handler (holds `next`, default `handle()`).
- `CompensationClaimHandler.java`, `ContactRequestHandler.java`, `SuggestionHandler.java`, `GeneralFeedbackHandler.java` — the concrete handlers.
- `Main.java` — the client that builds the chain and routes the messages.
