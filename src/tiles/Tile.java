package tiles;

import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class Tile {

    private ArrayList<Circle> elements;
    private int x, y; // x-y coordinate of upper-left corner

    public Tile(ArrayList<Circle> elements, int x, int y) {

        this.elements = elements;
        this.x = x;
        this.y = y;

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

}
