package comp1110.ass2;

public class Rug {
    private final char color;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int id;
    
    // Constructor
    public Rug(int x1, int y1, int x2, int y2, char color){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
    }

    public Rug(String rug){
        this.color = rug.charAt(0);
        this.id = Integer.parseInt(rug.substring(1,3));
        this.x1 = Integer.parseInt(rug.substring(3,4));
        this.y1 = Integer.parseInt(rug.substring(4,5));
        this.x2 = Integer.parseInt(rug.substring(5,6));
        this.y2 = Integer.parseInt(rug.substring(6,7));
    }
    
    //getters

    public char getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

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
}
