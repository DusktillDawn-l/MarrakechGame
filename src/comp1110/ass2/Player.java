package comp1110.ass2;

import java.util.Arrays;

public class Player {
    private final char colour;
    private int dirhams;	//the currency in use in this game
    private int rugAvailable; //Each player starts the game with 15 rugs
    private boolean inGame;
    private String playerString;
    private final int playerStringLength = 8;
    private final static int playerDigit= 0;
    private final static int colorDigit=1;
    private final static int dirhamsHundredsDigit=2;
    private final static int dirhamsTensDigit=3;
    private final static int dirhamsOnesDigit=4;
    private final static int rugTensDigit=5;
    private static final int rugOnesDigit=6;
    private final static int inGameDigit=7;
    public Player(char colour) {
        this.colour = colour;
        this.dirhams = 30;
        this.rugAvailable = 15;
        this.inGame = true;
        //构建playerString
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(playerStringLength);
        stringBuilder.setCharAt(playerDigit,'p');
        stringBuilder.setCharAt(colorDigit,colour);
        stringBuilder.setCharAt(dirhamsHundredsDigit,'0');
        stringBuilder.setCharAt(dirhamsTensDigit,'3');
        stringBuilder.setCharAt(dirhamsOnesDigit,'0');
        stringBuilder.setCharAt(rugTensDigit,'1');
        stringBuilder.setCharAt(rugOnesDigit,'5');
        stringBuilder.setCharAt(inGameDigit,'i');
        playerString = stringBuilder.toString();
    }

    public char getColour() {
        return colour;
    }

    public int getDirhams() {
        return dirhams;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void quitGame() {
        this.inGame = false;
    }

    public int getRugAvailable() {
        return rugAvailable;
    }

    public void placeRug() {
        rugAvailable--;
    }
    /**
     *
     * @param anotherPlayer the player you need to pay
     */
    public void payment(Player anotherPlayer,int amount){
    }
    public String getPlayerString(){
        return playerString;
    }
}
