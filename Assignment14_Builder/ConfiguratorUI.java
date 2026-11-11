import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Additional challenge: a console user interface where the user selects the
 * components and sees the final configuration. The choices are handed to a
 * {@link CustomComputerBuilder}, and the usual {@link ComputerDirector} builds it.
 */
public class ConfiguratorUI {

    private static final List<String> PROCESSORS = List.of(
            "Intel Core i5-14500", "Intel Core i7-14700", "AMD Ryzen 7 7800X3D", "AMD Ryzen 9 9950X3D");
    private static final List<String> RAM_OPTIONS = List.of("8", "16", "32", "64");
    private static final List<String> HARD_DRIVES = List.of(
            "512 GB SSD", "1 TB NVMe SSD", "2 TB NVMe SSD", "4 TB NVMe SSD (PCIe 5.0)");
    private static final List<String> GRAPHICS_CARDS = List.of(
            "Integrated graphics", "NVIDIA GeForce RTX 5060", "NVIDIA GeForce RTX 5070 Ti", "NVIDIA GeForce RTX 5090");
    private static final List<String> OPERATING_SYSTEMS = List.of(
            "Windows 11 Home", "Windows 11 Pro", "Ubuntu 24.04 LTS", "No operating system");
    private static final List<String> ACCESSORIES = List.of(
            "Keyboard", "Mouse", "Monitor", "Webcam", "Headset");

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new ConfiguratorUI().run();
    }

    private void run() {
        System.out.println("=== Computer Configurator ===");
        CustomComputerBuilder builder = new CustomComputerBuilder();

        builder.chooseProcessor(choose("Processor", PROCESSORS));
        builder.chooseRam(Integer.parseInt(choose("RAM (GB)", RAM_OPTIONS)));
        builder.chooseHardDrive(choose("Hard drive", HARD_DRIVES));
        builder.chooseGraphicsCard(choose("Graphics card", GRAPHICS_CARDS));
        String os = choose("Operating system", OPERATING_SYSTEMS);
        if (!os.equals("No operating system")) {
            builder.chooseOperatingSystem(os);
        }
        for (String accessory : chooseMany("Accessories", ACCESSORIES)) {
            builder.chooseAccessory(accessory);
        }

        ComputerDirector director = new ComputerDirector(builder);
        System.out.println("\nYour configuration:\n");
        System.out.println(director.constructComputer());
    }

    /** Shows a numbered menu and repeats the question until a valid number is entered. */
    private String choose(String title, List<String> options) {
        printMenu(title, options);
        while (true) {
            System.out.print("Choose 1-" + options.size() + ": ");
            String line = readLine();
            try {
                int choice = Integer.parseInt(line.trim());
                if (choice >= 1 && choice <= options.size()) {
                    return options.get(choice - 1);
                }
            } catch (NumberFormatException e) {
                // not a number: fall through and ask again
            }
            System.out.println("Invalid choice, try again.");
        }
    }

    /** Lets the user pick several options separated by commas, or none with Enter. */
    private List<String> chooseMany(String title, List<String> options) {
        printMenu(title, options);
        while (true) {
            System.out.print("Choose numbers separated by commas (Enter = none): ");
            String line = readLine().trim();
            if (line.isEmpty()) {
                return List.of();
            }
            try {
                return parseMany(line, options);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid choice, try again.");
            }
        }
    }

    private List<String> parseMany(String line, List<String> options) {
        List<String> chosen = new ArrayList<>();
        for (String part : line.split(",")) {
            int choice = Integer.parseInt(part.trim());   // NumberFormatException is an IllegalArgumentException
            if (choice < 1 || choice > options.size()) {
                throw new IllegalArgumentException("Out of range: " + choice);
            }
            String option = options.get(choice - 1);
            if (!chosen.contains(option)) {
                chosen.add(option);
            }
        }
        return chosen;
    }

    private void printMenu(String title, List<String> options) {
        System.out.println("\n" + title + ":");
        for (int i = 0; i < options.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + options.get(i));
        }
    }

    /** Reads one line; stops the program cleanly if the input ends (e.g. Ctrl+D). */
    private String readLine() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nNo more input. Configurator closed.");
            System.exit(0);
        }
        return scanner.nextLine();
    }
}
