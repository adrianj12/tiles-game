package tiles;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class InputState {

    private double x = -1;
    private double y = -1;

    public EventHandler<MouseEvent> getMouseHandler() {

        return event -> {

            x = event.getX();
            y = event.getY();

        };

    }

    public double getX() { return x; }
    public double getY() { return y; }

    public void reset() {

        x = y = -1;

    }

}