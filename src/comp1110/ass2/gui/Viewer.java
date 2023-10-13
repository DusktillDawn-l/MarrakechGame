package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import comp1110.ass2.Helper.*;

import java.util.ArrayList;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static comp1110.ass2.Helper.charToColor;

import java.awt.*;
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
        String playerStr = splits[0];
        for (int i = 0; i < playerStr.length() / 8; i++) {
            int iInc = i * 8;
            int playerGap = 130;
            // add player color
            Text p = new Text();
            p.setText("Player" + (i + 1));
            p.setFill(charToColor(playerStr.charAt(iInc + 1)));
            p.setStroke(Color.BLACK);
            p.setStyle("-fx-font: 24 arial;");
            p.setStrokeWidth(0.8);
            p.setX(50);
            p.setY(50 + i * playerGap);
            pane.getChildren().add(p);
            // add dirham amount
            Text dirham = new Text();
            dirham.setText("Dirhams: " + Integer.parseInt(playerStr.substring(2 + iInc, 5 + iInc)));
            dirham.setX(50);
            dirham.setY(70 + i * playerGap);
            pane.getChildren().add(dirham);
            // add rug amount
            Text rug = new Text();
            rug.setText("Remaining rugs: " + Integer.parseInt(playerStr.substring(5 + iInc, 7 + iInc)));
            rug.setX(50);
            rug.setY(90 + i * playerGap);
            pane.getChildren().add(rug);
        }



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
                rectangles.get(row * 7 + col).setFill(charToColor(squareColor));
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

    private VBox createPlayerSelectionInterface() {
        Text p = new Text();
        p.setText("Marrakech");
        p.setStyle("-fx-font: 50 arial;");
        p.setX(400);
        p.setY(350);
        Label label = new Label("Select Number of Player");
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 24;");  // Adjust as necessary

        // Create a choice box with player numbers 2 to 4
        ChoiceBox<Integer> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(2, 3, 4);
        choiceBox.setStyle("-fx-font-size: 24;");
        choiceBox.setValue(2);  // Set default value
        choiceBox.setMaxSize(200, 50);
        choiceBox.setMinSize(200, 50);

        // Create a confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setMaxSize(200, 50);
        confirmButton.setMinSize(200, 50);
        //在这里进行了游戏玩家数与棋盘的初始化
        confirmButton.setOnAction(e -> {
            int selectedValue = choiceBox.getValue();
            switch (selectedValue){
                case 2:
                    Marrakech.createGame("Pc03015iPy03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
                    break;
                case 3:
                    Marrakech.createGame("Pc03015iPy03015iPr03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
                    break;
                case 4:
                    Marrakech.createGame("Pc03015iPy03015iPr03015iPp03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
                    break;

            }
            System.out.println("Selected player number: " + selectedValue);
        });

        // Create a VBox to hold the label, choice box and button
        VBox vbox = new VBox(20);  // 20 is the spacing between elements
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        vbox.getChildren().addAll(p,label, choiceBox, confirmButton);
        return vbox;
    }
//旧的代码
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setTitle("Marrakech Viewer");
//        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
//
//        root.getChildren().add(controls);
//
//        makeControls();
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        displayState("Pc05800iPy02400iPp02900iPr00900iA20WBc18r17r19y15y14p08n00r22p07r19p11y11r08c14p29p29y27c04c11c11c14y18c17c25y25y08p25r18y18c17c26c26r21p25c19r12p26p27n00r21p06c21r15p24p27r28r28n00c21");
        VBox vbox = createPlayerSelectionInterface();
        // Set up the scene and stage
        Scene scene = new Scene(vbox, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
