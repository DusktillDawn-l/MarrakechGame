package comp1110.ass2.test;

import static comp1110.ass2.Helper.*;
import static org.junit.Assert.*;
import javafx.scene.paint.Color;

import org.junit.Test;
public class HelperTest {
    @Test
    public void testCharToColor(){
        assertEquals(charToColor('c'), Color.CYAN);
        assertEquals(charToColor('y'), Color.YELLOW);
        assertThrows(IllegalArgumentException.class, () -> {
            charToColor('s');
        });
    }
}
