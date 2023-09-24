package comp1110.ass2.test;

import static org.junit.Assert.*;

import comp1110.ass2.Color;
import comp1110.ass2.Player;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        player = new Player(Color.c);
    }

    @Test
    public void testDefaultConstructor() {
        // Test the default constructor
        assertEquals(Color.c, player.getColor());
        assertEquals(30, player.getDirhams());
        assertEquals(15, player.getRugAvailable());
        assertEquals('i', player.getInGame());
    }

    @Test
    public void testStringConstructor() {
        // Test the constructor that takes a playerString
        String playerString = "Pr00803i";
        Player newPlayer = new Player(playerString);
        assertEquals(Color.r, newPlayer.getColor());
        assertEquals(8, newPlayer.getDirhams());
        assertEquals(3, newPlayer.getRugAvailable());
        assertEquals('i', newPlayer.getInGame());
    }

    @Test
    public void testQuitGame() {
        // Test the quitGame method
        player.quitGame();
        assertEquals('o', player.getInGame());
    }

    @Test
    public void testPlaceRug() {
        // Test the placeRug method
        player.placeRug();
        assertEquals(14, player.getRugAvailable());
    }


    @Test(expected = RuntimeException.class)
    public void testInvalidStringConstructor() {
        // Test the constructor with an invalid playerString
        String invalidString = "InvalidString";
        Player newPlayer = new Player(invalidString);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidPlayerStringColor() {
        // Test the constructor with an invalid color in playerString
        String invalidString = "PZ003150i";
        Player newPlayer = new Player(invalidString);
    }
}
