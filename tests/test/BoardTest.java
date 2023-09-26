package test;

import comp1110.ass2.Board;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board board;

    @BeforeEach
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

