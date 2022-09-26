/**
 *
 * @author Adrian Abeyta <ajabeyta@unm.edu>
 * @aescription Used to represent each matching element in a tile. Consists of circle Shape
 * @usage Element is synonymous with circle (wrapper class)
 * @name Element
 *
 */

package tiles;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Element extends Circle {

    /**
     * @description constructor: allows creation of JavaFX Circle with specified center
     * coordinates, radius, and fill color
     * @param centerX center x-coordinate
     * @param centerY center y-coordinate
     * @param radius circle radius
     * @param color fill color for circle
     */
    public Element(double centerX, double centerY, double radius, Paint color) {

        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadius(radius);
        this.setFill(color);

    }

    /**
     * @description overriding default object equals method to provide more generic equality testing
     * needed to compare elements between Tiles
     * @param o object to be compared against
     * @return boolean indicating status of equality
     */
    @Override
    public boolean equals(Object o) {

        if(o == null) return false;

        if(o instanceof Element) {

            if (o == this) return true;

            try {

                final Element e = (Element) o;
                if (this.getCenterX() == e.getCenterX() &&
                        this.getCenterY() == e.getCenterY() &&
                        this.getFill() == e.getFill() &&
                        this.getRadius() == e.getRadius()) return true;

            } catch (ClassCastException e) {
                return false;
            }

        }

        return false;

    }

}
