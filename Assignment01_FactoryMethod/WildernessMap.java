/**
 * A concrete Map made of wild terrain: swamp, water and forest tiles.
 * Overrides the factory method to decide which concrete tiles are produced.
 */
public class WildernessMap extends Map {

    public WildernessMap(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    protected Tile createTile() {
        switch (random.nextInt(3)) {
            case 0:  return new SwampTile();
            case 1:  return new WaterTile();
            default: return new ForestTile();
        }
    }
}
