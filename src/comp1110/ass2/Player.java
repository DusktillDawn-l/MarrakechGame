package comp1110.ass2;

public class Player {
    private String colour;
    private int dirhams;	//the currency in use in this game
    private int rugAvailable; //Each player starts the game with 15 rugs

    public Player(String colour) {
        this.colour = colour;
        this.dirhams = 30;
        this.rugAvailable = 15;
    }

    /**
     *
     * @param anotherPlayerColour the colour of another player you need to pay
     */
    public void payment(String anotherPlayerColour){
        return;
    }
}
