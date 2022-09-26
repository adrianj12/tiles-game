/**
 *
 * @author Adrian Abeyta <ajabeyta@unm.edu>
 * @description Main file for running Tiles game - entry point to program
 * @usage Main application, no CLI arguments
 * @name TilesGame
 *
 */

package tiles;

// Animation
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import java.time.Duration;

// GUI components
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.layout.TilePane;
import javafx.geometry.Orientation;

// Graphics
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

// Utilities
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TilesGame extends Application {

    // Tile parameters
    private static final int NUM_ROWS = 4; // The product of rows and columns MUST be even
    private static final int NUM_COLS = 4;
    private static final int H_GAP = 5;
    private static final int V_GAP = 5;
    private static final int TILE_WIDTH = 100;
    private static final int TILE_HEIGHT = 100;

    private static final Random rand = new Random();

    // Create Tile arrayList
    private static final ArrayList<Tile> tiles = new ArrayList<>(NUM_ROWS * NUM_COLS);

    // Circle colors to choose randomly from -- DO NOT use NAVY, GREY, or GREEN (background colors)
    private static final Color[] colors = {
            Color.ORANGERED,
            Color.AQUAMARINE,
            Color.WHITE,
            Color.PURPLE

/*          @TODO: make it work with more colors
            Color.BLACK,
            Color.BURLYWOOD,
            Color.CORAL,
            Color.YELLOW,
            Color.GOLD,
            Color.LIGHTBLUE,
            Color.LIME
*/

    };

    // Pairs of matches separated by location
    private static final ArrayList<Element> pairsTL = new ArrayList<>();
    private static final ArrayList<Element> pairsTR = new ArrayList<>();
    private static final ArrayList<Element> pairsBL = new ArrayList<>();
    private static final ArrayList<Element> pairsBR = new ArrayList<>();

    // Colors tiles alternate between
    private static final Color bgColors1 = Color.NAVY;
    private static final Color bgColors2 = Color.GREY;

    // Holders for user-selected tiles
    private static int choiceIndex1, choiceIndex2;

    // Game score variables
    private static int streak = 0, longestStreak = 0;

    /**
     * @description Only runs the JavaFX application launch method
     * @param args CLI arguments (none accepted)
     */
    public static void main(String[] args) {

        launch(args);

    }

    /**
     * @description method to display JavaFX GUI -- describes an OS window
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage) {

        // Set window/stage properties for game
        stage.setTitle("Tiles Game by Adrian Abeyta");
        stage.setResizable(false);

        int windowWidth = (NUM_COLS * TILE_WIDTH) + (NUM_COLS  * H_GAP * 2);
        stage.setWidth(windowWidth);
        int windowHeight = (NUM_ROWS * TILE_HEIGHT + (NUM_ROWS * V_GAP * 2) + 100);
        stage.setHeight(windowHeight);

        Group sceneGroup = new Group();

        // Set up the game board properties
        TilePane board = new TilePane(Orientation.HORIZONTAL, H_GAP, V_GAP);

        // Set sizes and number of tiles
        board.setPrefColumns(NUM_COLS);
        board.setPrefTileWidth(TILE_WIDTH);
        board.setPrefTileHeight(TILE_HEIGHT);

        // How many pairs we need to generate in total
        int numPairs = (NUM_ROWS * NUM_COLS) / 2;

        // Holder variables for transferring element properties
        int radius;
        Color color;

        // Generate duplicate matches for each corner of every Tile
        for(int i = 0; i < numPairs; i++) {

            radius = rand.nextInt(5, 25);
            color = colors[rand.nextInt(colors.length)];
            pairsTL.add(new Element(30, 30,
                    radius, color));
            pairsTL.add(new Element(30, 30,
                    radius, color));

            radius = rand.nextInt(5, 25);
            color = colors[rand.nextInt(colors.length)];
            pairsTR.add(new Element(70, 30,
                    radius, color));
            pairsTR.add(new Element(70, 30,
                    radius, color));

            radius = rand.nextInt(5, 25);
            color = colors[rand.nextInt(colors.length)];
            pairsBR.add(new Element(70, 70,
                    radius, color));
            pairsBR.add(new Element(70, 70,
                    radius, color));

            radius = rand.nextInt(5, 25);
            color = colors[rand.nextInt(colors.length)];
            pairsBL.add(new Element(30, 70,
                    radius, color));
            pairsBL.add(new Element(30, 70,
                    radius, color));

        }

        // Shuffle up the pairs
        Collections.shuffle(pairsTR);
        Collections.shuffle(pairsTL);
        Collections.shuffle(pairsBR);
        Collections.shuffle(pairsBL);

        // Add matching elements tile by tile
        int index;
        Color bgColor = bgColors1;
        for(int i = 0; i < NUM_ROWS; i++) {

            for (int j = 0; j < NUM_COLS; j++) {

                ArrayList<Element> elementGroup = new ArrayList<>(4);

                //@TODO: Make pairs a multidimensional array to reduce lines
                // Top left
                index = rand.nextInt(pairsTL.size());
                elementGroup.add(pairsTL.get(index));
                pairsTL.remove(index);

                // Top right
                index = rand.nextInt(pairsTR.size());
                elementGroup.add(pairsTR.get(index));
                pairsTR.remove(index);

                // Bottom left
                index = rand.nextInt(pairsBL.size());
                elementGroup.add(pairsBL.get(index));
                pairsBL.remove(index);

                // Bottom right
                index = rand.nextInt(pairsBR.size());
                elementGroup.add(pairsBR.get(index));
                pairsBR.remove(index);

                tiles.add(new Tile(elementGroup, bgColor));
                elementGroup.clear();

                // Alternate colors between tiles horizontally
                if(!(NUM_COLS % 2 == 0 && j == (NUM_COLS - 1))) // This accounts for even-numbered columns
                    bgColor = (bgColor == bgColors1)? bgColors2 : bgColors1;

                board.getChildren().add(tiles.get((NUM_COLS * i) + j).getGroup());

            }

        }

        // Mouse event handling
        InputState mouseInput = new InputState();
        board.setOnMouseClicked(mouseInput.getMouseHandler());

        // Bottom screen score text
        Text streakCurrent = new Text(0, windowHeight - 80, "Current Streak: ");
        Text streakLongest = new Text(0, windowHeight - 60, "Longest Streak: ");
        Text scoreStreakCurrent = new Text(120, windowHeight - 80, String.valueOf(streak));
        Text scoreStreakLongest = new Text(120, windowHeight - 60, String.valueOf(longestStreak));

        // Add all nodes to the scene
        sceneGroup.getChildren().addAll(board, streakCurrent, streakLongest, scoreStreakLongest, scoreStreakCurrent);

        // Scene setting up
        Scene scene = new Scene(sceneGroup, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();

        // ***** Main game loop *****
        AnimationTimer a = new AnimationTimer() {

            private long nextTime = 0; // For incrementing animation timer counter
            double mouseClick_x = -1, mouseClick_y = -1;  // Click coordinate holders
            int choiceState = 0; // # of tiles currently selected
            int row, col; // Positions of chosen tiles

            // Container for matching elements between tiles
            ArrayList<Element> matches = new ArrayList<>();

            /**
             * @description loops infinitely beginning with 'now' parameter time
             * @param now
             *            The timestamp of the current frame given in nanoseconds. This
             *            value will be the same for all {@code AnimationTimers} called
             *            during one frame.
             */
            @Override
            public void handle(long now) {

                boolean isWon = false;

                if(now > nextTime) {

                    mouseClick_x = mouseInput.getX();
                    mouseClick_y = mouseInput.getY();

                    row = (int) (mouseClick_y / (H_GAP + TILE_WIDTH));
                    col = (int) (mouseClick_x / (V_GAP + TILE_HEIGHT));

                    if(mouseClick_x > -1) {

                        if(choiceState == 0) { // Choose the first tile

                            choiceIndex1 = (NUM_COLS * row) + col;

                            // Tile choice indicator
                            tiles.get(choiceIndex1).select();

                            choiceState++;

                        } else if(choiceState == 1) { // Choose the second tile

                            choiceIndex2 = (NUM_COLS * row) + col;

                            if(choiceIndex2 != choiceIndex1) {

                                // Tile choice indicator
                                tiles.get(choiceIndex2).select();

                                matches = Tile.matchElements(tiles.get(choiceIndex1), tiles.get(choiceIndex2));

                                if(!matches.isEmpty()) {

                                    // Remove the matching elements from both tiles
                                    tiles.get(choiceIndex1).removeElements(matches);
                                    tiles.get(choiceIndex2).removeElements(matches);

                                    // Clear first tile choice
                                    tiles.get(choiceIndex1).deselect();

                                    // Second tile was fully cleared, start over
                                    if (tiles.get(choiceIndex2).isEmpty()) {

                                        choiceState = 0;
                                        tiles.get(choiceIndex2).deselect();

                                    }

                                    // Switch to continue tile streak
                                    choiceIndex1 = choiceIndex2;

                                    // Keep track of score streak
                                    streak++;
                                    if (streak > longestStreak) longestStreak = streak;

                                    matches = null;

                                } else {

                                    // Clear tile markers
                                    tiles.get(choiceIndex1).deselect();
                                    tiles.get(choiceIndex2).deselect();

                                    choiceState = 0;
                                    streak = 0;

                                }

                            }

                        }

                    }

                    // Update GUI with streak data
                    scoreStreakCurrent.setText(String.valueOf(streak));
                    scoreStreakLongest.setText(String.valueOf(longestStreak));

                    mouseInput.reset();

                    // Check for win
                    int won = 0;
                    for(Tile tile : tiles) {

                        if(tile.isEmpty()) won++;

                    }
                    if(won == tiles.size()) isWon = true;

                    // @TODO: make this fancier with a larger text size or alert box
                    if(isWon) {
                        scoreStreakCurrent.setText("You cleared the board!!");
                    }

                    // Check for input every 0.01 seconds
                    nextTime = now + Duration.ofMillis(10).toNanos();

                }

            }

        };

        a.start();

    }

}