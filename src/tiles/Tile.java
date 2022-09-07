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
    Color bgColors1 = Color.NAVY;
    Color bgColors2 = Color.GREY;

    public Tile(ArrayList<Circle> elements, int x, int y) {

        this.elements = elements;
        this.x = x;
        this.y = y;

        Color bg = (bgColor)? bgColors1 : bgColors2;
        square = new Rectangle(100, 100, bg);
        group.getChildren().add(square);

        bgColor = !bgColor;

        for(Circle element : elements) {

            group.getChildren().add(element);

        }

    }

    public static boolean matchObjects(Tile first, Tile second) {

        boolean matchExist = false;



        return matchExist;

    }

    protected void removeElements(ArrayList<Circle> elements) {
        elements.removeAll(elements);
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
