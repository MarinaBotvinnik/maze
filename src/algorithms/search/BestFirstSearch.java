package algorithms.search;

/**
 * Class represents Best First Search algorithm.
 * The class extends the BFS class because the algorithms is basicaly the same.
 * the only change is in Best First search we do use the cost of the states.
 */
public class BestFirstSearch extends BreadthFirstSearch {
    /**
     * Override the function from BFS, so now its not changing the priority to zero
     * So the priority queue will work properly.
     * @param state
     */
    @Override
    protected void changePrio(AState state) {
        return;
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }
}
