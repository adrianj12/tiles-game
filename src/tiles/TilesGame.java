package tiles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.TilePane;
import javafx.geometry.Orientation;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class TilesGame extends Application {

    // Tile parameters
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 5;
    private static final int H_GAP = 5;
    private static final int V_GAP = 5;
    private static final int TILE_WIDTH = 100;
    private static final int TILE_HEIGHT = 100;

    // Array to contain board tiles
    private static final ArrayList<Tile> tiles = new ArrayList<>(NUM_ROWS * NUM_COLS);

    // Array of tile background colors to choose from
    //Color[] bgColors = new Color[5];
    Color bgColors1 = Color.NAVY;
    Color bgColors2 = Color.GREY;

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage stage) {

        // Set window/stage properties for game
        stage.setTitle("Tiles Game by Adrian Abeyta");
        //stage.setResizable(false);

        stage.setWidth((NUM_COLS * TILE_WIDTH) + ((NUM_COLS - 1) * H_GAP * 2));
        int windowHeight = (NUM_ROWS * TILE_HEIGHT + ((NUM_ROWS - 1) * V_GAP * 2)) + 100;
        stage.setHeight(windowHeight);

        System.out.println(stage.getWidth());

        Group group = new Group();

        // Set up the game board properties
        TilePane board = new TilePane(Orientation.HORIZONTAL, H_GAP, V_GAP);

        // Set sizes and number of tiles
        board.setPrefColumns(NUM_COLS);
        board.setPrefTileWidth(TILE_WIDTH);
        board.setPrefTileHeight(TILE_HEIGHT);

        // Create the tiles
        for(int i = 0; i < NUM_ROWS * NUM_COLS; i++) {

            // Alternate colors of subsequent tiles
            Color tileColor = (i % 2 == 0)? bgColors1 : bgColors2;

            tiles.add(new Tile(tileColor, new ArrayList<>()));
            Rectangle rectangle = new Rectangle(100, 100, tileColor);
            board.getChildren().add(rectangle);

        }

        Text streakCurrent = new Text(0, windowHeight - 80, "Current Streak: ");
        Text streakLongest = new Text(0, windowHeight - 60, "Longest Streak: ");
        group.getChildren().addAll(board, streakCurrent, streakLongest);

        Scene scene = new Scene(group, stage.getWidth(), stage.getHeight());

        stage.setScene(scene);
        stage.show();

    }

}