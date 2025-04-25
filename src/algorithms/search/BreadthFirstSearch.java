package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * BreadthFirstSearch class implements the Breadth-First Search algorithm.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {
    // Queue for storing the states to be evaluated in FIFO order
    LinkedList<AState> queue;

    // HashMaps for tracking closed and open lists of states
    HashMap<AState, AState> closedList;
    HashMap<AState, AState> openList;

    // Counter to keep track of the number of nodes evaluated
    int numberOfNodesEvaluated;

    /**
     * Constructor to initialize the algo
     * Initializes the queue, closed list, open list, and number of nodes evaluated.
     */
    public BreadthFirstSearch() {
        queue = new LinkedList<>();
        closedList = new HashMap<>();
        openList = new HashMap<>();
        numberOfNodesEvaluated = 0;
    }

    /**
     * method to solve the search problem for the given searchable object.
     * @param searchable The search able object containing the start and goal.
     * @return The solution representing the shortest path from start to goal, or null
     */
    @Override
    public Solution solve(ISearchable searchable) {
        AState goal = null;

        // Get the start and goal states from the searchable object
        AState startState = searchable.getStartState();
        AState goalState = searchable.getGoalState();

        // If the start state is the same as the goal state, return the solution immediately
        if (goalState.equals(startState)) {
            return goalState.backTrack();
        }

        // Add the start state to the open list and queue
        addState(startState);
        openList.put(startState, startState);

        // Perform the BFS algorithm to find the goal state
        while (!isEmpty() && goal == null) {
            AState state = popState(); // Pop the first state from the queue
            openList.remove(state); // Remove it from the open list
            numberOfNodesEvaluated++; // Increment the number of nodes evaluated

            // Get all possible neighboring states of the current state
            ArrayList<AState> arr = searchable.getAllPossibleStates(state);
            for (AState s : arr) {
                // If the neighbor is not in the closed list, process it
                AState closedListResult = closedList.get(s);
                if (closedListResult == null) {
                    // If this state is the goal state, mark it and break the loop
                    if (s.equals(goalState)) {
                        goal = s;
                        break;
                    }
                    // Otherwise, handle the neighbor state
                    handleNeighbour(s);
                }
            }
            // Mark the current state as evaluated by adding it to the closed list
            closedList.put(state, state);
        }

        // If the goal state is found, return the solution by backtracking
        if (goal == null)
            return null;
        return goal.backTrack();
    }

    /**
     * method to return the name of the algorithm.
     * @return The name of the algorithm, which is "BreadthFirstSearch".
     */
    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

    /**
     * method to return the number of nodes evaluated during the search process.
     * @return The number of nodes evaluated.
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated;
    }

    /**
     * Method to handle a neighboring state by either adding it to the open list or updating its cost.
     * @param s The neighboring state to handle.
     */
    protected void handleNeighbour(AState s) {
        AState openListResult = openList.get(s);
        if (openListResult == null) {
            // If the state is not in the open list, add it
            addState(s);
            openList.put(s, s);
        } else {
            // If the state is already in the open list, check if the cost needs updating
            if (openListResult.getCost() > s.getCost())
                openListResult.setCost(s.getCost());
        }
    }

    /**
     * Method to add a state to the front of the queue.
     * @param state The state to add to the queue.
     */
    protected void addState(AState state) {
        queue.addFirst(state);
    }

    /**
     * Method to pop the last state from the queue.
     * @return The last state in the queue.
     */
    protected AState popState() {
        return queue.removeLast();
    }

    /**
     * Method to check if the queue is empty.
     * @return True if the queue is empty, false otherwise.
     */
    protected boolean isEmpty() {
        return queue.isEmpty();
    }
}
