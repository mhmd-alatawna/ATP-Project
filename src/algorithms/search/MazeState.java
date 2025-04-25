package algorithms.search;

import algorithms.mazeGenerators.Maze;

/**
 * MazeState class extends AState and represents a state in the maze problem.
 */
public class MazeState extends AState {

    // Row and column coordinates of the current state in the maze
    int row;
    int column;

    /**
     * Constructor to initialize the MazeState
     * @param row The row coordinatess of the state.
     * @param column The column coordinate of the state.
     * @param cost The cost to reach to this state.
     * @param cameFrom The previous state from which this state was reached.
     */
    public MazeState(int row, int column, int cost, MazeState cameFrom) {
        super(cost, cameFrom); // Call the parent constructor with cost and cameFrom
        this.row = row; // Set the row coordinate
        this.column = column; // Set the column coordinate
    }

    /**
     * toString method
     * @return A string representation of the MazeState
     */
    @Override
    public String toString() {
        return "{" + row + "," + column + "}"; // Return the state as a string in the format {row,column}
    }

    /**
     * method to compare two MazeState objects based on their coordinates
     * @param other The object to compare with.
     * @return true if the row and column are the same for both states; false if not.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof MazeState) {
            return ((MazeState) other).row == row && ((MazeState) other).column == column; // Compare row and column
        }
        return false; // Return false if the other object is not an instance of MazeState
    }

    /**
     * Getter method for the row coordinate.
     * @return The row coordinate.
     */
    public int getRow() {
        return row;
    }

    /**
     * Setter method for the row coordinate.
     * @param row The row coordinate to set.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Getter method for the column coordinate.
     * @return The column coordinate.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Setter method for the column coordinate.
     * @param column The column coordinate to set.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * hashCode method to generate a unique hash code for the MazeState
     * @return A hash code
     */
    @Override
    public int hashCode() {
        return row * 1000 + column; // Generate a hash code using the row and column values
    }
}
