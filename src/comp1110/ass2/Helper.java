package comp1110.ass2;
import javafx.scene.paint.Color;

// Class for helper functions
public class Helper {

    // Convert color char to javafx Color
    public static Color charToColor(char character) {
        return switch (character) {
            case 'c' -> Color.CYAN;
            case 'y' -> Color.YELLOW;
            case 'r' -> Color.RED;
            case 'p' -> Color.PURPLE;
            default -> throw new IllegalArgumentException("Invalid character: " + character);
        };
    }
}
