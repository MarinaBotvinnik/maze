package algorithms.search;

import java.util.ArrayList;
import algorithms.mazeGenerators.Maze;

/**
 * A class that turns a maze to a searchable one.
 * A searchable maze can apply any searching algorithm on it to get a solution to the maze.
 * The class implements ISearchable interface so it has to implement the three main functions:
 * 1. get the start state - the start position of the maze.
 * 2. get the goal state - the goal position of the maze.
 * 3. get all successors of a state - all the positions in the maze that u can move to from a given position.
 */
public class SearchableMaze implements ISearchable {
    private Maze maze;

    public SearchableMaze(Maze maze) {
        if(maze!=null)
        this.maze = maze;
    }

    /**
     * get the start state - the start position of the maze.
     * @return A start state
     */
    @Override
    public AState getStartState() {
        MazeState state = new MazeState(maze.getStartPosition().getRowIndex(),maze.getStartPosition().getColumnIndex(),0);
        return state;
    }

    /**
     * get the goal state - the goal position of the maze.
     * @return A goal state
     */
    @Override
    public AState getGoalState() {
        MazeState state = new MazeState(maze.getGoalPosition().getRowIndex(),maze.getGoalPosition().getColumnIndex(),0);
        return state;
    }

    /**
     * get all successors of a state - all the positions in the maze that touching in any way the given state
     * 1    2   3
     * 4 state  5
     * 6    7   8
     * there are 8 possible successors at top to any state in the maze
     * a cell will consider a successor if its value is 0 and its touching the given state with a wall(if its 2,4,5,7 locations)
     * or if the cell touching the given state with a point and there is a zero way with one step to get there (1,3,6,8 locations)
     * @param state the given state.
     * @return An array list of states that are successors to the given state.
     */
    @Override
    public ArrayList<AState> getAllSuccessors(AState state) {
        if(state==null)
            return  null;
        ArrayList<AState> tmp = new ArrayList<>();
        String[] pos = state.getStateName().split(" ");
        int x = Integer.parseInt(pos[0]);
        int y = Integer.parseInt(pos[1]);
        AState curr;
        boolean up =false;
        boolean down = false;
        boolean left = false;
        boolean right = false;
        if(x-1>=0 && maze.getM_maze()[x-1][y] == 0) {
            curr = new MazeState(x - 1, y, 1);
            tmp.add(curr);
            up=true;
        }
        if(x+1<maze.getRows() && maze.getM_maze()[x+1][y] == 0){
            curr = new MazeState(x+1,y,1);
            tmp.add(curr);
            down = true;
        }
        if(y-1>=0 && maze.getM_maze()[x][y-1] == 0){
            curr = new MazeState(x,y-1,1);
            tmp.add(curr);
            left = true;
        }
        if(y+1<maze.getColumns() && maze.getM_maze()[x][y+1] == 0){
            curr = new MazeState(x,y+1,1);
            tmp.add(curr);
            right = true;
        }
        if(y-1>=0 && x-1>=0 && maze.getM_maze()[x-1][y-1] == 0 && (up || left)){
            curr = new MazeState(x-1,y-1,0);
            tmp.add(curr);
        }
        if(y+1<maze.getColumns() && x-1>=0 && maze.getM_maze()[x-1][y+1] == 0 && (up || right)){
            curr = new MazeState(x-1,y+1,0);
            tmp.add(curr);
        }
        if(y-1>=0 && x+1<maze.getRows() && maze.getM_maze()[x+1][y-1] == 0 && (down || left)){
            curr = new MazeState(x+1,y-1,0);
            tmp.add(curr);
        }
        if(y+1<maze.getColumns() && x+1<maze.getRows() && maze.getM_maze()[x+1][y+1] == 0 && (down || right)){
            curr = new MazeState(x+1,y+1,0);
            tmp.add(curr);
        }
        return tmp;
    }
}
