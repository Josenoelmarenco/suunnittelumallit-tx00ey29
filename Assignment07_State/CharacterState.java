/**
 * State (State design pattern).
 *
 * Declares the state-specific operations that every proficiency level must
 * provide. Each concrete level (Novice, Intermediate, Expert, Master)
 * implements this interface and gives its own behavior to the three actions.
 *
 * The GameCharacter (the Context) never decides what an action does; it simply
 * forwards the call to its current CharacterState. That is the essence of the
 * State pattern: the object's behavior changes when its internal state changes.
 */
public interface CharacterState {

    /** Train: increases experience points. Available at every level. */
    void train(GameCharacter character);

    /** Meditate: restores health points. Available from Intermediate onwards. */
    void meditate(GameCharacter character);

    /** Fight: costs health but grants a lot of experience. Available from Expert. */
    void fight(GameCharacter character);

    /** Human-readable name of this level, shown in the status line. */
    String getLevelName();

    /** Comma-separated list of the actions allowed in this state. */
    String availableActions();

    /** True only for the final state (Master), which ends the game. */
    default boolean isGameOver() {
        return false;
    }
}
