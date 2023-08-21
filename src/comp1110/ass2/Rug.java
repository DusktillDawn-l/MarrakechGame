package comp1110.ass2;

public class Rug {
    private char color;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    
    // Constructor
    public Rug(int x1, int y1, int x2, int y2, char color){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
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
}
