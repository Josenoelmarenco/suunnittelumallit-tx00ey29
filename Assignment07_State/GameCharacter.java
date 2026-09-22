/**
 * Context (State design pattern).
 *
 * Holds the character's data (name, experience, health) and a reference to the
 * current CharacterState. All three actions are delegated to that state, so the
 * same call (for example train()) behaves differently depending on the level.
 *
 * The concrete states are responsible for the transitions: when a threshold is
 * reached, a state calls setState(...) to move the character to the next level.
 */
public class GameCharacter {

    private final String name;
    private int experience;
    private int health;
    private final int maxHealth;
    private CharacterState state;

    public GameCharacter(String name) {
        this.name = name;
        this.experience = 0;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.state = new NoviceState();   // initial state: everyone starts as a Novice
    }

    // --- Actions delegated to the current state ---------------------------

    public void train() {
        state.train(this);
    }

    public void meditate() {
        state.meditate(this);
    }

    public void fight() {
        state.fight(this);
    }

    // --- State management (used by the concrete states) -------------------

    public void setState(CharacterState state) {
        this.state = state;
    }

    public CharacterState getState() {
        return state;
    }

    public boolean isGameOver() {
        return state.isGameOver();
    }

    // --- Attribute helpers (used by the concrete states) ------------------

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public void addExperience(int amount) {
        experience += amount;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    /** Adds (or subtracts) health, keeping it within the range [0, maxHealth]. */
    public void addHealth(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
        if (health < 0) {
            health = 0;
        }
    }

    // --- Presentation helpers --------------------------------------------

    public String status() {
        return String.format("%s | Level: %s | XP: %d | HP: %d/%d",
                name, state.getLevelName(), experience, health, maxHealth);
    }

    public String availableActions() {
        return state.availableActions();
    }
}
