package tiles;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class Tile {

    private final Color color;
    private ArrayList<Circle> objects;

    public Tile(Color background, ArrayList<Circle> objects) {

        this.color = background;
        this.objects = objects;

    }

    public boolean matchObjects(ArrayList<Circle> objects) {

        return this.objects.removeAll(objects);

    }

    public Color getColor() {

        return this.color;

    }

}
