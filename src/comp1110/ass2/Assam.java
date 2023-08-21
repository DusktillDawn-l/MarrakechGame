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

    public Assam(){

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

    }

    public void move(int steps){
    }
}
