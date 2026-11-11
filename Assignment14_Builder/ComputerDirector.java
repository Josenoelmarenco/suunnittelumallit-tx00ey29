/**
 * Director: knows the ORDER of the construction steps, but not the concrete parts.
 *
 * The same director works with any {@link ComputerBuilder}; swapping the builder
 * changes the resulting computer without changing this class.
 */
public class ComputerDirector {

    private ComputerBuilder builder;

    public ComputerDirector(ComputerBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(ComputerBuilder builder) {
        this.builder = builder;
    }

    /** Full configuration: every component plus the accessories. */
    public Computer constructComputer() {
        builder.reset();
        builder.buildProcessor();
        builder.buildRAM();
        builder.buildHardDrive();
        builder.buildGraphicsCard();
        builder.buildOperatingSystem();
        builder.buildAccessories();
        return builder.getComputer();
    }

    /** A bare system for resellers: core hardware only, no OS and no accessories. */
    public Computer constructBareboneComputer() {
        builder.reset();
        builder.buildProcessor();
        builder.buildRAM();
        builder.buildHardDrive();
        builder.buildGraphicsCard();
        return builder.getComputer();
    }
}
