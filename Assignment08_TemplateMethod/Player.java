/**
 * A game player (composed helper class used by PigGame).
 *
 * Holds the player's name and accumulated score. The game state lives partly
 * here and partly in the PigGame subclass, as allowed by the assignment.
 */
public class Player {

    private final String name;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }
}
