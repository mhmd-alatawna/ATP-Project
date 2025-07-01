package org.example.algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Represents a position in the maze with row and column indices.
 */
public class Position implements Serializable {
    int rowIndex, columnIndex;

    /**
     * Constructor to initialize position
     *
     * @param row the row index of the position
     * @param col the column index of the position
     */
    public Position(int row, int col) {
        this.rowIndex = row;
        this.columnIndex = col;
    }

    /**
     * Getter for the row index.
     *
     * @return the row index of the position
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Setter for the row index.
     *
     * @param rowIndex the new row index to set
     */
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * Getter for the column index.
     *
     * @return the column index of the position
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * Setter for the column index.
     *
     * @param columnIndex the new column index to set
     */
    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    /**
     * Method to check if the current position is equal to a given row and column.
     *
     * @param row the row index to compare with
     * @param col the column index to compare with
     * @return true if the position matches the given row and column, false if not
     */
    public boolean isEquals(int row, int col) {
        return rowIndex == row && columnIndex == col;
    }

    /**
     * Override of the equals method to compare two Position objects.
     *
     * @param other the object to compare with
     * @return true if the current position is equal to the given position, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof Position) {
            return rowIndex == ((Position)other).rowIndex && columnIndex == ((Position)other).columnIndex;
        }
        return false;  // Return false if the object is not an instance of Position
    }

    /**
     * Override of the toString method to represent the Position as a string.
     *
     * @return a string representation of the position in the form "{row,column}"
     */
    @Override
    public String toString(){
        return "{" + rowIndex + "," + columnIndex + "}";
    }
}
