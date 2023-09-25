package comp1110.ass2.test;
import comp1110.ass2.Assam;
import comp1110.ass2.Color;
import comp1110.ass2.Direction;
import comp1110.ass2.Player;
import org.junit.Before;
import org.junit.Test;

import static comp1110.ass2.Helper.charToColor;
import static org.junit.Assert.*;

public class AssamTest {
    private Assam a;
    private final String aStr = "A11N";

    @Before
    public void setUp() {
        a = new Assam(aStr);
    }

    @Test
    public void testDefaultConstructor() {
        // Test the default constructor
        assertEquals(1, a.getX());
        assertEquals(1, a.getY());
        assertEquals(Direction.N, a.getDirection());
    }

    @Test
    public void testMove() {
        a.move(4);
        assertEquals(4, a.getY());
        a.changeDirection(Direction.W);
        a.move(1);
        assertEquals(0, a.getX());
        a.move(1);
        assertEquals(6, a.getX());
    }

    @Test
    public void testInvalidMove() {
        assertThrows(RuntimeException.class, () -> {
            a.move(0);
        });
        assertThrows(RuntimeException.class, () -> {
            a.move(10);
        });
    }
    @Test
    public void testToString() {
        // Test the default constructor
        assertEquals(aStr, a.toString());
    }

    // Todo: add more tests
}
