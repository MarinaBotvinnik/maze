package algorithms.mazeGenerators;

/**
 * A class that creates an empty maze(with no walls)
 */
public class EmptyMazeGenerator extends AMazeGenerator {
    /**
     * The function creates a maze that filled with no walls at all.
     * @param rows (num of rows)
     * @param columns (num of columns)
     * @return an empty(all zeroes) Maze
     */
    @Override
    public Maze generate(int rows, int columns) {
        if(rows == 0 || columns ==0){
            Maze maze = new Maze (5,5);
            return maze;
        }
        Maze maze = new Maze(rows, columns);
        return maze;
    }
}
