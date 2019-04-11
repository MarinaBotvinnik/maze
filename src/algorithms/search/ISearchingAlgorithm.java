package algorithms.search;
/**
 * Interface for all the searching algorithms.
 */
public interface ISearchingAlgorithm {

    Solution solve(ISearchable s);
    int getNumberOfNodesEvaluated();
    String getName();
}
