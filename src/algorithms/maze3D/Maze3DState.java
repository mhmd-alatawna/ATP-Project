package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.MazeState;

/**
 * Represents a specific state
 * Each state holds its position, path cost, and a reference to the previous state.
 */
public class Maze3DState extends AState {
    int row;
    int column;
    int depth;

    /**
     * Constructs a new Maze3DState
     *
     * @param depth    the depth of the current position
     * @param row     the row index
     * @param column  the column index
     * @param cost   the cost to reach this state
     * @param cameFrom  the previous state
     */
    public Maze3DState(int depth, int row, int column, int cost, Maze3DState cameFrom) {
        super(cost, cameFrom);
        this.row = row;
        this.column = column;
        this.depth = depth;
    }

    /**
     * Returns a string representation of this Maze3DState
     *
     * @return a string representing the position of this state
     */
    @Override
    public String toString() {
        return "{" + depth + "," + row + "," + column + "}";
    }

    /**
     * Checks if this maze state is equal to another object.
     *
     * @param other the object to compare with
     * @return true if the other object is a Maze3DState with the same position, false if not
     */
    @Override
    public boolean equals(Object other){
        if(other instanceof Maze3DState){
            return ((Maze3DState) other).depth == depth && ((Maze3DState) other).row == row && ((Maze3DState) other).column == column;
        }
        return false;
    }

    /**
     * Returns the row index of this state.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row index of thisstate.
     *
     * @param row the new row index
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the column index of this state.
     *
     * @return the column indexx
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets the columnn index of this state.
     *
     * @param column the new column index
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Returns the depth of this state.
     *
     * @return the depth index
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Sets the depth of this state.
     *
     * @param depth the new depth index
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Generates a hash code for this state based on its position.
     *
     * @return a  hash code
     */
    @Override
    public int hashCode() {
        return depth * 1000 + row * 100 + column;
    }
}
