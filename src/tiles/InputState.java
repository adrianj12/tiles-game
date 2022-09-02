package tiles;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class InputState {

    private double x;
    private double y;

    public EventHandler<MouseEvent> getMouseHandler() {

        return event -> {

            x = event.getX();
            y = event.getY();

        };

    }

}
