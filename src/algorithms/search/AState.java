package algorithms.search;

/**
 *  class representing a state in a search algorithm.
 */
public abstract class AState {
    // Cost to reach this state
    int cost;

    // The previous state from which this state was reached
    AState cameFrom;

    /**
     * Constructor to initialize the state with a cost and a reference to the previous state.
     * @param cost The cost to reach this state.
     * @param cameFrom The previous state that led to this state.
     */
    public AState(int cost, AState cameFrom) {
        this.cost = cost;
        this.cameFrom = cameFrom;
    }

    /**
     * Backtrack and construct the solution path from state.
     * @return A Solution object containing the path for starting state to cuurent state
     */
    public Solution backTrack() {
        // Create a new Solution object to hold the path
        Solution solution = new Solution();

        // Current state starts as 'this' state
        AState curr = this;

        // Traverse back through the states (using the 'cameFrom' reference)
        while(curr != null) {
            // Add the current state to the start of the solution path
            solution.addToStartOfSolutionPath(curr);
            // Move to the previous state
            curr = curr.cameFrom;
        }
        // Return the complete solution path
        return solution;
    }

    /**
     * Getter for the cost of the state.
     * @return The cost to reach this state.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Setter for the cost of the state.
     * @param cost The cost to set for this state.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Getter for the previous state that led to this state.
     * @return The previous state from which this state was reached.
     */
    public AState getCameFrom() {
        return cameFrom;
    }

    /**
     * Setter for the previous state that led to this state.
     * @param cameFrom The previous state to set for this state.
     */
    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

}
