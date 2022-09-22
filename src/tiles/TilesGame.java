package tiles;

// Animation
import javafx.animation.AnimationTimer;
import javafx.application.Application;

// GUI components
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.layout.TilePane;
import javafx.geometry.Orientation;

// Graphics
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;

public class TilesGame extends Application {

    // Tile parameters
    private static final int NUM_ROWS = 6;
    public static final int NUM_COLS = 6;
    private static final int H_GAP = 5;
    private static final int V_GAP = 5;
    private static final int TILE_WIDTH = 100;
    private static final int TILE_HEIGHT = 100;

    // Create Tile arrayList
    private static final ArrayList<Tile> tiles = new ArrayList<>(NUM_ROWS * NUM_COLS);

    // Circle colors to choose randomly from
    private static final Color[] colors = {
            Color.GREEN,
            Color.AQUAMARINE,
            Color.FUCHSIA,
            Color.PURPLE
    };

    // Colors tiles alternate between
    private static Color bgColors1 = Color.NAVY;
    private static Color bgColors2 = Color.GREY;

    // User-selected tiles
    private static int choiceIndex1, choiceIndex2;

    // Game score variables
    private static int streak = 0, longestStreak = 0;

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage stage) {

        // Set window/stage properties for game
        stage.setTitle("Tiles Game by Adrian Abeyta");
        //stage.setResizable(false);

        int windowWidth = (NUM_COLS * TILE_WIDTH) + ((NUM_COLS - 1) * H_GAP * 2);
        stage.setWidth(windowWidth);
        int windowHeight = (NUM_ROWS * TILE_HEIGHT + ((NUM_ROWS - 1) * V_GAP * 2)) + 100;
        stage.setHeight(windowHeight);

        //System.out.println(stage.getWidth());
        // WHY DOESNT THIS WORK

        Group sceneGroup = new Group();

        // Set up the game board properties
        TilePane board = new TilePane(Orientation.HORIZONTAL, H_GAP, V_GAP);

        // Set sizes and number of tiles
        board.setPrefColumns(NUM_COLS);
        board.setPrefTileWidth(TILE_WIDTH);
        board.setPrefTileHeight(TILE_HEIGHT);

        Color bgColor = bgColors1;

        Random random = new Random();
        for(int i = 0; i < NUM_ROWS; i++) {

            for (int j = 0; j < NUM_COLS; j++) {

                ArrayList<Circle> elements = new ArrayList<>();

                elements.add(new Circle(TILE_WIDTH / 4, TILE_HEIGHT / 4, 10, Color.WHITE));
                elements.add(new Circle(TILE_WIDTH * (3 / 4), TILE_HEIGHT / 4, 10, Color.GREEN));
                elements.add(new Circle(TILE_WIDTH * (3 / 4), TILE_HEIGHT * (3 / 4), 10, Color.WHITE));
                //elements.add(new Circle(random.nextInt(10), 20, 5, Color.GREEN));

                tiles.add(new Tile(elements, (i * (TILE_WIDTH + H_GAP)), (j * (TILE_HEIGHT + V_GAP)), bgColor));

                // Alternate colors between tiles horizontally
                if(!(NUM_COLS % 2 == 0 && j == (NUM_COLS - 1))) // This accounts for even-numbered columns
                    bgColor = (bgColor == bgColors1)? bgColors2 : bgColors1;

                board.getChildren().add(tiles.get((NUM_COLS * i) + j).getGroup());

            }

        }

        // Mouse event handling
        InputState mouseInput = new InputState();
        board.setOnMouseClicked(mouseInput.getMouseHandler());

        Text streakCurrent = new Text(0, windowHeight - 80, "Current Streak: ");
        Text streakLongest = new Text(0, windowHeight - 60, "Longest Streak: ");
        sceneGroup.getChildren().addAll(board, streakCurrent, streakLongest);

        Scene scene = new Scene(sceneGroup, stage.getWidth(), stage.getHeight());

        stage.setScene(scene);
        stage.show();

        // Main game loop
        AnimationTimer a = new AnimationTimer() {

            private long nextTime = 0; // For incrementing animation timer counter
            double mouseClick_x = -1, mouseClick_y = -1;  // Click coordinate holders
            int choiceState = 0; // # of tiles currently selected
            int row, col; // Positions of chosen tiles
            ArrayList<Circle> matches = new ArrayList<>();

            @Override
            public void handle(long now) {

                if(now > nextTime) {

                    mouseClick_x = mouseInput.getX();
                    mouseClick_y = mouseInput.getY();

                    if(choiceState == 0 && mouseClick_x > -1) { // Choose the first tile

                        row = (int) (mouseClick_y / (H_GAP + TILE_WIDTH));
                        col = (int) (mouseClick_x / (V_GAP + TILE_HEIGHT));

                        choiceIndex1 = (NUM_COLS * row) + col;

                        // Tile choice indicator
                        tiles.get(choiceIndex1).setColor(Color.GREEN);

                        choiceState++;

                    } else if(choiceState == 1 && mouseClick_x > -1) { // Choose the second tile

                        row = (int) (mouseClick_y / (H_GAP + TILE_WIDTH));
                        col = (int) (mouseClick_x / (V_GAP + TILE_HEIGHT));

                        choiceIndex2 = (NUM_COLS * row) + col;

                        if(choiceIndex2 != choiceIndex1) {

                            // Tile choice indicator
                            tiles.get(choiceIndex2).setColor(Color.GREEN);

                           matches = Tile.matchElements(tiles.get(choiceIndex1), tiles.get(choiceIndex2));

                            if (!matches.isEmpty()) {System.out.println("Matches!");

                                // Remove the matching elements from both tiles
                                tiles.get(choiceIndex1).removeElements(matches);
                                tiles.get(choiceIndex2).removeElements(matches);

                                // Clear first tile choice
                                tiles.get(choiceIndex1).setColor();

                                choiceIndex1 = choiceIndex2;

                                streak++;
                                if (streak > longestStreak) longestStreak = streak;

                                matches = null;

                            } else {

                                // Clear tile markers
                                tiles.get(choiceIndex1).setColor();
                                tiles.get(choiceIndex2).setColor();

                                choiceState = 0;

                                streak = 0;

                            }

                        }

                    }

                    mouseInput.reset();

                    // Check for input every 0.1 seconds
                    nextTime = now + Duration.ofMillis(100).toNanos();

                }

            }

        };

        a.start();

    }

}