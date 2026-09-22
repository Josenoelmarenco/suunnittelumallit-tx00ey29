/**
 * Framework class (Template Method design pattern).
 *
 * This class is provided by the assignment and is used AS IS (not modified).
 * play() is the TEMPLATE METHOD: it defines the fixed skeleton of any game
 * (initialize, loop over turns until the game ends, then show the winner) while
 * leaving the concrete steps to the subclass. It is declared final so that the
 * overall algorithm cannot be overridden - only the individual steps can.
 */
public abstract class Game {

    public final void play(int numberOfPlayers) {
        // a template method specifying a generic game
        initializeGame(numberOfPlayers);
        int playerInTurn = 0;
        while (!endOfGame()) {
            playSingleTurn(playerInTurn);
            playerInTurn = ++playerInTurn % numberOfPlayers;
        }
        displayWinner();
    }

    public abstract void initializeGame(int numberOfPlayers);
    public abstract boolean endOfGame();
    public abstract void playSingleTurn(int player);
    public abstract void displayWinner();
}
