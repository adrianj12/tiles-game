/**
 *
 * @author Adrian Abeyta <ajabeyta@unm.edu>
 * @description Main class used on playing board. Contains all matching element pairs and background color.
 * @usage Instantiate to populate GridPane to create a checkers-style game board
 * @name Tile
 *
 */
package tiles;

// JavaFX
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// Java utilities
import java.util.ArrayList;

// @TODO: Make empty tiles unselectable
// @TODO: Tile should have eventHandler so no mouse coordinate calculation needed in main
public class Tile {

    private final ArrayList<Element> elements = new ArrayList<>();
    private final Group group = new Group(); // Main GUI group
    private final Rectangle square; // Tile background

    private final Color bgDefault;

    /**
     * @description constructor: accepts list of Elements to add and default background color
     * @param elements - list of all elements (graphic circles) to add to this tile
     * @param bgColor - default background color to use
     */
    public Tile(ArrayList<Element> elements, Color bgColor) {

        for(Element e : elements) {

            this.elements.add(new Element(e.getCenterX(), e.getCenterY(), e.getRadius(), e.getFill()));

        }

        // Background square
        this.bgDefault = bgColor;
        square = new Rectangle(100, 100, bgDefault);
        group.getChildren().add(square);

        // Add the circles last to be on top
        group.getChildren().addAll(elements);

    }

    /**
     * @description Finds and returns all matching elements between Tile parameters
     * @param first tile to find matches
     * @param second tile to find matches
     * @return an ArrayList of matching elements between two Tiles
     */
    public static ArrayList<Element> matchElements(Tile first, Tile second) {

        ArrayList<Element> matches = new ArrayList<>();

        for(Element element : first.elements) {

            if(second.elements.contains(element)) {
                matches.add(element);
            }

        }

        return matches;

    }

    /**
     * @description removes any elements from this Tile in ArrayList parameter
     * @param elements which Element objects to remove from this Tile
     */
    public void removeElements(ArrayList<Element> elements) {

        this.elements.removeAll(elements);

        try {

            group.getChildren().removeAll(elements);

        } catch(IndexOutOfBoundsException ie) {

            // Catch random exceptions that appear (in TODO to fix this)

        }

    }

    /**
     * @description sets background color back to default to indicate deselection
     */
    public void deselect() {

        this.square.setFill(bgDefault);

    }

    /**
     * @description sets background color to green to indicate selection
     */
    public void select() {

        this.square.setFill(Color.GREEN);

    }

    /**
     * @description used to check if this Tile has been cleared
     * @return whether this Tile has an empty list of elements (Tile is cleared)
     */
    public boolean isEmpty() {
        return (this.elements.isEmpty());
    }

    /**
     * @description getter method for JavaFX group member
     * @return JavaFX.Scene.Group object in this Tile
     */
    public Group getGroup() {
        return group;
    }

}