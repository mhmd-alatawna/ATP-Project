package org.example.algorithms.search;

import org.example.algorithms.mazeGenerators.Maze;
import org.example.algorithms.mazeGenerators.Position;

import java.util.ArrayList;

/**
 * SearchableMaze implements the ISearchable interface for a maze problem.
 * finding all possible states that can be reached from a given state.
 */
public class SearchableMaze implements ISearchable {

    // Instance variable to store the maze
    Maze maze;

    // Constants for normal and diagonal step costs
    final int normalStepCost = 0;
    final int diagonalStepCost = 0;

    /**
     * Constructor to initialize the maze.
     * @param maze The maze to be used for the search problem.
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Returns the start state of the maze.
     * @return The start state represented as a MazeState.
     */
    @Override
    public AState getStartState() {
        Position s = maze.getStartPosition(); // Get the start position from the maze
        // Create and return a MazeState representing the start state
        return new MazeState(s.getRowIndex(), s.getColumnIndex(), assignCost(s.getRowIndex(), s.getColumnIndex(), 0), null);
    }

    /**
     * Returns the goal state of the maze.
     * @return The goal state represented as a MazeState.
     */
    @Override
    public AState getGoalState() {
        Position g = maze.getGoalPosition(); // Get the goal position from the maze
        // Create and return a MazeState representing the goal state
        return new MazeState(g.getRowIndex(), g.getColumnIndex(), 0, null);
    }

    /**
     * Returns a list of all possible neighbros of a given state in the maze.
     * @param state The current state from which neighbors are to be evaluated.
     * @return A list of all possible neighbors that can be reached from the current state.
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        if (state instanceof MazeState) {
            MazeState st = (MazeState) state;
            ArrayList<AState> arr = new ArrayList<>(); // List to store possible states
            boolean left = false, right = false, up = false, down = false;
            int r = st.getRow(), c = st.getColumn();
            int parentCost = st.getCost() - assignCost(st.getRow(), st.getColumn(), 0); // Calculate the parent's cost

            // Check possible movements (up, down, left, right) and add valid states to the list
            if (r + 1 < maze.getMatrix().length && maze.getMatrix()[r + 1][c] == 0) {
                arr.add(new MazeState(r + 1, c, parentCost + assignCost(r + 1, c, normalStepCost), st));
                down = true;
            }
            if (c + 1 < maze.getMatrix()[0].length && maze.getMatrix()[r][c + 1] == 0) {
                arr.add(new MazeState(r, c + 1, parentCost + assignCost(r, c + 1, normalStepCost), st));
                right = true;
            }
            if (c - 1 >= 0 && maze.getMatrix()[r][c - 1] == 0) {
                arr.add(new MazeState(r, c - 1, parentCost + assignCost(r, c - 1, normalStepCost), st));
                left = true;
            }
            if (r - 1 >= 0 && maze.getMatrix()[r - 1][c] == 0) {
                arr.add(new MazeState(r - 1, c, parentCost + assignCost(r - 1, c, normalStepCost), st));
                up = true;
            }
            handleDiagonals(left, right, up, down, arr, st); // Handle diagonal movements (if applicable)
            return arr; // Return the list of possible states
        }
        return null; // Return null if the state is not an instance of MazeState
    }

    /**
     * Handles diagonal movements also
     * @param left left movement is possible.
     * @param right right movement is possible.
     * @param up up movement is possible.
     * @param down down movement is possible.
     * @param arr The collection where valid diagonal states will be stored.
     * @param st The MazeState from which diagonal movement options are evaluated.
     */
    private void handleDiagonals(boolean left, boolean right, boolean up, boolean down, ArrayList<AState> arr, MazeState st) {
        int r = st.getRow(), c = st.getColumn();
        int parentCost = st.getCost() - assignCost(st.getRow(), st.getColumn(), 0); // Calculate the parent's cost

        // Check and add valid diagonal states (up-left, up-right, down-left, down-right)
        if (left || up) {
            if (c - 1 >= 0 && r - 1 >= 0 && maze.getMatrix()[r - 1][c - 1] == 0)
                arr.add(new MazeState(r - 1, c - 1, parentCost + assignCost(r - 1, c - 1, diagonalStepCost), st));
        }
        if (right || up) {
            if (c + 1 < maze.getMatrix()[0].length && r - 1 >= 0 && maze.getMatrix()[r - 1][c + 1] == 0)
                arr.add(new MazeState(r - 1, c + 1, parentCost + assignCost(r - 1, c + 1, diagonalStepCost), st));
        }
        if (right || down) {
            if (c + 1 < maze.getMatrix()[0].length && r + 1 < maze.getMatrix().length && maze.getMatrix()[r + 1][c + 1] == 0)
                arr.add(new MazeState(r + 1, c + 1, parentCost + assignCost(r + 1, c + 1, diagonalStepCost), st));
        }
        if (left || down) {
            if (r + 1 < maze.getMatrix().length && c - 1 >= 0 && maze.getMatrix()[r + 1][c - 1] == 0)
                arr.add(new MazeState(r + 1, c - 1, parentCost + assignCost(r + 1, c - 1, diagonalStepCost), st));
        }
    }

    /**
     * Calculates the cost for a given state
     * @param r The row of the state.
     * @param c The column of the state.
     * @param estimatedFirstCost The estimated cost for the first step.
     * @return The total cost calculated as the sum of the estimated cost and the Manhattan distance to the goal.
     */
    private int assignCost(int r, int c, int estimatedFirstCost) {
        MazeState goal = (MazeState) getGoalState(); // Get the goal state
        // Calculate the Manhattan distance between the current state and the goal
        int distance = Math.abs(r - goal.row) + Math.abs(c - goal.column);
        return estimatedFirstCost + distance; // Return the total cost
    }

}
