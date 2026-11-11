import java.util.ArrayList;
import java.util.List;

/**
 * Product: the final computer configuration.
 *
 * A Computer is assembled step by step by a {@link ComputerBuilder}. Every component
 * starts with a "not installed" value, so a director that skips a step still produces
 * a valid, printable computer.
 */
public class Computer {

    private static final String NOT_INSTALLED = "(not installed)";

    private final String type;
    private String processor = NOT_INSTALLED;
    private int ramGb = 0;
    private String hardDrive = NOT_INSTALLED;
    private String graphicsCard = NOT_INSTALLED;
    private String operatingSystem = NOT_INSTALLED;
    private final List<String> accessories = new ArrayList<>();

    public Computer(String type) {
        this.type = type;
    }

    public void setProcessor(String processor) { this.processor = processor; }
    public void setRamGb(int ramGb) { this.ramGb = ramGb; }
    public void setHardDrive(String hardDrive) { this.hardDrive = hardDrive; }
    public void setGraphicsCard(String graphicsCard) { this.graphicsCard = graphicsCard; }
    public void setOperatingSystem(String operatingSystem) { this.operatingSystem = operatingSystem; }
    public void addAccessory(String accessory) { accessories.add(accessory); }

    public String getType() { return type; }
    public String getProcessor() { return processor; }
    public int getRamGb() { return ramGb; }
    public String getHardDrive() { return hardDrive; }
    public String getGraphicsCard() { return graphicsCard; }
    public String getOperatingSystem() { return operatingSystem; }
    public List<String> getAccessories() { return List.copyOf(accessories); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(type).append(" ===\n");
        sb.append(String.format("  %-17s %s%n", "Processor:", processor));
        sb.append(String.format("  %-17s %s%n", "RAM:", ramGb > 0 ? ramGb + " GB" : NOT_INSTALLED));
        sb.append(String.format("  %-17s %s%n", "Hard drive:", hardDrive));
        sb.append(String.format("  %-17s %s%n", "Graphics card:", graphicsCard));
        sb.append(String.format("  %-17s %s%n", "Operating system:", operatingSystem));
        sb.append(String.format("  %-17s %s%n", "Accessories:",
                accessories.isEmpty() ? "none" : String.join(", ", accessories)));
        return sb.toString();
    }
}
