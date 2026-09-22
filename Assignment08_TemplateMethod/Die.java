import java.util.Random;

/**
 * A simple die (composed helper class used by PigGame).
 *
 * Keeping the die in its own class is an example of "composing other classes
 * that your subclass uses", as the assignment suggests, instead of putting
 * everything in the game subclass.
 */
public class Die {

    private final Random random = new Random();
    private final int sides;

    public Die() {
        this(6);
    }

    public Die(int sides) {
        this.sides = sides;
    }

    /** Returns a random value in the range [1, sides]. */
    public int roll() {
        return random.nextInt(sides) + 1;
    }
}
