package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField boardTextField;


    /**
     * Draw a placement in the window, removing any previously drawn placements
     * The default color (not painted square) is orange
     * @param state an array of two strings, representing the current game state
     */

    void displayState(String state) {
        int boardSize = 80;
        double gapSize = 0;
        Pane pane = new Pane();

        ArrayList<Rectangle> rectangles = new ArrayList<>();

        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                double x = 320 + col * (boardSize + gapSize);
                double y = 70 + row * (boardSize + gapSize);

                Rectangle square = new Rectangle((int) x, (int) y, boardSize, boardSize);
                square.setFill(Color.WHITE);
                square.setStroke(Color.BLACK);
                rectangles.add(square);
                pane.getChildren().add(square);
            }
        }

        Scene scene = new Scene(pane, 1200, 700);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Board");
        primaryStage.setScene(scene);
        primaryStage.show();

        String[] splits = state.split("A");
        String assamAndBoard = splits[1];
        String assam = assamAndBoard.substring(0, 3);
        String board = assamAndBoard.substring(4);
        char assamDirection = assam.charAt(2);
        char assamPositionRow = assam.charAt(0);
        char assamPositionCol = assam.charAt(1);

        // Fill the Rug color
        for (int i = 0; i < board.length(); i += 3) {
            String currentSquare = board.substring(i, i + 3);
            if (!currentSquare.equals("n00")) {
                int row = i / 3 / 7;
                int col = i / 3 % 7;
                char squareColor = currentSquare.charAt(0);
                if (squareColor == 'p') {
                    rectangles.get(row * 7 + col).setFill(Color.PURPLE);
                } else if (squareColor == 'c') {
                    rectangles.get(row * 7 + col).setFill(Color.CYAN);
                } else if (squareColor == 'y') {
                    rectangles.get(row * 7 + col).setFill(Color.YELLOW);
                } else if (squareColor == 'r'){
                    rectangles.get(row * 7 + col).setFill(Color.RED);
                } else {
                    throw new RuntimeException("Invalid color");
                }
            }
        }

        // Fill the assam to the right position,use arrow can represent the direction
        int assamRow = Character.getNumericValue(assamPositionRow);
        int assamCol = Character.getNumericValue(assamPositionCol);

        double assamX = 320 + assamCol * (boardSize + gapSize) + (double) boardSize / 2;
        double assamY = 70 + assamRow * (boardSize + gapSize) + (double) boardSize / 2;

        // Create an arrow pointing upward as default
        Polygon assamArrow = new Polygon();
        assamArrow.getPoints().addAll(
                assamX, assamY - boardSize / 5,
                assamX - boardSize / 5, assamY + boardSize / 5,
                assamX + boardSize / 5, assamY + boardSize / 5
        );
        assamArrow.setFill(Color.BLACK);

        switch (assamDirection) {
            case 'N':
                break;  // Default: Arrow is already pointing up
            case 'E':
                assamArrow.setRotate(90);
                break;
            case 'S':
                assamArrow.setRotate(180);
                break;
            case 'W':
                assamArrow.setRotate(270);
                break;
            default:
                throw new RuntimeException("Invalid direction");
        }

        pane.getChildren().addAll(assamArrow);
    }


        /**
         * Create a basic text field for input and a refresh button.
         */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
