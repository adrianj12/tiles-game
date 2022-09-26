/**
 *
 * @author Adrian Abeyta <ajabeyta@unm.edu>
 * @description Encapsulates all mouse cursor coordinates and handles click events
 * @usage Instantiate to get a mouse click event with x,y coordinates
 * @name InputState
 *
 */
package tiles;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class InputState {

    private double x = -1;
    private double y = -1;

    /**
     *
     * @description for getting mouse clicking event
     * @return MouseEventHandler to determine click coordinates
     */
    public EventHandler<MouseEvent> getMouseHandler() {

        return event -> {

            x = event.getX();
            y = event.getY();

        };

    }

    /**
     * @description getter for x coordinate
     * @return last clicked x coordinate
     */
    public double getX() { return x; }

    /**
     * @description getter for y coordinate
     * @return last clicked y coordinate
     */
    public double getY() { return y; }

    /**
     * @description This is used to clear the x and y coordinates so user can choose next tile
     */
    public void reset() {

        x = y = -1;

    }

}