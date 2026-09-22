import java.util.Scanner;

/**
 * Client (State design pattern).
 *
 * The client only ever talks to the Context (GameCharacter); it never touches
 * the concrete states directly. Before each turn it prints the current status
 * and the actions allowed in the current state, then reads and dispatches a
 * command. The loop ends when the character reaches the Master level.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Game Character Development System ===");
        System.out.print("Enter your character's name: ");
        String name = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        if (name.isEmpty()) {
            name = "Hero";
        }

        GameCharacter character = new GameCharacter(name);
        System.out.println("\nWelcome, " + name + "! Your journey begins as a Novice.");

        // --- Main game loop -------------------------------------------------
        while (!character.isGameOver()) {
            System.out.println("---------------------------------------------");
            System.out.println("Status : " + character.status());
            System.out.println("Actions: " + character.availableActions() + "  (or 'quit')");
            System.out.print("> ");

            if (!scanner.hasNextLine()) {
                break;   // end of input (e.g. piped input finished)
            }
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "train":
                    character.train();
                    break;
                case "meditate":
                    character.meditate();
                    break;
                case "fight":
                    character.fight();
                    break;
                case "quit":
                    System.out.println("You leave your training unfinished. Goodbye!");
                    scanner.close();
                    return;
                case "":
                    break;   // empty line: just refresh the status
                default:
                    System.out.println("Unknown action: '" + command
                            + "'. Available: " + character.availableActions());
            }
            System.out.println();
        }

        // --- Victory screen -------------------------------------------------
        System.out.println("=============================================");
        System.out.println("Congratulations, " + character.getName() + "!");
        System.out.println("Final status: " + character.status());
        System.out.println("You have reached the MASTER level. The game is over.");
        System.out.println("=============================================");
        scanner.close();
    }
}
