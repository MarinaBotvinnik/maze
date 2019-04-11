package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    /**
     * Abstract class for searching algorithms
     * contains number of nodes the algorithm will be visiting until it found an end point
     */
    private int NumberOfNodesEvaluated;

    @Override
    public abstract Solution solve(ISearchable s);

    public void increaseEvaluated(){
        NumberOfNodesEvaluated++;
    }
    public int getNumberOfNodesEvaluated(){
        return NumberOfNodesEvaluated;
    }
    public abstract String getName();
}
