package rpgmap;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.InputStream;

/**
 * Flyweight: the graphic of ONE tile type, shared by every tile of that type.
 *
 * Intrinsic state (stored here, shared): the tile type and its image.
 * Extrinsic state (NOT stored, passed in): where the tile is drawn, given to
 * {@link #draw(GraphicsContext, double, double, double)} as coordinates.
 *
 * Instances are created only by {@link TileGraphicFactory}.
 */
public final class TileGraphic {

    private final String type;
    private final Image image;

    /** Loads the image of the given tile type from /tiles/&lt;type&gt;.png. */
    TileGraphic(String type) {
        this.type = type;
        String path = "/tiles/" + type + ".png";
        InputStream stream = TileGraphic.class.getResourceAsStream(path);
        if (stream == null) {
            throw new IllegalArgumentException("No graphic for tile type '" + type + "' (" + path + ")");
        }
        this.image = new Image(stream);
        System.out.println("TileGraphic created for '" + type + "' (image loaded once)");
    }

    public String getType() {
        return type;
    }

    /**
     * Draws the shared image at a position supplied by the caller.
     *
     * @param x    left edge in pixels (extrinsic)
     * @param y    top edge in pixels (extrinsic)
     * @param size width and height of the cell in pixels
     */
    public void draw(GraphicsContext gc, double x, double y, double size) {
        gc.drawImage(image, x, y, size, size);
    }
}
