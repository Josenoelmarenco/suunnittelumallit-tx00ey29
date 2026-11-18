package rpgmap;

import java.util.HashMap;

/**
 * Flyweight factory: creates each tile graphic the first time it is requested
 * and returns the SAME object every later time.
 *
 * However many tiles a map has, at most one {@link TileGraphic} exists per tile
 * type. This factory only manages graphics for rendering; creating the Tile
 * objects themselves is still the job of the Factory Method in {@link Map}.
 */
public class TileGraphicFactory {

    private final HashMap<String, TileGraphic> graphics = new HashMap<>();
    private int requests = 0;

    /** @return the shared graphic for the given tile type, created on first use. */
    public TileGraphic getGraphic(String type) {
        requests++;
        return graphics.computeIfAbsent(type, TileGraphic::new);
    }

    /** @return how many distinct graphic objects have been created so far. */
    public int getCreatedCount() {
        return graphics.size();
    }

    /** @return how many times a graphic has been requested (one per tile drawn). */
    public int getRequestCount() {
        return requests;
    }
}
