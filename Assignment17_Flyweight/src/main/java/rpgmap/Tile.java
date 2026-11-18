package rpgmap;

/**
 * Abstract product of the Factory Method pattern (from Assignment 01).
 *
 * Flyweight refactoring: a Tile holds ONLY intrinsic state, the data that is
 * the same for every tile of its kind (its type and its character). It has no
 * position: where a tile lies is extrinsic state, kept by the {@link Map} grid
 * and passed to the renderer at drawing time.
 */
public abstract class Tile {

    /** @return the single character that represents this tile on the map. */
    public abstract char getCharacter();

    /**
     * @return the tile type as a lower-case string, e.g. "swamp". It is also the
     *         key the {@link TileGraphicFactory} uses to find the shared graphic.
     */
    public abstract String getType();

    /**
     * Optional extra behaviour a tile could perform (for example, an event
     * triggered when the player steps on it). Not used in this assignment.
     */
    public abstract void action();
}
