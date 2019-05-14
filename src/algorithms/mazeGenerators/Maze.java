package algorithms.mazeGenerators;

import java.nio.ByteBuffer;
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

    public Maze(byte[] arr)
    {
     byte[] tmp = new byte[4];
     putBigToSmall(tmp, arr,0);
     m_id= decompressByte(tmp);
     putBigToSmall(tmp,arr,4);
     rows = decompressByte(tmp);
     putBigToSmall(tmp,arr,8);
     columns = decompressByte(tmp);
     m_maze = fillTheMaze(arr);
     start = createPosition(arr,arr.length-16);
     end = createPosition(arr, arr.length-8);
    }

    private Position createPosition(byte[] arr, int place){
        byte[] tmp = new byte[4];
        putBigToSmall(tmp,arr,place);
        int tmpX = decompressByte(tmp);
        putBigToSmall(tmp,arr,place+4);
        int tmpY = decompressByte(tmp);
        Position position = new Position(tmpX,tmpY);
        return position;
    }

    private int[][] fillTheMaze(byte[] arr){
        int[][] maze = new int[rows][columns];
        int place = 12;
        for(int i=0; i<rows; i++)
            for(int j=0; j<columns; j++){
                maze[i][j] = arr[place];
                place++;
            }
        return maze;
    }

    private void putBigToSmall(byte[] small, byte[]big, int place){
        for(int i =0; i<small.length; i++) {
            small[i] = big[place];
            place++;
        }
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

    public int getM_id() {
        return m_id;
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
    private int putInArray (byte[] arr, byte[] input, int k){
        for(int i=0; i< input.length; i++){
            arr[k] = input[i];
            k++;
        }
        return k;
    }

    /**
     * compressing int into two cell byte array
     * @param num
     * @return
     */
    private byte[] compressInt(int num){
        byte[] tmp = new byte[16];
        int i=0;
        // turn int into binary num.
        while(num>0){
            tmp[i] = (byte)(num%2);
            num=num/2;
            i++;
        }
        byte [] compressed = new byte [4];
        int one = 0;
        int twos =0;
        for(int k=0; k<4; k++){
            one = one + (int)tmp[k]*(int)(Math.pow(2,twos));
            twos++;
        }
        compressed[0] = (byte)one;
        one=0;
        twos=0;
        for(int k=4; k<8; k++){
            one = one + (int)tmp[k]*(int)(Math.pow(2,twos));
            twos++;
        }
        compressed[1] = (byte)one;
        one=0;
        twos=0;
        for(int k=8; k<12; k++){
            one = one + (int)tmp[k]*(int)(Math.pow(2,twos));
            twos++;
        }
        compressed[2] = (byte)one;
        one=0;
        twos=0;
        for(int k=12; k<16; k++){
            one = one + (int)tmp[k]*(int)(Math.pow(2,twos));
            twos++;
        }
        compressed[3] = (byte)one;
        return compressed;
    }

    private int decompressByte( byte[] arr){
        byte[] binary = new byte[16];
        for(int i=0; i<4; i++){
            binary[i]= (byte)(arr[0]%2);
            arr[0] = (byte)(arr[0]/2);
        }
        for(int i=4; i<8; i++){
            binary[i]= (byte)(arr[1]%2);
            arr[1] = (byte)(arr[1]/2);
        }
        for(int i=8; i<12; i++){
            binary[i]= (byte)(arr[2]%2);
            arr[2] = (byte)(arr[2]/2);
        }
        for(int i=12; i<16; i++){
            binary[i]= (byte)(arr[3]%2);
            arr[3] = (byte)(arr[3]/2);
        }
        int res =0 ;
        int twos =0;
        for(int i=0; i<16;i++) {
            res = res + ((int)(binary[i])) * ((int)(Math.pow(2, twos)));
            twos++;
        }
        return res;
    }

    public byte[] toByteArray() {
        int size = 12 + rows * columns + 16;
        byte[] data = new byte[size];
        byte[] id= compressInt(m_id);
        byte[] row = compressInt(rows);
        byte[] column = compressInt(columns);
        byte[] startX = compressInt(start.getRowIndex());
        byte[] startY = compressInt(start.getColumnIndex());
        byte[] endX = compressInt(end.getRowIndex());
        byte[] endY = compressInt(end.getColumnIndex());
        int i=0;
        for(int k=0; k<4; k++){
            Integer tmpID= new Integer()
        }
        i = putInArray(data, id,i);
        i = putInArray(data,row,i);
        i = putInArray(data,column,i);
        for (int j = 0; j < rows; j++)
            for (int k = 0; k < columns; k++) {
                Integer tmp = new Integer(m_maze[j][k]);
                data[i] = tmp.byteValue();
                i++;
            }
        i = putInArray(data,startX,i);
        i = putInArray(data,startY,i);
        i = putInArray(data,endX,i);
        putInArray(data,endY,i);
        return data;
    }
}
