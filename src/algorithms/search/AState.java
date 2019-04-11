package algorithms.search;
import java.util.Comparator;
import java.util.Objects;

/**
 * An abstract class representing a state in the algorithm
 * every state is unique , has a unique name.
 * every state has its cost
 * every state has its father - the earlier state that leaded to this one.
 */
public abstract class AState {
    private String stateName;
    private double cost;
    private AState cameFrom;

    public void setStateName(String stateName) {
        if( stateName != null) {
            this.stateName = stateName;
        }
    }

    public void setCost(double cost) {
        if( cost >=0)
        this.cost = cost;
    }

    public AState(){
        this.stateName = null;
        this.cameFrom = null;
        this.cost =0;
    }

    public String getStateName() {
        return stateName;
    }

    public double getCost() {
        return cost;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(AState cameFrom) {
        if( cameFrom != null)
        this.cameFrom = cameFrom;
    }

    /**
     * A comparator that compares states using their costs.
     * if the cost of one state bigger then the other than the first state is bigger.
     */
    Comparator<AState> compare = new Comparator<AState>() {
        @Override
        public int compare(AState o1, AState o2) {
            if(o1.getCost()> o2.getCost())
                return 1;
            if(o1.getCost() < o2.getCost())
                return -1;
            else return 0;
        }
    };

    @Override
    public String toString() {
        return this.getStateName();
    }
}
