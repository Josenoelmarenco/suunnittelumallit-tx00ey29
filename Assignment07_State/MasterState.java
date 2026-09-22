/**
 * Concrete State: MASTER (the final level).
 *
 * Reaching this state ends the game. No further actions have any effect, and
 * isGameOver() returns true so the main loop stops.
 */
public class MasterState implements CharacterState {

    @Override
    public void train(GameCharacter character) {
        System.out.println("You are already a Master. There is nothing left to train.");
    }

    @Override
    public void meditate(GameCharacter character) {
        System.out.println("You are already a Master, at perfect peace.");
    }

    @Override
    public void fight(GameCharacter character) {
        System.out.println("You are already a Master. Your journey is complete.");
    }

    @Override
    public String getLevelName() {
        return "Master";
    }

    @Override
    public String availableActions() {
        return "(none - the game is over)";
    }

    @Override
    public boolean isGameOver() {
        return true;
    }
}
