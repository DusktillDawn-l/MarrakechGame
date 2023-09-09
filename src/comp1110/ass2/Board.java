package comp1110.ass2;

public class Board {
    private static final int row = 7;
    private static final int column = 7;
    public static String[][] board = new String[row][column];
    public Board() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                board[i][j] = "n00";
            }
        }
    }
    //initial board with board string
    public Board(String boardString){
        if (boardString.charAt(0)!='B'||boardString.length()!=148)
            throw new RuntimeException("Invalid board string");
        else {
            boardString = boardString.substring(1);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    board[i][j]= boardString.substring(i*3,(i+1)*3);
                }
            }
        }
    }

    /**
     *
     * @param row the coordinate of row
     * @param column the coordinate of column
     * @param abbrRugString the abbreviation of rug string
     */
    public void setBoard(int row,int column,String abbrRugString){
        board[row][column] = abbrRugString;
    }

    @Override
    public String toString() {
        String boardString="B";
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                boardString+=board[i][j];
            }
        }
        return boardString;
    }
}
