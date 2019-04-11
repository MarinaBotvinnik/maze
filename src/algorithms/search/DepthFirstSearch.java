package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * A class that represents the DFS searching algorithm.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {
    private Stack<AState> states;
    HashMap<String, Boolean> visited;

    /**
     * A function that solves a given Searchable problem using the DFS algorithm.
     * @param s - Every problem is can be searchable.
     * @return an object of Type Solution that contains the solution for the problem.
     */
    @Override
    public Solution solve(ISearchable s) {
        if(s==null){
            return  null;
        }
        Solution sol = new Solution();
        states = new Stack<>();
        visited = new HashMap<>();
        visited.put(s.getStartState().getStateName(), true);
        states.push(s.getStartState());
        boolean exitFound = false;
        while (states.size() != 0 && exitFound == false) {
            AState state = states.pop();
            if (!visited.containsKey(state.getStateName())) {
                visited.put(state.getStateName(), true);
                increaseEvaluated();
            }
            ArrayList<AState> successors = s.getAllSuccessors(state);
            for (AState st : successors) {
                if (st.getStateName().equals(s.getGoalState().getStateName())) {
                    st.setCameFrom(state);
                    ArrayList<AState> solutionPath = new ArrayList<>();
                    while(st.getCameFrom()!=null){
                        solutionPath.add(0,st);
                        st = st.getCameFrom();
                    }
                    solutionPath.add(0,st);
                    sol.setSolutionPath(solutionPath);
                    exitFound = true;
                    break;
                }
                if (!visited.containsKey(st.getStateName())) {
                    states.push(st);
                }
                if (st.getCameFrom() == null)
                    st.setCameFrom(state);
            }
        }
        return sol;
    }

    @Override
    public String getName() {
        return "DepthFirstSearch";
    }
}
