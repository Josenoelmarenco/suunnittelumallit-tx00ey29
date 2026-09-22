/**
 * Concrete State: INTERMEDIATE.
 *
 * The character can now train AND meditate. Meditating restores health.
 * Fighting is still unavailable. When the experience threshold is reached,
 * this state promotes the character to the Expert level.
 */
public class IntermediateState implements CharacterState {

    private static final int TRAIN_XP    = 25;   // experience gained per training
    private static final int MEDITATE_HP = 20;   // health restored per meditation
    private static final int ADVANCE_XP  = 250;  // total experience needed to advance

    @Override
    public void train(GameCharacter character) {
        character.addExperience(TRAIN_XP);
        System.out.println("You train hard. (+" + TRAIN_XP + " XP)");
        checkAdvance(character);
    }

    @Override
    public void meditate(GameCharacter character) {
        character.addHealth(MEDITATE_HP);
        System.out.println("You meditate and restore your body. (+" + MEDITATE_HP
                + " HP, now " + character.getHealth() + "/" + character.getMaxHealth() + ")");
    }

    @Override
    public void fight(GameCharacter character) {
        System.out.println("You are not skilled enough to fight yet. Reach Expert first.");
    }

    private void checkAdvance(GameCharacter character) {
        if (character.getExperience() >= ADVANCE_XP) {
            character.setState(new ExpertState());
            System.out.println(">>> LEVEL UP! You are now EXPERT. You can fight.");
        }
    }

    @Override
    public String getLevelName() {
        return "Intermediate";
    }

    @Override
    public String availableActions() {
        return "train, meditate";
    }
}
