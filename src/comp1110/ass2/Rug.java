package comp1110.ass2;

public class Rug {
    private final Color color;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    public String rugString;
    public static int rugNumber=0;//representing how many rug have been placed
    public static final int rugStringLength = 7;//the length of rug string
    public static final int colorDigit = 0;//the color digit of rug string representing the color of the player placing this rug
    public static final int idTensDigit = 1;//the tens digit of id in rug string
    public static final int idOnesDigit = 2;//the ones digit of id in rug string

    public static final int x1CoordinateDigit = 3;//the x1 coordinate digit of rug string
    public static final int y1CoordinateDigit = 4;//the y1 coordinate digit of rug string
    public static final int x2CoordinateDigit = 5;//the x2 coordinate digit of rug string
    public static final int y2CoordinateDigit = 6;//the y2 coordinate digit of rug string

    // Constructor
    public Rug(int x1, int y1, int x2, int y2, Color color){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
        rugNumber++;
        //build rug String
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(rugStringLength);
        stringBuilder.setCharAt(colorDigit,color.getColor());
        stringBuilder.setCharAt(idTensDigit,Character.forDigit(rugNumber/10,10));
        stringBuilder.setCharAt(idOnesDigit,Character.forDigit(rugNumber%10,10));
        stringBuilder.setCharAt(x1CoordinateDigit,Character.forDigit(x1,10));
        stringBuilder.setCharAt(y1CoordinateDigit,Character.forDigit(y1,10));
        stringBuilder.setCharAt(x2CoordinateDigit,Character.forDigit(x2,10));
        stringBuilder.setCharAt(y2CoordinateDigit,Character.forDigit(y2,10));
        rugString = stringBuilder.toString();
        System.out.println(rugString);
    }
    
    //getters
    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public String getRugString(){
        return rugString;
    }
}
