package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static comp1110.ass2.Helper.charToColor;

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private int numberOfRotationsInOneRound =0;
    private int dice = 0;
    private final AtomicInteger round = new AtomicInteger(0);
    private char currentPlayer = 'c';
    private int AIPlayerNumber = 0;
    private int gamePhase = 0;//define when to rotate, roll dice or place rug.
    private int gridSelectedNumber = 0;//define the number of rug being selected during select rug phase.
    private int lastSelectedBoardRow;
    private int lastSelectedBoardColumn;
    String initialGameState = "";

    public void nextRound () {
        round.getAndIncrement();
        this.currentPlayer = Marrakech.getNextPlayer(currentPlayer);
        while (Marrakech.getPlayerFromColor(currentPlayer) == null){
            currentPlayer = Marrakech.getNextPlayer(currentPlayer);
        }
        System.out.println("current player: "+currentPlayer);
        if (isAIplayer(currentPlayer, AIPlayerNumber, Marrakech.playerList.size())){
            AIPlayerTurn();
        }
    }
    public void selectGridToPlace(int row, int column, int currantPhase, char currentPlayer, ArrayList<Rectangle> rectangles){
        if (currantPhase==1){
            //选择grid, gridNumber++
            gridSelectedNumber++;
            rectangles.get((row) * 7 + column).setFill(charToColor(currentPlayer));

            if(gridSelectedNumber ==1){//when select one gird
                lastSelectedBoardRow = row;
                lastSelectedBoardColumn = column;
            }
            if (gridSelectedNumber ==2){//when select two grid
                if ((row == lastSelectedBoardRow && Math.abs(column - lastSelectedBoardColumn) == 1)
                        || (column == lastSelectedBoardColumn && Math.abs(row - lastSelectedBoardRow) == 1)){
                    //when the two places selected can construct a rug
                    Rug rug = new Rug(lastSelectedBoardRow, lastSelectedBoardColumn, row, column, comp1110.ass2.Color.valueOf(String.valueOf(currentPlayer)));
                    while (!Marrakech.isRugValid(Marrakech.getGameString(),rug.toString())){
                        rug = new Rug(lastSelectedBoardRow, lastSelectedBoardColumn, row, column, comp1110.ass2.Color.valueOf(String.valueOf(currentPlayer)));
                    }
                    System.out.println("currentRug: "+rug);
                    if (Marrakech.isPlacementValid(Marrakech.getGameString(),rug.toString()))
                    {
                        Marrakech.makePlacement(Marrakech.getGameString(),rug.toString());
                        Marrakech.rugList.add(rug);
                        gamePhase = 0;
                        nextRound();
                    }
                    else showAlert("Grid selection invalid\nPlease reselect");
                }
                else showAlert("Grid selection invalid\nPlease reselect");
                gridSelectedNumber =0;
                displayState(Marrakech.getGameString());
            }
        }
        else showAlert("Please rotate assam and roll dice first.");
    }
    private void showAlert(String info){
        // Create a confirmation alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, info, ButtonType.YES);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);  // Removes the header
        alert.showAndWait();
    }

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
                square.setOnMouseClicked(event -> {
                    selectGridToPlace((int) ((square.getX()-300)/(boardSize + gapSize)), (int) ((square.getY()-120)/(boardSize + gapSize)), gamePhase,currentPlayer,rectangles);

                });
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
            if (gamePhase==0){
                pressRoll();
            }
            else {showAlert("Please place rug first");}

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

        // Check whether the game is over
        checkWinner(Marrakech.getGameString());

        // Create a Text component for round number
//        Text roundNumber = new Text();
//        roundNumber.setX(1000);
//        roundNumber.setY(50);
//        roundNumber.setFill(Color.BLACK);
//        roundNumber.setFont(Font.font(30));
//        roundNumber.setText("Round " + round.get());
//        pane.getChildren().add(roundNumber);
    }

    // Create players and return the game string
    private void createPlayerSelectionInterface() {
        Text p = new Text();
        p.setText("Marrakech");
        p.setStyle("-fx-font: 50 arial;");
        p.setX(400);
        p.setY(350);
        Label label = new Label("Select Number\nof Player");
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

        Label label2 = new Label("Select Number\nof AI player");
        label2.setMaxWidth(Double.MAX_VALUE);
        label2.setAlignment(Pos.CENTER);
        label2.setStyle("-fx-font-size: 24;");  // Adjust as necessary
        // Create a choice box with ai numbers 0 to 3
        ChoiceBox<Integer> choiceBox2 = new ChoiceBox<>();
        choiceBox2.getItems().addAll(0, 1, 2, 3);
        choiceBox2.setStyle("-fx-font-size: 24;");
        choiceBox2.setValue(0);  // Set default value
        choiceBox2.setMaxSize(200, 50);
        choiceBox2.setMinSize(200, 50);

        // Create a confirm button
        Button confirmButton = new Button("Confirm");
        confirmButton.setMaxSize(200, 50);
        confirmButton.setMinSize(200, 50);
        

        //在这里进行了游戏玩家数与棋盘的初始化
        confirmButton.setOnAction(e -> {
            int selectedValue = choiceBox.getValue();
            AIPlayerNumber = choiceBox2.getValue();
            if (selectedValue <= AIPlayerNumber){
                Alert alert = new Alert(Alert.AlertType.ERROR);

                // Set the properties of the alert
                alert.setTitle("Configuration Error");
                alert.setHeaderText("There must be at least one human player");
                alert.setContentText("Please select again");
                // Show the alert and wait for user response
                alert.showAndWait();
                return;
            }
            switch (selectedValue){
                case 2:
                    Marrakech.createGame("Pc03015iPy03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
                    initialGameState = "Pc03015iPy03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
//                    currentPlayer = Marrakech.playerList.get(0).getColor().getColor();
                    displayState(initialGameState);
                    break;
                case 3:
                    Marrakech.createGame("Pc03015iPy03015iPp03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
                    initialGameState = "Pc03015iPy03015iPp03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
//                    currentPlayer = Marrakech.playerList.get(0).getColor().getColor();
                    displayState(initialGameState);
                    break;
                case 4:
                    Marrakech.createGame("Pc03015iPy03015iPp03015iPr03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
                    initialGameState = "Pc03015iPy03015iPp03015iPr03015iA33NBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
//                    currentPlayer = Marrakech.playerList.get(0).getColor().getColor();
                    displayState(initialGameState);
                    break;
            }

        });

// Create a VBox to hold the label, choice box, and button
        VBox whole = new VBox(20);  // 20 is the spacing between elements
        VBox vbox = new VBox(20);  // 20 is the spacing between elements
        vbox.getChildren().addAll(label, choiceBox);
        VBox vbox1 = new VBox(20);  // 20 is the spacing between elements
        vbox1.getChildren().addAll(label2, choiceBox2);

        // Create an HBox to hold the choice boxes in parallel
        HBox hbox = new HBox(20);  // 20 is the spacing between the choice boxes
        hbox.getChildren().addAll(vbox, vbox1);

        // Add elements to the VBox, including the HBox
        whole.setAlignment(Pos.CENTER);
        whole.setFillWidth(true);
        whole.setPrefSize(300, 300);
        whole.getChildren().addAll(p, hbox, confirmButton);

        // Assuming root is of type Pane or AnchorPane
        root.getChildren().add(whole);

        // Bind layoutX and layoutY properties of the VBox to center it in root
        whole.setLayoutX((WINDOW_WIDTH - whole.getPrefWidth()) / 2 - 50);
        whole.setLayoutY((WINDOW_HEIGHT - whole.getPrefHeight()) / 2 - 25);
    }

    public void pressRoll(){
        int randomDiceValue = Marrakech.rollDie();
        dice = randomDiceValue;
        Marrakech.assam.move(randomDiceValue);

        char boardColor = Marrakech.board.getColor(Marrakech.assam.getX(), Marrakech.assam.getY());
        Player p = Marrakech.getPlayerFromColor(currentPlayer);
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
        if (p.getInGame() == 'i'){
            gamePhase = 1;
        } else {
            nextRound();
        }
        displayState(Marrakech.getGameString());
    }

    // check game over and display the winner
    public void checkWinner(String gameState) {
        char winner = Marrakech.getWinner(gameState);
        if (winner != 'n') {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("The winner is Player " + winner + " !!");
            alert.setContentText("Please quit the game");
            alert.showAndWait();
            return;
        }
        System.out.println("Winner is Player " + winner);
    }

    // Check whether the current player is AI
    public boolean isAIplayer (char currentPlayer, int AIPlayerNumber, int playerNumber) {
        String fixedOrder = "cypr";

        String currentPlayers = fixedOrder.substring(0, playerNumber);
        String aiPlayers = currentPlayers.substring(playerNumber - AIPlayerNumber);
        // Check if the currentPlayer is among the AI players
        return aiPlayers.indexOf(currentPlayer) != -1;
    }

    // AI player's turn
    public void AIPlayerTurn () {
        // roll dice first
        pressRoll();
        // place rug in random position
        int randomX = 0;
        int randomY = 0;
        int randomX2 = 0;
        int randomY2 = 0;
        String testRug = "";
        int i = 0;
        while (!Marrakech.isPlacementValid(Marrakech.getGameString(),testRug) && i < 100){
            int x = Marrakech.assam.getX();
            int y = Marrakech.assam.getY();
            Random r = new Random();
            randomX = (r.nextBoolean() ? 1 : -1) + x;
            randomY = (r.nextBoolean() ? 1 : -1) + y;
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};
            int index = r.nextInt(4);
            // Generate a random neighbor
            randomX2 = randomX + dx[index];
            randomY2 = randomY + dy[index];
            i++;
            testRug = currentPlayer + "99" + randomX + randomY + randomX2 + randomY2;
        }
        if (i == 100){
            System.out.println(testRug);
            throw new RuntimeException("AI player cannot find a valid rug");}
        System.out.println(testRug);
        Rug rug = new Rug(randomX, randomY, randomX2, randomY2, comp1110.ass2.Color.valueOf(String.valueOf(currentPlayer)));
        System.out.println("currentRug: "+rug);
        Marrakech.makePlacement(Marrakech.getGameString(),rug.toString());
        Marrakech.rugList.add(rug);

        // Go to next player's turn
        gamePhase = 0;
        nextRound();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        createPlayerSelectionInterface();
//        String testStr = "Pc02608iPy01908iPp02108iPr05209iA30SBn00n00r11c09c09n00n00r07r04r11r08c02r00r00r07r04n00y12y12r02p01p05y11y11p11c07r02p01p05y08y08p11r10r10y10n00c08n00c00n00p08y10n00n00n00c00n00c10c10";
//        Marrakech.createGame(testStr);
//        displayState(testStr);
//         Set up the scene and stage
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
