package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;

/**
 * Represents a searchable 3D maze
 */
public class SearchableMaze3D implements ISearchable {
    Maze3D maze;  // The 3D maze object
    final int normalStepCost = 0;  // Default cost for a step

    /**
     * Constructs a searchable 3D
     *
     * @param maze the 3D maze to be used for searching
     */
    public SearchableMaze3D(Maze3D maze) {
        this.maze = maze;
    }

    /**
     * Returns the starting state of the maze.
     *
     * @return the AState representing the start position in the maze
     */
    @Override
    public AState getStartState() {
        Position3D s = maze.getStartPosition();
        // Creates and returns a new state representing the start position
        return new Maze3DState(s.getDepthIndex() , s.getRowIndex(), s.getColumnIndex(), assignCost(s.getDepthIndex() , s.getRowIndex() , s.getColumnIndex() , 0), null);
    }

    /**
     * Returns the goal state of the maze.
     *
     * @return the AState representing the goal position in the maze
     */
    @Override
    public AState getGoalState() {
        Position3D g = maze.getGoalPosition();
        // Creates and returns a new state representing the goal position
        return new Maze3DState(g.getDepthIndex() , g.getRowIndex(), g.getColumnIndex(), 0, null);
    }

    /**
     * Returns all possible states that can be reached from the given state.
     *
     * @param state the current state
     * @return a list of all reachable states from the current state
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        if (state instanceof Maze3DState) {
            Maze3DState st = (Maze3DState) state;
            ArrayList<AState> arr = new ArrayList<>();
            int d = st.getDepth(), r = st.getRow(), c = st.getColumn();
            int parentCost = st.getCost() - assignCost(st.getDepth() , st.getRow() , st.getColumn() , 0);

            // Checking possible moves in each direction (up, down, left, right, depth up, depth down)
            if (r + 1 < maze.getMatrix()[0].length && maze.getMatrix()[d][r + 1][c] == 0)
                arr.add(new Maze3DState(d , r + 1, c, parentCost + assignCost(d , r + 1 , c , normalStepCost), st));

            if (c + 1 < maze.getMatrix()[0][0].length && maze.getMatrix()[d][r][c + 1] == 0)
                arr.add(new Maze3DState(d , r, c + 1, parentCost + assignCost(d , r , c + 1 , normalStepCost), st));

            if (c - 1 >= 0 && maze.getMatrix()[d][r][c - 1] == 0)
                arr.add(new Maze3DState(d ,r, c - 1, parentCost + assignCost(d , r , c - 1 , normalStepCost), st));

            if (r - 1 >= 0 && maze.getMatrix()[d][r - 1][c] == 0)
                arr.add(new Maze3DState(d , r - 1, c, parentCost + assignCost(d, r - 1 , c , normalStepCost), st));

            if (d - 1 >= 0 && maze.getMatrix()[d - 1][r][c] == 0)
                arr.add(new Maze3DState(d - 1,r, c, parentCost + assignCost(d - 1 , r , c, normalStepCost), st));

            if (d + 1 < maze.getMatrix().length && maze.getMatrix()[d + 1][r][c] == 0)
                arr.add(new Maze3DState(d + 1, r, c, parentCost + assignCost(d + 1 , r , c , normalStepCost), st));
            return arr;
        }
        return null;  // Return null if the state is not of type Maze3DState
    }

    /**
     * Assigns a cost based on the distance from the given position to the goal.
     *
     * @param d the depth coordinate
     * @param r the row coordinate
     * @param c the column coordinate
     * @param estimatedFirstCost the initial step cost to be added
     * @return the calculatedcost
     */
    private int assignCost(int d , int r , int c , int estimatedFirstCost){
        Maze3DState goal = (Maze3DState) getGoalState();
        int distance = Math.abs(d - goal.getDepth()) + Math.abs(r - goal.row) + Math.abs(c - goal.column);
        return estimatedFirstCost + distance;  // Return total cost (estimated cost + distance)
    }
}
