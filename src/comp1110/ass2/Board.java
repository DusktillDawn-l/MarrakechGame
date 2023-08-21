package comp1110.ass2;

public class Board {
    private char[][] squareColor = new char[7][7];

    public Board()

    //getter
    public char getColor(int x, int y){
        return squareColor[x][y];
    }

    //set color
    public void setColor(int x, int y, char color)

    //check how many same color rugs are connected with the given coordinate.
    public int connectedRugs (int x, int y)
}
