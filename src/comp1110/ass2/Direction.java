package comp1110.ass2;

public enum Direction {
    N('N'),//north
    E('E'),//east
    S('S'),//south
    W('W');//west
    public char direction;
    Direction(char direction){
        this.direction = direction;
    }
    public char getDirection(){
        return direction;
    }
}
