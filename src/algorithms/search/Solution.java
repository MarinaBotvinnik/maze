package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The class defines the solution of a searching problem.
 */
public class Solution implements Serializable {
    private ArrayList<AState> solutionPath;

    public Solution() {
        this.solutionPath = null;
    }

    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }

    public void setSolutionPath(ArrayList<AState> solutionPath) {
        if(solutionPath!=null)
        this.solutionPath = solutionPath;
    }
}
