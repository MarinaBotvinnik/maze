package algorithms.mazeGenerators;

/**
 * An abstract class for all Maze Generators with two functions.
 */

public abstract class AMazeGenerator implements IMazeGenerator {
    @Override
    /**
     *  A function(The main function) that in charge of the creation of the maze.
     *  (Abstract function that can be implemented in different ways)
     *  input: num of rows, num of columns
     *  Output: an object of type Maze
     */
     public abstract Maze generate(int rows, int columns);

    /**
     * A function that measuring the time in millis that takes to create a maze(a.k.a to run the "generate" function)
     * @param rows (num of rows of the maze)
     * @param columns (num of columns of the maze)
     * @return the time in millis
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        if(rows == 0 || columns == 0)
            return 0;
        long start = System.currentTimeMillis();
        generate(rows, columns);
        long end = System.currentTimeMillis();
        return end - start;
    }
}
