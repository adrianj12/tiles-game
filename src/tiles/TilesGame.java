package tiles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.TilePane;
import javafx.geometry.Orientation;

public class TilesGame extends Application {

    private static final int NUM_ROWS = 2;
    private static final int NUM_COLS = 2;

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("Tiles Game by Adrian Abeyta");

        TilePane board = new TilePane(Orientation.HORIZONTAL, 2, 2);

        // Set sizes and number of tiles
        board.setPrefColumns(2);
        board.setPrefTileWidth(10);
        board.setPrefTileHeight(10);

        Rectangle first = new Rectangle(4, 4 , Paint.valueOf("CYAN"));
        Rectangle second = new Rectangle(4, 4 , Paint.valueOf("CYAN"));
        Rectangle third = new Rectangle(4, 4 , Paint.valueOf("CYAN"));
        Rectangle fourth = new Rectangle(4, 4 , Paint.valueOf("CYAN"));
        board.getChildren().add(first);
        board.getChildren().add(second);
        board.getChildren().add(third);
        board.getChildren().add(fourth);

        Scene scene = new Scene(board, 400, 300);

        stage.setScene(scene);
        stage.show();

    }

}