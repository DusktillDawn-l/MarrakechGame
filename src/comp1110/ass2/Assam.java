package comp1110.ass2;

public class Assam {
    private int x;
    private int y;
    Direction dir;
    private String assamString;//the assam string
    private static final int assamStringLength = 4;//the length of assam string
    public static final int assamDigit = 0;//the digit representing this string is a assam string
    public static final int xCoordinateDigit = 1;//the x coordinate digit of assam string
    public static final int yCoordinateDigit = 2;//the y coordinate digit of assam string
    public static final int orientationDigit = 3;//the orientation digit of assam string

    public Assam(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    public Assam(String string){
        if (string.charAt(assamDigit)=='A'&&string.length()==assamStringLength)
        {
            this.x = Character.getNumericValue(string.charAt(xCoordinateDigit));
            this.y = Character.getNumericValue(string.charAt(yCoordinateDigit));
            this.dir = Direction.valueOf(String.valueOf(string.charAt(orientationDigit)));
        }
        else
            throw new RuntimeException("Invalid Assam String");
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

    public String toString(){
        return "A" +
                x +
                y +
                dir;
    }
}
