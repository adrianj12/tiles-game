package tiles;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Element extends Circle {

    public Element(double centerX, double centerY, double radius, Paint color) {

        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadius(radius);
        this.setFill(color);

    }

    @Override
    public boolean equals(Object o) {

        if(o == this) return true;

        try {

            final Element e = (Element) o;
            if (this.getCenterX() == e.getCenterX() &&
                    this.getCenterY() == e.getCenterY() &&
                    this.getFill() == e.getFill() &&
                    this.getRadius() == e.getRadius()) return true;

        } catch(ClassCastException e) {
            return false;
        }

        return false;

    }

}
