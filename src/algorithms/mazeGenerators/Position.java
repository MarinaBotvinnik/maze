package algorithms.mazeGenerators;

/**
 * A class that represents a position in a maze with x and y coordinates.
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getRowIndex() {
        return x;
    }

    public int getColumnIndex() {
        return y;
    }

    @Override
    public String toString() {
        return "{" + x +"," + y +"}";
    }
}
