/**
 * Concrete State: EXPERT.
 *
 * The character can train, meditate AND fight. Fighting costs health but grants
 * a large amount of experience, so the player must balance fighting (to gain XP
 * fast) with meditating (to stay alive). When the final threshold is reached,
 * this state promotes the character to the Master level, ending the game.
 */
public class ExpertState implements CharacterState {

    private static final int TRAIN_XP        = 25;   // experience gained per training
    private static final int MEDITATE_HP     = 20;   // health restored per meditation
    private static final int FIGHT_XP        = 50;   // experience gained per fight
    private static final int FIGHT_HP_COST   = 35;   // health lost per fight
    private static final int MIN_HP_TO_FIGHT = 35;   // cannot fight below this health
    private static final int ADVANCE_XP      = 500;  // total experience needed to advance

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
        // Extra rule to make the game interesting: you must be healthy enough to fight.
        if (character.getHealth() < MIN_HP_TO_FIGHT) {
            System.out.println("You are too weak to fight (need at least " + MIN_HP_TO_FIGHT
                    + " HP). Meditate first.");
            return;
        }
        character.addHealth(-FIGHT_HP_COST);
        character.addExperience(FIGHT_XP);
        System.out.println("You fight a fierce battle! (-" + FIGHT_HP_COST + " HP, +"
                + FIGHT_XP + " XP, now " + character.getHealth() + "/"
                + character.getMaxHealth() + " HP)");
        checkAdvance(character);
    }

    private void checkAdvance(GameCharacter character) {
        if (character.getExperience() >= ADVANCE_XP) {
            character.setState(new MasterState());
            System.out.println(">>> LEVEL UP! You have reached the MASTER level.");
        }
    }

    @Override
    public String getLevelName() {
        return "Expert";
    }

    @Override
    public String availableActions() {
        return "train, meditate, fight";
    }
}
