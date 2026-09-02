/**
 * Abstract product of the Factory Method pattern.
 *
 * Every concrete terrain tile knows the character that represents it on the
 * map and its human-readable type. The map's display logic depends only on
 * this abstraction, never on the concrete subclasses.
 */
public abstract class Tile {

    /** @return the single character that represents this tile on the map. */
    public abstract char getCharacter();

    /** @return the tile type as a lower-case string, e.g. "swamp". */
    public abstract String getType();

    /**
     * Optional extra behaviour a tile could perform (for example, an event
     * triggered when the player steps on it). It is NOT used in this
     * assignment; it only illustrates that concrete tiles may define
     * additional methods declared by the Tile abstraction.
     */
    public abstract void action();
}
