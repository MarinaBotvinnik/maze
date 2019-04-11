package algorithms.mazeGenerators;

import java.util.Arrays;

/**
 * A class that describes a maze with five parameters:
 * 1. number of rows
 * 2. number of columns
 * 3. a two demention array when 0 value in a cell is a path and 1 is a wall
 * 4. a position of the start point
 * 5. a position of the end point
 */
public class Maze {
    private int rows;
    private int columns;
    private int[][] m_maze;
    private Position start;
    private Position end;

    /**
     * default constractor that creates a maze in the size given
     * default start at maze[0][0]
     * default end at maze[rows-1][colum
     * @param rows
     * @param columns
     */
    public Maze(int rows, int columns) {
        if(rows == 0 || columns == 0){
            this.rows = 5;
            this.columns = 5;
        }
        else {
            this.rows = rows;
            this.columns = columns;
        }
        this.m_maze =  new int[this.rows][this.columns];
        start = new Position(0,0);
        end = new Position(this.rows-1,this.columns-1);
    }

    /**
     * Sets the start position with the given coordinates.
     * @param x
     * @param y
     */
    public void setStartPosition(int x, int y) {
        if(x>=0 && x<rows && y>=0 && y<columns) {
            Position tmp = new Position(x, y);
            start = tmp;
        }
        else start = new Position(0,0);
    }

    /**
     * Sets the end position with the given coordinates.
     * @param x
     * @param y
     */
    public void setGoalPosition(int x, int y) {
        if(x>=0 && x<rows && y>=0 && y<columns) {
            Position tmp = new Position(x, y);
            end = tmp;
        }
        else end = new Position(rows-1,columns-1);
    }

    public int[][] getM_maze() {
        return m_maze;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /**
     * Prints the maze exactly like it is - two dimensional array filled with zeroes and ones.
     */
    public void print() {
        for(int i=0; i<rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(i == start.getRowIndex() && j == start.getColumnIndex())
                    System.out.print("S");
                else if (i == end.getRowIndex() && j == end.getColumnIndex())
                    System.out.print("E");
                else System.out.print(""+m_maze[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Prints the maze array convenient to the user(GUI version)
     */
    public void printVer2() {
        for (int i = 0; i < m_maze.length; i++) {
            for (int j = 0; j < m_maze[i].length; j++) {
                if (i == start.getRowIndex() && j == start.getColumnIndex()) {//startPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                } else if (i == end.getRowIndex() && j == end.getColumnIndex()) {//goalPosition
                    System.out.print(" " + "\u001B[44m" + " ");
                } else if (m_maze[i][j] == 1) System.out.print(" " + "\u001B[45m" + " ");
                else System.out.print(" " + "\u001B[107m" + " ");
            }
            System.out.println(" " + "\u001B[107m");
        }

    }

    /**
     * a function gets the start position of the maze
     * @return  start Position
     */
    public Position getStartPosition()
    {
        Position tmp = new Position(start.getRowIndex(), start.getColumnIndex());
        return tmp;
    }

    /**
     * a function gets the goal position of the maze
     * @return goal Position
     */
    public Position getGoalPosition()
    {
        Position tmp = new Position(end.getRowIndex(),end.getColumnIndex());
        return tmp;
    }

    /**
     * sets a value in the maze in the given coordinates.
     * @param row (coordinate of the row)
     * @param column (coordinate of the column)
     * @param value (0 or 1)
     */
    public void setValueInMaze(int row, int column, int value)
    {
        if(row<rows && row>=0 && column< columns && column>=0 && (value == 0 || value == 1))
            m_maze[row][column] = value;
    }
}
