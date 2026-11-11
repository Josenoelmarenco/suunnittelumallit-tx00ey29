/**
 * Builder: declares one step for each part of a {@link Computer}.
 *
 * Each concrete builder decides WHICH component goes into each step; the director
 * decides WHICH steps are executed and in what order.
 */
public interface ComputerBuilder {

    /** Starts a new, empty computer. */
    void reset();

    void buildProcessor();

    void buildRAM();

    void buildHardDrive();

    void buildGraphicsCard();

    void buildOperatingSystem();

    /** Additional challenge: optional extras such as a keyboard or a monitor. */
    void buildAccessories();

    /** Returns the finished computer and prepares the builder for the next one. */
    Computer getComputer();
}
