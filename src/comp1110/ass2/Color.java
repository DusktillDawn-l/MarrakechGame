package comp1110.ass2;

public enum Color {
    c('c'),//cyan
    y('y'),//yellow
    r('r'),//red
    p('p');//purple
    private char color;

    Color(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }
}
