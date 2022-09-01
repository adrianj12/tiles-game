import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TilesGame extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Tiles Game by Adrian Abeyta");

        Circle circ = new Circle(34, 34 , 40);
        Group root = new Group(circ);
        Scene scene = new Scene(root, 400, 300);

        stage.setScene(scene);
        stage.show();

    }

}