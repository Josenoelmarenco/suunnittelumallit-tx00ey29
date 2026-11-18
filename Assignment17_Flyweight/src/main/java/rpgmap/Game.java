package rpgmap;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Client and JavaFX application.
 *
 * Phase 1: generate the map with the Factory Method {@link #createMap(String, int, int)}
 * (unchanged from Assignment 01). Phase 2: render it on a canvas with
 * {@link MapRenderer}, which shares one {@link TileGraphic} per tile type
 * through {@link TileGraphicFactory} (Flyweight).
 *
 * The buttons generate new maps; the status line shows how many tiles were
 * drawn and how few graphic objects were needed.
 */
public class Game extends Application {

    private static final int ROWS = 18;
    private static final int COLS = 28;
    private static final double TILE_SIZE = 32;

    private final TileGraphicFactory graphicFactory = new TileGraphicFactory();
    private final MapRenderer renderer = new MapRenderer(graphicFactory, TILE_SIZE);
    private final Canvas canvas = new Canvas(COLS * TILE_SIZE, ROWS * TILE_SIZE);
    private final Label status = new Label();
    private int mapsDrawn = 0;

    /**
     * Factory method that creates the desired kind of Map (from Assignment 01).
     *
     * @param type "city" or "wilderness" (case-insensitive)
     */
    public static Map createMap(String type, int rows, int cols) {
        if ("city".equalsIgnoreCase(type)) {
            return new CityMap(rows, cols);
        } else if ("wilderness".equalsIgnoreCase(type)) {
            return new WildernessMap(rows, cols);
        }
        throw new IllegalArgumentException("Unknown map type: " + type);
    }

    @Override
    public void start(Stage stage) {
        Button cityButton = new Button("New city map");
        cityButton.setOnAction(event -> show(createMap("city", ROWS, COLS)));
        Button wildernessButton = new Button("New wilderness map");
        wildernessButton.setOnAction(event -> show(createMap("wilderness", ROWS, COLS)));

        HBox toolbar = new HBox(10, cityButton, wildernessButton);
        toolbar.setPadding(new Insets(10));
        status.setPadding(new Insets(8, 10, 10, 10));

        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        root.setCenter(canvas);
        root.setBottom(status);

        show(createMap("city", ROWS, COLS));

        stage.setTitle("RPG map - Flyweight rendering");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /** Phase 2: renders the map and reports how the flyweights were shared. */
    private void show(Map map) {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.render(map, canvas.getGraphicsContext2D());
        mapsDrawn++;

        int tiles = map.getRows() * map.getCols();
        String text = String.format(
                "%s map %d x %d: %d tiles drawn using %d tile graphics. "
                        + "Graphic objects created in total: %d (after %d maps).",
                map.getName(), map.getRows(), map.getCols(), tiles, countTypes(map),
                graphicFactory.getCreatedCount(), mapsDrawn);
        status.setText(text);
        System.out.println(text + " Graphic requests so far: " + graphicFactory.getRequestCount());
    }

    /** @return how many different tile types appear on the map. */
    private static int countTypes(Map map) {
        java.util.Set<String> types = new java.util.HashSet<>();
        for (int row = 0; row < map.getRows(); row++) {
            for (int col = 0; col < map.getCols(); col++) {
                types.add(map.getTile(row, col).getType());
            }
        }
        return types.size();
    }
}
