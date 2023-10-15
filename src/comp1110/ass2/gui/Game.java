package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static comp1110.ass2.Helper.charToColor;
import static comp1110.ass2.Helper.charToColorStr;

public class Game extends Application {


    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private int numberOfRotationsInOneRound =0;
    private int dice = 0;
    private final AtomicInteger round = new AtomicInteger(0);
    private char currentPlayer = 'n';
    String initialGameState = "";

    void displayState(String state) {
        int boardSize = 70;
        double gapSize = 0;
        Pane pane = new Pane();
        ArrayList<Rectangle> rectangles = new ArrayList<>();

        Image boardImage = new Image("file:assets/Board Image.png");
        ImageView boardView = new ImageView(boardImage);
        boardView.setX(200);
        boardView.setY(20);
        boardView.setFitHeight(680);
        boardView.setFitWidth(680);
        pane.getChildren().add(boardView);


        for (int col = 0; col < 7; col++) {
            for(int row = 0; row < 7; row++) {
                double x = 300 + col * (boardSize + gapSize);
                double y = 120 + row * (boardSize + gapSize);

                Rectangle square = new Rectangle((int) x, (int) y, boardSize, boardSize);
                square.setFill(Color.ORANGE);
                square.setStroke(Color.BLACK);
                rectangles.add(square);
                pane.getChildren().add(square);
            }
        }

        // 创建左转箭头
        Polygon leftArrow = new Polygon();
        leftArrow.getPoints().addAll(
                1000.0, 300.0,  // 左侧顶点
                1050.0, 350.0,  // 下方顶点
                1050.0, 250.0   // 上方顶点
        );
        leftArrow.setFill(Color.BLACK);

        // 创建右转箭头
        Polygon rightArrow = new Polygon();
        rightArrow.getPoints().addAll(
                1150.0, 300.0,  // 右侧顶点
                1100.0, 350.0,  // 下方顶点
                1100.0, 250.0   // 上方顶点
        );
        rightArrow.setFill(Color.BLACK);

        leftArrow.setOnMouseClicked(e -> {
                // 这里执行左转操作
            if (numberOfRotationsInOneRound >=0){
                Marrakech.rotateAssam(Marrakech.assam.toString(), 270);
                numberOfRotationsInOneRound--;
                displayState(Marrakech.getGameString());
            }
        });

        rightArrow.setOnMouseClicked(e -> {
            // 这里执行右转操作
            if (numberOfRotationsInOneRound <=0){
                Marrakech.rotateAssam(Marrakech.assam.toString(), 90);
                numberOfRotationsInOneRound++;
                displayState(Marrakech.getGameString());
            }
        });

        pane.getChildren().addAll(leftArrow, rightArrow);

        // Create a Rectangle component to represent the dice
        Rectangle diceSquare = new Rectangle(1007, 620, 85, 55);

        diceSquare.setFill(Color.RED);
        Text roll = new Text("ROLL");
        roll.setX(1012);
        roll.setY(660);
        roll.setFont(Font.font(30));

        // Create a Text component to display the number of the side of the dice


        roll.setOnMouseClicked(e -> {
            // Set the default value of the die to 1


            int randomDiceValue = Marrakech.rollDie();
            dice = randomDiceValue;
            Marrakech.assam.move(randomDiceValue);

            // Calculate payment
            Marrakech.updateBoard(state);
            char boardColor = Marrakech.board.getColor(Marrakech.assam.getX(), Marrakech.assam.getY());
            int activePlayerNo = Marrakech.getActivePlayerNo();
            int index = round.get() % activePlayerNo;
            System.out.println("playerList为"+Marrakech.playerList);
            round.getAndIncrement();
            System.out.println("目前为第"+round+"轮次");
            Player p = Marrakech.playerList.get(index);
            currentPlayer = Marrakech.playerList.get((index + 1) % activePlayerNo).getColor().getColor();
            System.out.println("当前走完的玩家为"+p);
            System.out.println("当前踩到的棋盘颜色为"+boardColor);
            if (boardColor != p.getColor().getColor() && boardColor != 'n') {
                System.out.println(Marrakech.getGameString());
                Player anotherPlayer = Marrakech.getPlayerFromColor(boardColor);
                int amount = Marrakech.getPaymentAmount(Marrakech.getGameString());
                if (amount > p.getDirhams()) {
                    amount = p.getDirhams();
                    p.quitGame();
                    System.out.println("Player " + p.getColor() + " quit the game");
                }
                p.payment(anotherPlayer, amount);
                System.out.println("Player " + p.getColor() + " paid " + anotherPlayer.getColor() + " " + amount + " dirhams");
            }


            numberOfRotationsInOneRound = 0;
            displayState(Marrakech.getGameString());
        });

        // Paint diceSquare first
        pane.getChildren().addAll(diceSquare, roll);

        //切换到displayState的界面
        root.getChildren().clear();
        root.getChildren().add(pane);



        String[] splits = state.split("A");
        String playerStr = splits[0];
        for (int i = 0; i < playerStr.length() / 8; i++) {
            int iInc = i * 8;
            int playerGap = 130;
            // add player color
            Text p = new Text();
            p.setText("Player " + playerStr.charAt(iInc + 1));
            // Set the color of the text to gray if the player has quit
            if (playerStr.charAt(iInc + 7) == 'o'){
                p.setFill(Color.GRAY);
            } else {
                p.setFill(charToColor(playerStr.charAt(iInc + 1)));
                // Add a triangle pointing to the current player
                if (currentPlayer == playerStr.charAt(iInc + 1) ||
                        (currentPlayer == 'n' && i == 0)){
                    // create a triangle pointing to the current player using i
                    Polygon triangle = new Polygon();
                    triangle.getPoints().addAll(
                            10.0, 30.0 + i * playerGap,
                            10.0, 50.0 + i * playerGap,
                            50.0, 40.0 + i * playerGap
                    );
                    pane.getChildren().add(triangle);
                }
            }
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

        double assamX = 300 + assamRow * (boardSize + gapSize) + (double) boardSize / 2;
        double assamY = 120 + assamCol * (boardSize + gapSize) + (double) boardSize / 2;

        // Create an arrow pointing upward as default
        Polygon assamArrow = new Polygon();
        assamArrow.getPoints().addAll(
                assamX, assamY - boardSize / 5,  // Tip of the arrow
                assamX - boardSize / 5, assamY + boardSize / 5,  // Bottom left corner
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

        // Create a Text component to display the number of the side of the dice
        Text diceNumber = new Text();
        diceNumber.setX(1100);
        diceNumber.setY(660);
        diceNumber.setFill(Color.BLACK);
        diceNumber.setFont(Font.font(36));
        diceNumber.setText("→ " + dice);
        pane.getChildren().add(diceNumber);
        pane.getChildren().addAll(assamArrow);

        // Create a Text component for round number
        Text roundNumber = new Text();
        roundNumber.setX(1000);
        roundNumber.setY(50);
        roundNumber.setFill(Color.BLACK);
        roundNumber.setFont(Font.font(30));
        roundNumber.setText("Round " + round.get());
        pane.getChildren().add(roundNumber);
    }

    // Create players and return the game string
    private void createPlayerSelectionInterface() {
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
//                    displayState("Pc03015iPy03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
                    initialGameState = "Pc03015iPy03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
                    displayState(initialGameState);
                    break;
                case 3:
                    Marrakech.createGame("Pc03015iPy03015iPr03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
//                    displayState("Pc03015iPy03015iPr03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
                    initialGameState = "Pc03015iPy03015iPr03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
                    displayState(initialGameState);
                    break;
                case 4:
                    Marrakech.createGame("Pc03015iPy03015iPr03015iPp03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
//                    displayState("Pc03015iPy03015iPr03015iPp03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
                    initialGameState = "Pc03015iPy03015iPr03015iPp03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
                    displayState(initialGameState);
                    break;
            }

        });

        // Create a VBox to hold the label, choice box and button
        VBox vbox = new VBox(20);  // 20 is the spacing between elements
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        vbox.getChildren().addAll(p, label, choiceBox, confirmButton);

        // Set a preferred size for the VBox (optional, but helps with centering)
        vbox.setPrefSize(300, 300);  // Example dimensions, adjust as needed

        // Assuming root is of type Pane or AnchorPane
        root.getChildren().add(vbox);

        // Bind layoutX and layoutY properties of the VBox to center it in root
        vbox.setLayoutX((WINDOW_WIDTH - vbox.getPrefWidth()) / 2);
        vbox.setLayoutY((WINDOW_HEIGHT - vbox.getPrefHeight()) / 2);
    }

    // check game over and display the winner
    public boolean checkWinner(String gameState) {
        char winner = Marrakech.getWinner(gameState);
        if (winner == 'n') { return false; }
        Text p = new Text();
        p.setText("Winner is Player " + charToColorStr(winner));
        p.setStyle("-fx-font: 50 arial;");
        p.setFill(charToColor(winner));
        p.setX(400);
        p.setY(350);
        root.getChildren().add(p);
        return true;
    }

    public void gameStart(String gameState){
        createPlayerSelectionInterface();

        while (!checkWinner(gameState)){
            for (Player p : Marrakech.playerList){
                displayState(gameState);
                // rotate Assam
                // move Assam
                // make payment
                Marrakech.updateBoard(gameState);
                char currentColor = Marrakech.board.getColor(Marrakech.assam.getX(), Marrakech.assam.getY());
                if (currentColor != p.getColor().getColor()) {
                    Player anotherPlayer = Marrakech.getPlayerFromColor(currentColor);
                    p.payment(anotherPlayer, Marrakech.getPaymentAmount(gameState));
                }
                // place rug
            }
        }


    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // createPlayerSelectionInterface();
        String testStr = "Pc03407iPy06907iPp01207iPr00407iA14NBn00n00r03p05y06r11p08n00n00n00p05c13r11p09r15r15p15y17r13y10y10n00n00y03y17r13p11p11n00c05p04y09y15y15y12n00p16n00y09c14c14y12n00p16n00n00p12p12n00";
        Marrakech.createGame(testStr);
        displayState(testStr);
        // Set up the scene and stage
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
