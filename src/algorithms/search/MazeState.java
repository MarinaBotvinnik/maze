package algorithms.search;
/**
 * The class define a state in the maze problem.
 * The params x,y are the indexes of the state in the maze.
 */
public class MazeState extends AState {
    private int x;
    private int y;

    public MazeState(int x, int y, double cost) {
        super();
        this.setStateName(""+x+" "+y);
        if(cost>=0) {
            this.setCost(cost);
        }
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
