import java.util.Random;

/**
 * Abstract creator of the Factory Method pattern.
 *
 * A Map is a rectangular grid of {@link Tile} objects. The concrete subclass
 * decides WHICH tiles populate the grid through the factory method
 * {@link #createTile()} — the base class never references a concrete tile
 * type, so new terrain types can be added without changing this class.
 */
public abstract class Map {

    protected final int rows;
    protected final int cols;
    private   final Tile[][] tiles;
    protected final Random random = new Random();

    protected Map(int rows, int cols) {
        this.rows  = rows;
        this.cols  = cols;
        this.tiles = new Tile[rows][cols];
        fill();
    }

    /**
     * The factory method. Creates a new tile of a random type honoring the
     * chosen map type. Implemented by each concrete Map subclass.
     *
     * @return a freshly created {@link Tile}.
     */
    protected abstract Tile createTile();

    /** Populates every cell of the grid using the factory method. */
    private void fill() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                tiles[r][c] = createTile();
            }
        }
    }

    /**
     * Displays the map as a matrix of characters. This method is completely
     * agnostic of the concrete tile types: it only asks each tile for the
     * character that represents it.
     */
    public void display() {
        for (int r = 0; r < rows; r++) {
            StringBuilder line = new StringBuilder(cols);
            for (int c = 0; c < cols; c++) {
                line.append(tiles[r][c].getCharacter());
            }
            System.out.println(line);
        }
    }
}
