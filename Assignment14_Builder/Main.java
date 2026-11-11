/**
 * Client: demonstrates building different computers with ONE director and
 * different builders, grades and construction recipes.
 *
 * For the interactive configurator (additional challenge) run ConfiguratorUI.
 */
public class Main {
    public static void main(String[] args) {
        ComputerDirector director = new ComputerDirector(new GamingComputerBuilder(Grade.STANDARD));

        System.out.println("1) Same director, different builders\n");
        System.out.println(director.constructComputer());

        director.setBuilder(new OfficeComputerBuilder(Grade.STANDARD));
        System.out.println(director.constructComputer());

        System.out.println("2) Same builder type, different component grades\n");
        director.setBuilder(new GamingComputerBuilder(Grade.PREMIUM));
        System.out.println(director.constructComputer());

        director.setBuilder(new OfficeComputerBuilder(Grade.BUDGET));
        System.out.println(director.constructComputer());

        System.out.println("3) Same builder, different recipe (barebone: no OS, no accessories)\n");
        director.setBuilder(new GamingComputerBuilder(Grade.BUDGET));
        System.out.println(director.constructBareboneComputer());

        System.out.println("Run 'java ConfiguratorUI' to choose your own components.");
    }
}
