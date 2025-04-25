package algorithms.search;

import java.util.ArrayList;

/**
 * The Solution class represents the solution to a search problem.
 * It stores the sequence of states that form the solution path.
 */
public class Solution {
    // List to store the solution path (sequence of states)
    ArrayList<AState> solutionPath;

    /**
     * Constructor that initializes the solution path.
     */
    public Solution() {
        solutionPath = new ArrayList<>(); // Initialize the list as an empty path
    }

    /**
     * Returns the solution path.
     * @return The list of states representing the solution path.
     */
    public ArrayList<AState> getSolutionPath() {
        return solutionPath; // Return the list of states representing the solution path
    }

    /**
     * Adds a state to the start of the solution path.
     * @param state The state to add at the beginning of the solution path.
     */
    public void addToStartOfSolutionPath(AState state) {
        solutionPath.add(0, state); // Adds the state to the beginning of the path
    }

    /**
     * Adds a state to the end of the solution path.
     *
     * @param state The state to add to the end of the solution path.
     */
    public void addToEndOfSolutionPath(AState state) {
        solutionPath.add(state); // Adds the state to the end of the path
    }

    /**
     * Returns a string representation of the solution path.
     *
     * @return A string representing the solution path.
     */
    @Override
    public String toString() {
        return solutionPath.toString(); // Return the string representation of the solution path
    }
}
