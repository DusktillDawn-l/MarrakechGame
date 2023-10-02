package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        Text p = new Text();
        p.setText("GAME START");
        p.setStyle("-fx-font: 50 arial;");
        p.setX(400);
        p.setY(350);
        root.getChildren().add(p);
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
}
