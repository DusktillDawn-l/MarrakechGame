package comp1110.ass2;

public class Assam {
    private int x;
    private int y;
    Direction dir;
    private String assamString;
    private final int assamStringLength = 4;
    public static final int assamDigit = 0;
    public static final int xCoordinateDigit = 1;
    public static final int yCoordinateDigit = 2;
    public static final int orientationDigit = 3;

    public Assam(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(assamStringLength);
        stringBuilder.setCharAt(assamDigit,'A');
        stringBuilder.setCharAt(xCoordinateDigit,Character.forDigit(x,10));
        stringBuilder.setCharAt(yCoordinateDigit,Character.forDigit(y,10));
        String direction = dir.toString();
        stringBuilder.setCharAt(orientationDigit,direction.charAt(0));
        assamString = stringBuilder.toString();
    }


    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Direction getDirection() {
        return this.dir;
    }

    public void changeDirection(Direction d){
        return;
    }

    public void move(int steps){
        return;
    }

    public String getAssamString(){
        return assamString;
    }
}
