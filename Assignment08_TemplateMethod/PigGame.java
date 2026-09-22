import java.util.Scanner;

/**
 * Concrete game (Template Method design pattern).
 *
 * PigGame extends the Game framework and fills in the four abstract steps that
 * play() calls. It never overrides play() itself - the skeleton of the game is
 * fixed by the framework; only the steps change.
 *
 * PIG (a classic dice game):
 *   - On your turn you roll a die repeatedly.
 *   - Each roll of 2-6 is added to your "turn total".
 *   - You may hold to bank the turn total into your score, or roll again.
 *   - If you roll a 1, you lose the whole turn total and score nothing.
 *   - The first player to reach the target score wins.
 */
public class PigGame extends Game {

    private final int targetScore;
    private final Die die = new Die();
    private final Scanner scanner = new Scanner(System.in);

    private Player[] players;
    private Player winner;

    public PigGame() {
        this(100);   // standard Pig target
    }

    public PigGame(int targetScore) {
        this.targetScore = targetScore;
    }

    // --- Template Method steps -------------------------------------------

    @Override
    public void initializeGame(int numberOfPlayers) {
        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player("Player " + (i + 1));
        }
        winner = null;

        System.out.println("========================================");
        System.out.println("            PIG - a dice game           ");
        System.out.println("========================================");
        System.out.println("First to reach " + targetScore + " points wins.");
        System.out.println("Roll a 1 and you lose everything gained this turn!");
        System.out.println("Players: " + numberOfPlayers);
    }

    @Override
    public boolean endOfGame() {
        // The game ends as soon as somebody has reached the target score.
        return winner != null;
    }

    @Override
    public void playSingleTurn(int player) {
        Player current = players[player];
        System.out.println("\n---------------------------------------------");
        System.out.println(current.getName() + "'s turn (current score: "
                + current.getScore() + ")");

        int turnTotal = 0;
        boolean turnOver = false;

        while (!turnOver) {
            int roll = die.roll();

            if (roll == 1) {
                System.out.println("  Rolled a 1! Turn total lost. No points this turn.");
                turnOver = true;   // busted: turn ends, turnTotal is discarded
            } else {
                turnTotal += roll;
                int potential = current.getScore() + turnTotal;
                System.out.println("  Rolled a " + roll + ".  Turn total: " + turnTotal
                        + "  (banking now would give " + potential + ")");

                if (potential >= targetScore) {
                    System.out.println("  Holding now reaches " + targetScore + " and WINS!");
                }

                System.out.print("  Roll again? (y = roll, n = hold): ");
                if (askRollAgain()) {
                    continue;      // roll again
                }
                // hold: bank the turn total
                current.addScore(turnTotal);
                System.out.println("  " + current.getName() + " holds. New score: "
                        + current.getScore());
                turnOver = true;
            }
        }

        // The subclass is responsible for detecting the end condition.
        if (current.getScore() >= targetScore) {
            winner = current;
        }
    }

    @Override
    public void displayWinner() {
        System.out.println("\n========================================");
        System.out.println("               GAME OVER                ");
        System.out.println("========================================");
        if (winner != null) {
            System.out.println("WINNER: " + winner.getName() + " with "
                    + winner.getScore() + " points!");
        }
        System.out.println("\nFinal scores:");
        for (Player p : players) {
            System.out.println("  " + p.getName() + ": " + p.getScore());
        }
    }

    // --- Helper ----------------------------------------------------------

    /**
     * Reads the player's decision. Returns true to roll again, false to hold.
     * Anything other than "y" means hold; if there is no input at all (for
     * example when the game is run non-interactively), the player holds so the
     * game always terminates.
     */
    private boolean askRollAgain() {
        if (scanner.hasNextLine()) {
            String answer = scanner.nextLine().trim().toLowerCase();
            return answer.equals("y");
        }
        System.out.println("(no input - holding automatically)");
        return false;
    }
}
