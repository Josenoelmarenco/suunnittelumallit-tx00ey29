package rpgmap;

import java.util.Random;

/**
 * Abstract creator of the Factory Method pattern (from Assignment 01).
 *
 * A Map is a rectangular grid of {@link Tile} objects. The concrete subclass
 * decides WHICH tiles populate the grid through the factory method
 * {@link #createTile()}.
 *
 * Flyweight refactoring: the grid is where the extrinsic state lives. The row
 * and column of a tile are its indices in the grid; the tile object itself
 * never stores them. Read access was added so that a renderer can walk the
 * grid without knowing the concrete tile types.
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

    /** @return a human-readable name of the map type, e.g. "City". */
    public abstract String getName();

    /** Populates every cell of the grid using the factory method. */
    private void fill() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                tiles[r][c] = createTile();
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /** @return the tile at the given position (row and column are the extrinsic state). */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /** Displays the map as a matrix of characters (the text output of Assignment 01). */
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
