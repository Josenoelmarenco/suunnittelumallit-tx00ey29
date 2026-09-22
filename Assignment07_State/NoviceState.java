/**
 * Concrete State: NOVICE (the initial level).
 *
 * A novice can ONLY train. Meditating and fighting are not yet available, so
 * those methods just explain why. When the experience reaches the threshold,
 * this state promotes the character to the Intermediate level.
 */
public class NoviceState implements CharacterState {

    private static final int TRAIN_XP   = 25;    // experience gained per training
    private static final int ADVANCE_XP = 100;   // experience needed to advance

    @Override
    public void train(GameCharacter character) {
        character.addExperience(TRAIN_XP);
        System.out.println("You train hard as a novice. (+" + TRAIN_XP + " XP)");

        // The concrete state is responsible for the transition.
        if (character.getExperience() >= ADVANCE_XP) {
            character.setState(new IntermediateState());
            System.out.println(">>> LEVEL UP! You are now INTERMEDIATE. You can meditate.");
        }
    }

    @Override
    public void meditate(GameCharacter character) {
        System.out.println("A novice cannot meditate yet. Keep training to advance.");
    }

    @Override
    public void fight(GameCharacter character) {
        System.out.println("A novice is too inexperienced to fight. Keep training.");
    }

    @Override
    public String getLevelName() {
        return "Novice";
    }

    @Override
    public String availableActions() {
        return "train";
    }
}
