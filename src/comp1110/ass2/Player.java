package comp1110.ass2;

import java.util.Arrays;

public class Player {
    private final char colour;
    private int dirhams;	//the currency in use in this game
    private int rugAvailable; //Each player starts the game with 15 rugs
    private char inGame;

    public Player(char colour) {
        this.colour = colour;
        this.dirhams = 30;
        this.rugAvailable = 15;
        this.inGame = 'i';
    }
    public Player (String s){
        if (s.charAt(0) != 'P') {
            throw new RuntimeException("Invalid player string");
        }
        this.colour = s.charAt(1);
        this.dirhams = Integer.parseInt(s.substring(2,5));
        this.rugAvailable = Integer.parseInt(s.substring(5,7));
        this.inGame = s.charAt(7);;
    }
    public char getColour() {
        return colour;
    }

    public int getDirhams() {
        return dirhams;
    }

    public char getInGame() {
        return inGame;
    }

    public void quitGame() {
        this.inGame = 'o';
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

    @Override
    public String toString() {
        StringBuilder playerString = new StringBuilder();

        playerString.append('P');
        playerString.append(colour);

        // Format the dirhams to be a 3-digit number
        String formattedDirhams = String.format("%03d", dirhams);
        playerString.append(formattedDirhams);

        String formattedRugsRemaining = String.format("%02d", rugAvailable);
        playerString.append(formattedRugsRemaining);
        playerString.append(inGame);

        return playerString.toString();

    }
}
