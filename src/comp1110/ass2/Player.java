package comp1110.ass2;

public class Player {
    private final char colour;
    private int dirhams;	//the currency in use in this game
    private int rugAvailable; //Each player starts the game with 15 rugs
    private boolean inGame;
    public Player(char colour) {
        this.colour = colour;
        this.dirhams = 30;
        this.rugAvailable = 15;
        this.inGame = true;
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

    public void useRug() {
        rugAvailable--;
    }
    /**
     *
     * @param anotherPlayer the player you need to pay
     */
    public void payment(Player anotherPlayer,int amount){
    }
}
