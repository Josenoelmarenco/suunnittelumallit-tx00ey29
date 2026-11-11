import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Builder for the user interface (additional challenge).
 *
 * The user chooses each component first; the build steps then install exactly
 * those choices. The director drives it like any other builder.
 */
public class CustomComputerBuilder implements ComputerBuilder {

    private String processor;
    private int ramGb;
    private String hardDrive;
    private String graphicsCard;
    private String operatingSystem;
    private final List<String> accessories = new ArrayList<>();
    private Computer computer;

    public CustomComputerBuilder() {
        this.computer = createEmptyComputer();
    }

    public void chooseProcessor(String processor) { this.processor = processor; }
    public void chooseRam(int ramGb) { this.ramGb = ramGb; }
    public void chooseHardDrive(String hardDrive) { this.hardDrive = hardDrive; }
    public void chooseGraphicsCard(String graphicsCard) { this.graphicsCard = graphicsCard; }
    public void chooseOperatingSystem(String operatingSystem) { this.operatingSystem = operatingSystem; }
    public void chooseAccessory(String accessory) { accessories.add(accessory); }

    @Override
    public void reset() {
        computer = createEmptyComputer();
    }

    private Computer createEmptyComputer() {
        return new Computer("Custom Computer");
    }

    @Override
    public void buildProcessor() {
        if (processor != null) computer.setProcessor(processor);
    }

    @Override
    public void buildRAM() {
        computer.setRamGb(ramGb);
    }

    @Override
    public void buildHardDrive() {
        if (hardDrive != null) computer.setHardDrive(hardDrive);
    }

    @Override
    public void buildGraphicsCard() {
        if (graphicsCard != null) computer.setGraphicsCard(graphicsCard);
    }

    @Override
    public void buildOperatingSystem() {
        if (operatingSystem != null) computer.setOperatingSystem(operatingSystem);
    }

    @Override
    public void buildAccessories() {
        for (String accessory : accessories) {
            computer.addAccessory(accessory);
        }
    }

    @Override
    public Computer getComputer() {
        Computer result = computer;
        reset();
        return result;
    }
}
