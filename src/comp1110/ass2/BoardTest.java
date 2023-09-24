package comp1110.ass2;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testDefaultConstructor() {
        // Test the default constructor
        String expected = "Bn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
        assertEquals(expected, board.toString());
    }

    @Test
    public void testStringConstructor() {
        // Test the constructor that takes a boardString
        String boardString = "Bn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
        Board newBoard = new Board(boardString);
        assertEquals(boardString, newBoard.toString());
    }

    @Test
    public void testSetBoard() {
        // Test the setBoard method
        board.setBoard(2, 3, "ABC");
        String expected = "Bn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00ABCn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
        assertEquals(expected, board.toString());
    }
}

