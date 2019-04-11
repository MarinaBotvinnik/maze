package algorithms.search;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * A class that represents the BFS searching algorithm.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {
    private PriorityQueue<AState> queue;
    HashMap<String, Boolean> visited;

    /**
     * A function that solves a given Searchable problem using the BFS algorithm.
     * @param s - Every problem is can be searchable.
     * @return an object of Type Solution that contains the solution for the problem.
     */
    @Override
    public Solution solve(ISearchable s) {

            Solution sol = new Solution();
            queue = new PriorityQueue<>(s.getStartState().compare);
            visited = new HashMap<>();
            visited.put(s.getStartState().getStateName(), true);
            changePrio(s.getStartState());
            queue.add(s.getStartState());
            boolean exitFound = false;
            while (queue.size() != 0 && exitFound == false) {
                AState state = queue.poll();
                if (!visited.containsKey(state.getStateName())) {
                    visited.put(state.getStateName(), true);
                    increaseEvaluated();
                }
                ArrayList<AState> successors = s.getAllSuccessors(state);
                for (AState st : successors) {
                    if (st.getStateName().equals(s.getGoalState().getStateName())) {
                        st.setCameFrom(state);
                        ArrayList<AState> solutionPath = new ArrayList<>();
                        while (st.getCameFrom() != null) {
                            solutionPath.add(0, st);
                            st = st.getCameFrom();
                        }
                        solutionPath.add(0, st);
                        sol.setSolutionPath(solutionPath);
                        exitFound = true;
                        break;
                    }
                    if (!visited.containsKey(st.getStateName())) {
                        changePrio(st);
                        queue.add(st);
                    }
                    if (st.getCameFrom() == null)
                        st.setCameFrom(state);
                }
            }

        return sol;
    }

    /**
     * a function that changing all the costs of the states to zero
     * so they will have the same priority
     * @param state
     */
    protected void changePrio (AState state){
        if(state!=null)
        state.setCost(0);
    }
    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }
}
