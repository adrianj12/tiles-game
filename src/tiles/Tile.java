package tiles;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class Tile {

    private ArrayList<Element> elements = new ArrayList<>();
    private int x, y; // x-y coordinate of upper-left corner
    private Group group = new Group();
    private static boolean bgColor = false;
    private Rectangle square;

    private final Color bgDefault;

    public Tile(ArrayList<Circle> elements, int x, int y, Color bgColor) {

        for(Circle c : elements) {

            this.elements.add(new Element(c.getCenterX(), c.getCenterY(), c.getRadius(), c.getFill()));

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

    public static ArrayList<Circle> matchElements(Tile first, Tile second) {

        ArrayList<Circle> matches = new ArrayList<>();

        for(Element element1 : first.elements) {

            for(Element element2 : second.elements) {

                if(element1.equals(element2)) matches.add((Circle) element1);

            }

        }

        return matches;

    }

    private static boolean equalsElement(Circle c, Circle d) {

        if(c.getCenterX() == d.getCenterX() &&
                c.getCenterY() == d.getCenterY() &&
                c.getFill() == d.getFill()) return true;

        return false;

    }

    public void removeElements(ArrayList<Circle> elements) {

        for(Circle c : elements) {

            this.elements.remove((Element) c);
            group.getChildren().remove(1);

        }

    }

    public void setColor(Color bgColor) {

        group.getChildren().set(0, new Rectangle(100, 100, bgColor));

    }

    public void setColor() {

        group.getChildren().set(0, new Rectangle(100, 100, bgDefault));

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

    private class Element extends Circle {

        public Element(double centerX, double centerY, double radius, Paint color) {

            this.setCenterX(centerX);
            this.setCenterY(centerY);
            this.setRadius(radius);
            this.setFill(color);

        }

        @Override
        public boolean equals(Object o) {

            final Element e = (Element) o;

            if (this.getCenterX() == e.getCenterX() &&
                    this.getCenterY() == e.getCenterY() &&
                    this.getFill() == e.getFill()) return true;

            return false;

        }

    }

}