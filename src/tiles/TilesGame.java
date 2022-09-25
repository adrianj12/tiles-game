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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TilesGame extends Application {

    // Tile parameters
    private static final int NUM_ROWS = 4; // The product of rows and columns MUST be even
    public static final int NUM_COLS = 4;
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
            /*
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
    private static ArrayList<Pair> pairsTL = new ArrayList<>();
    private static ArrayList<Pair> pairsTR = new ArrayList<>();
    private static ArrayList<Pair> pairsBL = new ArrayList<>();
    private static ArrayList<Pair> pairsBR = new ArrayList<>();

    // Colors tiles alternate between
    private static Color bgColors1 = Color.NAVY;
    private static Color bgColors2 = Color.GREY;

    // Holders for user-selected tiles
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

        int numPairs = (NUM_ROWS * NUM_COLS) / 2;
        int radius = 0;
        Color color;
        for(int i = 0; i < numPairs; i++) {

            radius = rand.nextInt(5, 25);
            color = colors[rand.nextInt(colors.length)];
            pairsTL.add(new Pair(new Element(30, 30,
                    radius, color)));
            pairsTL.add(new Pair(new Element(30, 30,
                    radius, color)));

            radius = rand.nextInt(5, 25);
            color = colors[rand.nextInt(colors.length)];
            pairsTR.add(new Pair(new Element(70, 30,
                    radius, color)));
            pairsTR.add(new Pair(new Element(70, 30,
                    radius, color)));

            radius = rand.nextInt(5, 25);
            color = colors[rand.nextInt(colors.length)];
            pairsBR.add(new Pair(new Element(70, 70,
                    radius, color)));
            pairsBR.add(new Pair(new Element(70, 70,
                    radius, color)));

            radius = rand.nextInt(5, 25);
            color = colors[rand.nextInt(colors.length)];
            pairsBL.add(new Pair(new Element(30, 70,
                    radius, color)));
            pairsBL.add(new Pair(new Element(30, 70,
                    radius, color)));

        }

        // Shuffle up the pairs
        Collections.shuffle(pairsTR);
        Collections.shuffle(pairsTL);
        Collections.shuffle(pairsBR);
        Collections.shuffle(pairsBL);

        ArrayList<Element> elementGroup = new ArrayList<>(4);
        int index = 0;
        Pair holder;
        for(int i = 0; i < NUM_ROWS; i++) {

            for (int j = 0; j < NUM_COLS; j++) {

                // Top left
                index = rand.nextInt(pairsTL.size());
                holder = pairsTL.get(index);
                elementGroup.add(holder.getElement());
                pairsTL.remove(index);
                System.out.println(pairsTR.size());
                // Top right
                index = rand.nextInt(pairsTR.size());
                holder = pairsTR.get(index);
                elementGroup.add(holder.getElement());
                pairsTR.remove(index);

                // Bottom left
                index = rand.nextInt(pairsBL.size());
                holder = pairsBL.get(index);
                elementGroup.add(holder.getElement());
                pairsBL.remove(index);

                // Bottom right
                index = rand.nextInt(pairsBR.size());
                holder = pairsBR.get(index);
                elementGroup.add(holder.getElement());
                pairsBR.remove(index);

                tiles.add(new Tile(elementGroup, (i * (TILE_WIDTH + H_GAP)), (j * (TILE_HEIGHT + V_GAP)), bgColor));
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

        sceneGroup.getChildren().addAll(board, streakCurrent, streakLongest, scoreStreakLongest, scoreStreakCurrent);

        Scene scene = new Scene(sceneGroup, stage.getWidth(), stage.getHeight());

        stage.setScene(scene);
        stage.show();

        // Main game loop
        AnimationTimer a = new AnimationTimer() {

            private long nextTime = 0; // For incrementing animation timer counter
            double mouseClick_x = -1, mouseClick_y = -1;  // Click coordinate holders
            int choiceState = 0; // # of tiles currently selected
            int row, col; // Positions of chosen tiles
            ArrayList<Element> matches = new ArrayList<>();

            @Override
            public void handle(long now) {

                boolean isWon = false;

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
                            tiles.get(choiceIndex2).select();

                           matches = Tile.matchElements(tiles.get(choiceIndex1), tiles.get(choiceIndex2));

                            if (!matches.isEmpty()) {

                                // Remove the matching elements from both tiles
                                tiles.get(choiceIndex1).removeElements(matches);
                                tiles.get(choiceIndex2).removeElements(matches);

                                // Clear first tile choice
                                tiles.get(choiceIndex1).deselect();

                                // Switch to continue tile streak
                                choiceIndex1 = choiceIndex2;

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

                    scoreStreakCurrent.setText(String.valueOf(streak));
                    scoreStreakLongest.setText(String.valueOf(longestStreak));

                    mouseInput.reset();

                    // Check for win
                    int won = 0;
                    for(Tile tile : tiles) {

                        if(tile.isEmpty()) won++;

                    }
                    if(won == tiles.size()) isWon = true;

                    if(isWon) {
                        scoreStreakCurrent.setText("You won!!");
                    }

                    // Check for input every 0.1 seconds
                    nextTime = now + Duration.ofMillis(100).toNanos();

                }

            }

        };

        a.start();

    }

    // @TODO: Remove this private class and just use Element(s)
    private class Pair {

        private Element element;

        public Pair(Element e) {

            this.element = e;

        }

        public Element getElement() {
            return this.element;
        }

    }

}