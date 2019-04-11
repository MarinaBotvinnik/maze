package algorithms.mazeGenerators;

/**
 * A class represents a simple maze a.k.a a maze that its only demand is to contain a path between
 * the start position and the end position
 * The class extends the AMazeGenerator abstract class.
 */
public class SimpleMazeGenerator extends AMazeGenerator {
    /**
     * This function creates a maze with the given size not using any special algorithm.
     * @param rows (num of rows)
     * @param columns (num of columns)
     * @return A maze that must contain a path between the start point and the end point
     */
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows,columns);
        int start = (int)(Math.random()*rows);
        maze.setStartPosition(start,0);
        int end = (int)(Math.random()*rows);
        maze.setGoalPosition(end,columns-1);
        for(int i=0; i<rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i != start) {
                    int value = (int) (Math.random() * 2);
                    maze.setValueInMaze(i, j, value);
                }
            }
        }
        if(end - start >0){
            for(int i= start; i<=end; i++)
                maze.setValueInMaze(i,columns-1,0);
        }
         else{
            for(int i=end; i<=start; i++)
                maze.setValueInMaze(i,columns-1,0);
        }
         return maze;
    }
}
