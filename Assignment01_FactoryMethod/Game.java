/**
 * Client of the Factory Method pattern.
 *
 * The Game class owns a factory method {@link #createMap(String, int, int)}
 * that returns either a {@link CityMap} or a {@link WildernessMap}. The
 * main() method creates the desired map and displays it, without depending
 * on any concrete tile class.
 */
public class Game {

    /**
     * Factory method that creates the desired kind of Map.
     *
     * @param type "city" or "wilderness" (case-insensitive)
     * @param rows number of rows in the map
     * @param cols number of columns in the map
     * @return the requested concrete {@link Map}
     */
    public static Map createMap(String type, int rows, int cols) {
        if ("city".equalsIgnoreCase(type)) {
            return new CityMap(rows, cols);
        } else if ("wilderness".equalsIgnoreCase(type)) {
            return new WildernessMap(rows, cols);
        }
        throw new IllegalArgumentException("Unknown map type: " + type);
    }

    public static void main(String[] args) {
        final int rows = 8;
        final int cols = 16;

        System.out.println("City map (R=road, F=forest, B=building):");
        Map city = createMap("city", rows, cols);
        city.display();

        System.out.println();

        System.out.println("Wilderness map (S=swamp, W=water, F=forest):");
        Map wilderness = createMap("wilderness", rows, cols);
        wilderness.display();
    }
}
