package tiles;

import javafx.animation.AnimationTimer;
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
import javafx.scene.input.MouseEvent;

import java.time.Duration;
import java.util.ArrayList;

public class TilesGame extends Application {

    // Tile parameters
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 5;
    private static final int H_GAP = 5;
    private static final int V_GAP = 5;
    private static final int TILE_WIDTH = 100;
    private static final int TILE_HEIGHT = 100;

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

        System.out.println(stage.getWidth()); // WHY DOESNT THIS WORK

        Group group = new Group();

        // Set up the game board properties
        TilePane board = new TilePane(Orientation.HORIZONTAL, H_GAP, V_GAP);

        // Set sizes and number of tiles
        board.setPrefColumns(NUM_COLS);
        board.setPrefTileWidth(TILE_WIDTH);
        board.setPrefTileHeight(TILE_HEIGHT);

        // Create the board tile rectangle graphics
        for(int i = 0; i < NUM_ROWS * NUM_COLS; i++) {

            // Alternate colors of subsequent tiles
            Color tileColor = (i % 2 == 0)? bgColors1 : bgColors2;

            Rectangle rectangle = new Rectangle(100, 100, tileColor);
            board.getChildren().add(rectangle);

        }

        // Mouse event handling
        InputState mouseInput = new InputState();
        board.setOnMouseClicked(mouseInput.getMouseHandler());

        Text streakCurrent = new Text(0, windowHeight - 80, "Current Streak: ");
        Text streakLongest = new Text(0, windowHeight - 60, "Longest Streak: ");
        group.getChildren().addAll(board, streakCurrent, streakLongest);

        Scene scene = new Scene(group, stage.getWidth(), stage.getHeight());

        stage.setScene(scene);
        stage.show();

        AnimationTimer a = new AnimationTimer() {

            private long nextTime = 0;
            double mouseClick_x1 = -1, mouseClick_y1 = -1;  // First chosen tile
            double mouseClick_x2 = -1, mouseClick_y2 = -1; // Second chosen tile
            int choiceState = 0; // # of tiles currently selected
            int row_1, row_2, col_1, col_2;

            private static final ArrayList<Tile> tiles = new ArrayList<>(NUM_ROWS * NUM_COLS);

            @Override
            public void handle(long now) {

                if(nextTime == 0) {

                    for(int i = 0; i < NUM_ROWS; i++) {

                        for(int j = 0; j < NUM_COLS; j++) {

                            tiles.add(new Tile(new ArrayList<>(), (i * (TILE_WIDTH + H_GAP)), (j * (TILE_HEIGHT + V_GAP))));

                        }

                    }

                }

                if(now > nextTime) {

                    // First tile is chosen
                    if(choiceState == 0 && mouseInput.getX() > -1) {

                        mouseClick_x1 = mouseInput.getX();
                        mouseClick_y1 = mouseInput.getY();

                        row_1 = (int) (mouseClick_x1 / (H_GAP + TILE_WIDTH));
                        col_1 = (int) (mouseClick_y1 / (V_GAP + TILE_HEIGHT));

                        int index = (NUM_COLS * row_1) + col_1;

                        group.getChildren().add(new Rectangle(tiles.get(index).getX(), tiles.get(index).getY(), 100, 100));
                        choiceState++;
                        mouseInput.reset();

                    } else if(choiceState == 1 && mouseInput.getX() > -1) {
                        // Second tile is chosen

                        mouseClick_x2 = mouseInput.getX();
                        mouseClick_y2 = mouseInput.getY();

                        row_2 = (int) (mouseClick_x2 / (H_GAP + TILE_WIDTH));
                        col_2 = (int) (mouseClick_y2 / (V_GAP + TILE_HEIGHT));

                        choiceState++;
                        mouseInput.reset();

                    } else if(choiceState == 2) {



                    }

                    // Check for input every 0.1 seconds
                    nextTime = now + Duration.ofMillis(100).toNanos();

                }

            }

        };

        a.start();

    }

}