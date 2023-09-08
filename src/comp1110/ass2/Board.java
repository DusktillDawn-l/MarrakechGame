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
