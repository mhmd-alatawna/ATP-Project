package org.example.algorithms.search;

import java.util.HashMap;
import java.util.Stack;

/**
 * DepthFirstSearch class performs the Depth-First Search algorithm.
 */
public class DepthFirstSearch implements ISearchingAlgorithm {
    // Stack to manage the states to be explored in DFS
    Stack<AState> stack;

    // HashMap to track which states have been evaluated (marked as visited)
    HashMap<AState, Boolean> map;

    // Counter to keep track of the number of nodes evaluated
    int numberOfNodesEvaluated;

    /**
     * Constructor to initialize the dfs algo
     * Initializes the stack,map and the number of nodes
     */
    public DepthFirstSearch() {
        stack = new Stack<>();
        map = new HashMap<>();
        numberOfNodesEvaluated = 0;
    }

    /**
     * method to solve the search problem for the given searchable object.
     * @param searchable The searchable object containing the start and goal states.
     * @return The solution representing the path from start to goal, or null
     */
    @Override
    public Solution solve(ISearchable searchable) {
        AState goal = null;

        // If the goal state is the same as the start state, return the solution immediately
        if (searchable.getGoalState().equals(searchable.getStartState()))
            return searchable.getGoalState().backTrack();

        // Push the start state onto the stack
        stack.add(searchable.getStartState());

        // Perform the DFS algorithm to find the goal state
        while (!stack.isEmpty()) {
            AState state = stack.pop(); // Pop the top state from the stack
            map.put(state, Boolean.TRUE); // Mark the state as visited (evaluated)
            numberOfNodesEvaluated++; // Increment the number of nodes evaluated

            // Explore all possible neighboring states of the current state
            for (AState s : searchable.getAllPossibleStates(state)) {
                // If the state has not been visited before, process it
                if (map.get(s) == null) {
                    // If this state is the goal state, mark it and break the loop
                    if (s.equals(searchable.getGoalState())) {
                        goal = s;
                        break;
                    }
                    // Otherwise, push the state onto the stack for further exploration
                    stack.push(s);
                }
            }
        }

        // If the goal state is found, return the solution by backtracking
        if (goal == null)
            return null;
        return goal.backTrack();
    }

    /**
     * method to return the name of the algorithm.
     * @return The name of the algorithm
     */
    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

    /**
     * method to return the number of nodes evaluated during the search process.
     * @return The number of nodes evaluated.
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated;
    }
}
