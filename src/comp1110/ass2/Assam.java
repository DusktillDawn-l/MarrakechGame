package comp1110.ass2;

enum Direction {
        N,
        E,
        S,
        W
}

public class Assam {
    private int x;
    private int y;
    Direction dir;

    public Assam(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
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
}
