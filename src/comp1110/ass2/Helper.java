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

    // Convert color char to color string
    public static String charToColorStr(char character) {
        return switch (character) {
            case 'c' -> "Cyan";
            case 'y' -> "Yellow";
            case 'r' -> "Red";
            case 'p' -> "Purple";
            default -> throw new IllegalArgumentException("Invalid character: " + character);
        };
    }

}
