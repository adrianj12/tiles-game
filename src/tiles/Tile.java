package tiles;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Tile {

    private ArrayList<Element> elements = new ArrayList<>();
    private int x, y; // x-y coordinate of upper-left corner
    private Group group = new Group(); // Main GUI group
    private Rectangle square; // Tile background

    private final Color bgDefault;

    public Tile(ArrayList<Element> elements, int x, int y, Color bgColor) {

        for(Element e : elements) {

            this.elements.add(new Element(e.getCenterX(), e.getCenterY(), e.getRadius(), e.getFill()));

        }

        // Set default values
        this.x = x;
        this.y = y;
        this.bgDefault = bgColor;

        // Background square
        square = new Rectangle(100, 100, bgDefault);
        group.getChildren().add(square);

        // Add the circles last to be on top
        group.getChildren().addAll(elements);

    }

    public static ArrayList<Element> matchElements(Tile first, Tile second) {

        ArrayList<Element> matches = new ArrayList<>();

        for(Element element1 : first.elements) {

            for(Element element2 : second.elements) {

                if(element1.equals(element2)) matches.add(element1);

            }

        }

        return matches;

    }

    public void removeElements(ArrayList<Element> elements) {

        for(Element e : elements) {

            this.elements.remove(e);
            group.getChildren().removeAll(e);

        }

    }

    public void setColor(Color bgColor) {

        group.getChildren().set(0, new Rectangle(100, 100, bgColor));

    }

    public void deselect() {

        group.getChildren().set(0, new Rectangle(100, 100, bgDefault));

    }

    public void select() {
        this.square.setFill(Color.GREEN);
    }

    public boolean isEmpty() {
        return (this.elements.isEmpty());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Group getGroup() {
        return group;
    }

}