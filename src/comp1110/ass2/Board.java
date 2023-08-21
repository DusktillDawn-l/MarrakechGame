package comp1110.ass2;
import java.util.ArrayList;
public class Board {
    private char[][] squareColor = new char[7][7];
    private ArrayList<Player> playerList = new ArrayList<Player>();
    public Board(){

    }

    //getter
    public char getColor(int x, int y){
        return squareColor[x][y];
    }

    //set color
    public void setColor(int x, int y, char color){
        squareColor[x][y] = color;
    }

    //check how many same color rugs are connected with the given coordinate.
    public int connectedRugs (int x, int y){
        return 1;
    }

    // Initialise new player here and add it to player list
    public void addPlayer (char color){
        //TODO: check valid
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
}
