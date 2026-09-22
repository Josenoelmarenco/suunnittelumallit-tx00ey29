/**
 * Client (Template Method design pattern).
 *
 * The client works with the game through the Game framework type and simply
 * calls the template method play(). All the flow control lives in Game.play();
 * the concrete behavior comes from PigGame.
 *
 * Optional command-line arguments:
 *   args[0] = number of players (minimum 2, default 2)
 *   args[1] = target score       (default 100)
 */
public class Main {

    public static void main(String[] args) {
        int numberOfPlayers = 2;
        int targetScore = 100;

        if (args.length > 0) {
            try {
                numberOfPlayers = Math.max(2, Integer.parseInt(args[0]));
            } catch (NumberFormatException ignored) {
                // keep default
            }
        }
        if (args.length > 1) {
            try {
                targetScore = Math.max(1, Integer.parseInt(args[1]));
            } catch (NumberFormatException ignored) {
                // keep default
            }
        }

        Game game = new PigGame(targetScore);   // used through the framework type
        game.play(numberOfPlayers);             // the template method drives everything
    }
}
