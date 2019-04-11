package algorithms.search;

import java.util.ArrayList;

/**
 * The class defines the solution of a searching problem.
 */
public class Solution {
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
