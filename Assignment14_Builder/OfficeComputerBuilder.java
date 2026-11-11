/**
 * Concrete Builder: assembles an office computer. Efficient CPU, moderate RAM,
 * integrated graphics and productivity accessories. The grade selects the part tier.
 */
public class OfficeComputerBuilder implements ComputerBuilder {

    private final Grade grade;
    private Computer computer;

    public OfficeComputerBuilder(Grade grade) {
        this.grade = grade;
        this.computer = createEmptyComputer();
    }

    @Override
    public void reset() {
        computer = createEmptyComputer();
    }

    private Computer createEmptyComputer() {
        return new Computer("Office Computer (" + grade + ")");
    }

    @Override
    public void buildProcessor() {
        computer.setProcessor(switch (grade) {
            case BUDGET -> "Intel Core i3-14100";
            case STANDARD -> "Intel Core i5-14500";
            case PREMIUM -> "Intel Core i7-14700";
        });
    }

    @Override
    public void buildRAM() {
        computer.setRamGb(switch (grade) {
            case BUDGET -> 8;
            case STANDARD -> 16;
            case PREMIUM -> 32;
        });
    }

    @Override
    public void buildHardDrive() {
        computer.setHardDrive(switch (grade) {
            case BUDGET -> "256 GB SSD";
            case STANDARD -> "512 GB SSD";
            case PREMIUM -> "1 TB SSD";
        });
    }

    @Override
    public void buildGraphicsCard() {
        computer.setGraphicsCard("Integrated Intel UHD Graphics");
    }

    @Override
    public void buildOperatingSystem() {
        computer.setOperatingSystem("Windows 11 Pro");
    }

    @Override
    public void buildAccessories() {
        computer.addAccessory("Wireless keyboard and mouse");
        computer.addAccessory("24\" Full HD monitor");
        if (grade != Grade.BUDGET) {
            computer.addAccessory("HD webcam");
        }
    }

    @Override
    public Computer getComputer() {
        Computer result = computer;
        reset();
        return result;
    }
}
