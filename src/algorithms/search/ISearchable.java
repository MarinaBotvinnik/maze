package algorithms.search;

import java.util.ArrayList;
/**
 * Interface for a generic search problem.
 */
public interface ISearchable {
    AState getStartState();
    AState getGoalState();
    ArrayList<AState> getAllSuccessors(AState state);
}
