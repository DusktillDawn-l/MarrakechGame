package comp1110.ass2.test;

import comp1110.ass2.Color;
import comp1110.ass2.Rug;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class RugTest {
    private Rug rug;

    @Before
    public void setUp() {
        rug = new Rug(0, 0, 0, 1, Color.r);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(0, rug.getX1());
        assertEquals(0, rug.getY1());
        assertEquals(0, rug.getX2());
        assertEquals(1, rug.getY2());
        assertEquals(Color.r, rug.getColor());
    }

    @Test
    public void testToString() {
        assertEquals("r010001", rug.toString());
    }

    @Test
    public void testGetAbbrRugString() {
        assertEquals("r03", rug.getAbbrRugString());
    }

}
