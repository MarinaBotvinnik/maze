package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * The class create a maze based on Prim's algorithm.
 */
public class MyMazeGenerator extends AMazeGenerator {
    /**
     * The function gets the number of rows and columns of the maze that need to be created. The function create the maze based on Prim's algorithm.
     * @param rows
     * @param columns
     * @return maze with start point ans exit point marked.
     */
    @Override
    public Maze generate(int rows, int columns) {
            Maze maze = new Maze(rows, columns);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++)
                    maze.setValueInMaze(i, j, 1);
            }
            String tmpkey = "";
            chooseStartPoint(maze, rows, columns);
            ArrayList<Position> walls = new ArrayList<>();
            HashMap<String, Position> visited = new HashMap<>();
            String key = "" + maze.getStartPosition().getRowIndex() + " " + maze.getStartPosition().getColumnIndex();
            visited.put(key, maze.getStartPosition());
            InsertWallsOfCell(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex(), walls, maze, visited);
            while (!walls.isEmpty()) {
                int choose = (int) (Math.random() * (walls.size()));
                Position tmp = walls.remove(choose);
                tmpkey = "" + tmp.getRowIndex() + " " + tmp.getColumnIndex();
                visited.put(tmpkey, tmp);
                paintWithZeroes(maze, tmp, visited);
                maze.setValueInMaze(tmp.getRowIndex(), tmp.getColumnIndex(), 0);
                InsertWallsOfCell(tmp.getRowIndex(), tmp.getColumnIndex(), walls, maze, visited);
            }
            chooseEndPoint(maze);

            return maze;
    }

    /**
     * the function receives the maze and choosing a random start point and marked it.
     * @param maze
     */

    private void chooseEndPoint(Maze maze) {
        if (maze != null) {
            boolean isChosen = false;
            int count = 0;
            while (isChosen == false && count != 1000) {
                int chooseSize = (int) (Math.random() * 4);
                count++;
                int i;
                switch (chooseSize) {
                    case 0:
                        for (i = 0; i < maze.getColumns(); i++) {
                            if (maze.getM_maze()[0][i] == 0 && (maze.getStartPosition().getRowIndex() != 0 || maze.getStartPosition().getColumnIndex() != i)) {
                                maze.setGoalPosition(0, i);
                                maze.setValueInMaze(0, i, 0);
                                isChosen = true;
                                break;
                            }
                        }
                        break;
                    case 1:
                        for (i = 0; i < maze.getRows(); i++) {
                            if (maze.getM_maze()[i][maze.getColumns() - 1] == 0 && (maze.getStartPosition().getRowIndex() != i || maze.getStartPosition().getColumnIndex() != maze.getColumns() - 1)) {
                                maze.setGoalPosition(i, maze.getColumns() - 1);
                                maze.setValueInMaze(i, maze.getColumns() - 1, 0);
                                isChosen = true;
                                break;
                            }
                        }
                        break;
                    case 2:
                        for (i = 0; i < maze.getColumns(); i++) {
                            if (maze.getM_maze()[maze.getRows() - 1][i] == 0 && (maze.getStartPosition().getRowIndex() != maze.getRows() - 1 || maze.getStartPosition().getColumnIndex() != i)) {
                                maze.setGoalPosition(maze.getRows() - 1, i);
                                maze.setValueInMaze(maze.getRows() - 1, i, 0);
                                isChosen = true;
                                break;
                            }
                        }
                        break;
                    case 3:
                        for (i = 0; i < maze.getRows(); i++) {
                            if (maze.getM_maze()[i][0] == 0 && (maze.getStartPosition().getRowIndex() != i || maze.getStartPosition().getColumnIndex() != 0)) {
                                maze.setGoalPosition(i, 0);
                                maze.setValueInMaze(i, 0, 0);
                                isChosen = true;
                                break;
                            }
                        }
                        break;
                }
            }
            if (isChosen)
                return;
            else {
                int i;
                for (i = 0; i < maze.getColumns(); i++) {
                    if (maze.getM_maze()[0][i] == 0 && (maze.getStartPosition().getRowIndex() != 0 || maze.getStartPosition().getColumnIndex() != i)) {
                        maze.setGoalPosition(0, i);
                        maze.setValueInMaze(0, i, 0);
                        return;
                    }
                }
                for (i = 0; i < maze.getRows(); i++) {
                    if (maze.getM_maze()[i][maze.getColumns() - 1] == 0 && (maze.getStartPosition().getRowIndex() != i || maze.getStartPosition().getColumnIndex() != maze.getColumns() - 1)) {
                        maze.setGoalPosition(i, maze.getColumns() - 1);
                        maze.setValueInMaze(i, maze.getColumns() - 1, 0);
                        return;
                    }
                }
                for (i = 0; i < maze.getColumns(); i++) {
                    if (maze.getM_maze()[maze.getRows() - 1][i] == 0 && (maze.getStartPosition().getRowIndex() != maze.getRows() - 1 || maze.getStartPosition().getColumnIndex() != i)) {
                        maze.setGoalPosition(maze.getRows() - 1, i);
                        maze.setValueInMaze(maze.getRows() - 1, i, 0);
                        return;
                    }
                }
                for (i = 0; i < maze.getRows(); i++) {
                    if (maze.getM_maze()[i][0] == 0 && (maze.getStartPosition().getRowIndex() != i || maze.getStartPosition().getColumnIndex() != 0)) {
                        maze.setGoalPosition(i, 0);
                        maze.setValueInMaze(i, 0, 0);
                        return;
                    }
                }
            }
        }
    }

    /**
     * the function return true if the position is a legal position in maze or false otherwise.
     * @param maze
     * @param pos
     * @return true or false
     */
    private boolean isPosInMaze(Maze maze, Position pos){
        if( maze!= null && pos.getRowIndex()>=0 && pos.getColumnIndex()>=0 && pos.getRowIndex()<maze.getRows() && pos.getColumnIndex()<maze.getColumns())
            return true;
        return false;
    }

    /**
     * The function will receive the maze, a specific position and all the position that already visited.
     * First the function will choose all the neighbors of the position that placed 2 cells from the position.
     * If one of the chosen neighbors is legal position than the function will paint with zero the cell that between the position and the chosen neighbor.
     * @param maze
     * @param position
     * @param visited
     */
    private void paintWithZeroes (Maze maze, Position position,HashMap<String,Position> visited){
        if(isPosInMaze(maze,position) && visited !=null ) {
            Position up = new Position(position.getRowIndex() - 2, position.getColumnIndex());
            Position down = new Position(position.getRowIndex() + 2, position.getColumnIndex());
            Position left = new Position(position.getRowIndex(), position.getColumnIndex() - 2);
            Position right = new Position(position.getRowIndex(), position.getColumnIndex() + 2);
            String upKey = "" + up.getRowIndex() + " " + up.getColumnIndex();
            String downKey = "" + down.getRowIndex() + " " + down.getColumnIndex();
            String leftKey = "" + left.getRowIndex() + " " + left.getColumnIndex();
            String rightKey = "" + right.getRowIndex() + " " + right.getColumnIndex();

            if (visited.containsKey(upKey) && isPosInMaze(maze, up)) {
                maze.setValueInMaze(position.getRowIndex() - 1, position.getColumnIndex(), 0);
                return;
            }
            if (visited.containsKey(downKey) && isPosInMaze(maze, down)) {
                maze.setValueInMaze(position.getRowIndex() + 1, position.getColumnIndex(), 0);
                return;
            }
            if (visited.containsKey(leftKey) && isPosInMaze(maze, left)) {
                maze.setValueInMaze(position.getRowIndex(), position.getColumnIndex() - 1, 0);
                return;
            }
            if (visited.containsKey(rightKey) && isPosInMaze(maze, right)) {
                maze.setValueInMaze(position.getRowIndex(), position.getColumnIndex() + 1, 0);
                return;
            }
        }
    }

    /**
     * The function choosing a start point for the maze.
     * @param maze
     * @param rows
     * @param columns
     */
    private void chooseStartPoint(Maze maze, int rows, int columns)
    {
        if (maze != null) {
            if (rows > 0 && columns > 0) {
                int x = (int) (Math.random() * rows);
                int y = (int) (Math.random() * columns);
                int chooseSize = (int) (Math.random() * 4);
                switch (chooseSize) {
                    case 0:
                        maze.setStartPosition(0, y);
                        break;
                    case 1:
                        maze.setStartPosition(x, columns - 1);
                        break;
                    case 2:
                        maze.setStartPosition(rows - 1, y);
                        break;
                    case 3:
                        maze.setStartPosition(x, 0);
                        break;
                }
            } else maze.setStartPosition(0, 0);
            maze.setValueInMaze(maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex(), 0);
        }
    }

    /**
     * the Function receive x- row index and y- column index and check if they are legal indexes in the maze.
     * If true the function will create a new position based on the indexes. The new position will be added to the walls list
     * only if it's not part of the visited positions already.
     * @param x
     * @param y
     * @param walls
     * @param maze
     * @param visited
     * @return
     */
    private boolean InsertWallPosition(int x, int y, ArrayList<Position> walls, Maze maze, HashMap<String,Position> visited){
        if(maze != null && walls != null && visited != null) {
            if (x >= 0 && x < maze.getRows() && y >= 0 && y < maze.getColumns()) {
                Position tmp = new Position(x, y);
                String tmpKey = "" + tmp.getRowIndex() + " " + tmp.getColumnIndex();
                if (!visited.containsKey(tmpKey)) {
                    walls.add(tmp);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The function using the InsertWallPosition function to insert the neighbors of a specific cell to the Walls list.
     * The specific cell defined by the x and y params.
     * @param x
     * @param y
     * @param walls
     * @param maze
     * @param visited
     */
    private void InsertWallsOfCell (int x,int y, ArrayList<Position> walls, Maze maze,HashMap<String,Position> visited){
        InsertWallPosition(x+2,y,walls,maze,visited);
        InsertWallPosition(x-2,y,walls,maze,visited);
        InsertWallPosition(x,y+2, walls,maze,visited);
        InsertWallPosition(x,y-2,walls,maze,visited);
    }
}
