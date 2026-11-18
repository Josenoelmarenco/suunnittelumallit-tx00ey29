package rpgmap;

import javafx.scene.canvas.GraphicsContext;

/**
 * Renders a {@link Map} on a JavaFX canvas using the shared tile graphics.
 *
 * For every cell it asks the factory for the graphic of the tile's type
 * (intrinsic, shared) and draws it at the cell's position (extrinsic,
 * computed from the row and column at drawing time).
 */
public class MapRenderer {

    private final TileGraphicFactory factory;
    private final double tileSize;

    public MapRenderer(TileGraphicFactory factory, double tileSize) {
        this.factory = factory;
        this.tileSize = tileSize;
    }

    public double getTileSize() {
        return tileSize;
    }

    public void render(Map map, GraphicsContext gc) {
        for (int row = 0; row < map.getRows(); row++) {
            for (int col = 0; col < map.getCols(); col++) {
                Tile tile = map.getTile(row, col);
                TileGraphic graphic = factory.getGraphic(tile.getType());
                graphic.draw(gc, col * tileSize, row * tileSize, tileSize);
            }
        }
    }
}
