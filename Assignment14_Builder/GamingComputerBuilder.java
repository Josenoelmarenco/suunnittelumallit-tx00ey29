/**
 * Concrete Builder: assembles a gaming computer. Powerful CPU and GPU, lots of RAM,
 * fast storage and gaming accessories. The grade selects how high-end the parts are.
 */
public class GamingComputerBuilder implements ComputerBuilder {

    private final Grade grade;
    private Computer computer;

    public GamingComputerBuilder(Grade grade) {
        this.grade = grade;
        this.computer = createEmptyComputer();
    }

    @Override
    public void reset() {
        computer = createEmptyComputer();
    }

    private Computer createEmptyComputer() {
        return new Computer("Gaming Computer (" + grade + ")");
    }

    @Override
    public void buildProcessor() {
        computer.setProcessor(switch (grade) {
            case BUDGET -> "AMD Ryzen 5 7600";
            case STANDARD -> "AMD Ryzen 7 7800X3D";
            case PREMIUM -> "AMD Ryzen 9 9950X3D";
        });
    }

    @Override
    public void buildRAM() {
        computer.setRamGb(switch (grade) {
            case BUDGET -> 16;
            case STANDARD -> 32;
            case PREMIUM -> 64;
        });
    }

    @Override
    public void buildHardDrive() {
        computer.setHardDrive(switch (grade) {
            case BUDGET -> "1 TB NVMe SSD";
            case STANDARD -> "2 TB NVMe SSD";
            case PREMIUM -> "4 TB NVMe SSD (PCIe 5.0)";
        });
    }

    @Override
    public void buildGraphicsCard() {
        computer.setGraphicsCard(switch (grade) {
            case BUDGET -> "NVIDIA GeForce RTX 5060";
            case STANDARD -> "NVIDIA GeForce RTX 5070 Ti";
            case PREMIUM -> "NVIDIA GeForce RTX 5090";
        });
    }

    @Override
    public void buildOperatingSystem() {
        computer.setOperatingSystem("Windows 11 Home");
    }

    @Override
    public void buildAccessories() {
        computer.addAccessory("RGB mechanical keyboard");
        computer.addAccessory("Gaming mouse");
        if (grade == Grade.PREMIUM) {
            computer.addAccessory("27\" 240 Hz OLED monitor");
            computer.addAccessory("Wireless headset");
        }
    }

    @Override
    public Computer getComputer() {
        Computer result = computer;
        reset();
        return result;
    }
}
