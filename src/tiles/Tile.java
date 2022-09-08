package tiles;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Tile {

    private ArrayList<Circle> elements;
    private int x, y; // x-y coordinate of upper-left corner
    private Group group = new Group();
    private static boolean bgColor = false;
    private Rectangle square;

    // Array of tile background colors to choose from
    //Color[] bgColors = new Color[5];
    private static Color bgColors1 = Color.NAVY;
    private static Color bgColors2 = Color.GREY;

    private final Color bgDefault;

    public Tile(ArrayList<Circle> elements, int x, int y) {

        this.elements = elements;
        this.x = x;
        this.y = y;

        this.bgDefault = (bgColor)? bgColors1 : bgColors2;
        square = new Rectangle(100, 100, bgDefault);
        group.getChildren().add(square);

        bgColor = !bgColor;

        for(Circle element : elements) {

            group.getChildren().add(element);

        }

    }

    public ArrayList<Circle> matchElements(Tile compare) {

        ArrayList<Circle> matched = new ArrayList<>();

        for(Circle element1 : compare.elements) {

            for(Circle element2 : elements) {

                if(equalsElement(element1, element2)) matched.add(element2);

            }

        }

        removeElements(matched);

        return matched;

    }

    private static boolean equalsElement(Circle c, Circle d) {

        if(c.getCenterX() == d.getCenterX() &&
                c.getCenterY() == d.getCenterY() &&
                c.getFill() == d.getFill()) return true;

        return false;

    }

    public void removeElements(ArrayList<Circle> elements) {

        for(Circle c : elements) {

            for(Circle d : this.elements) {

                if(equalsElement(c, d)) this.elements.remove(d);

            }

        }

    }

    public void setColor(Color bgColor) {

        group.getChildren().set(group.getChildren().indexOf(square), new Rectangle(100, 100, bgColor));

    }

    public void setColor() {

        //group.getChildren().set(group.getChildren().indexOf(square), new Rectangle(100, 100, bgDefault));

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
