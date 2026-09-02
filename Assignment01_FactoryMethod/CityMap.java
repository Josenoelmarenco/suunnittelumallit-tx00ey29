/**
 * A concrete Map made of city terrain: road, forest and building tiles.
 * Overrides the factory method to decide which concrete tiles are produced.
 */
public class CityMap extends Map {

    public CityMap(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    protected Tile createTile() {
        switch (random.nextInt(3)) {
            case 0:  return new RoadTile();
            case 1:  return new ForestTile();
            default: return new BuildingTile();
        }
    }
}
