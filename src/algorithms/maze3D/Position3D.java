package algorithms.maze3D;

/**
 * Represents a position in a 3D maze
 */
public class Position3D {
    int rowIndex;        // Row coordinate
    int columnIndex;     // Column coordinate
    int depthIndex;      // Depth (layer) coordinate

    /**
     * Constructs a Position3D object
     *
     * @param depthIndex the depth (layer) index
     * @param rowIndex the row index
     * @param columnIndex the column index
     */
    public Position3D(int depthIndex , int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.depthIndex = depthIndex;
    }

    /**
     * Gets the row index of this position.
     *
     * @return the row index
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Sets the row index of this position.
     *
     * @param rowIndex the new row index
     */
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * Gets the column index of this position.
     *
     * @return the column index
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * Sets the column index of this position.
     *
     * @param columnIndex the new column index
     */
    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    /**
     * Gets the depth (layer) index of this position.
     *
     * @return the depth index
     */
    public int getDepthIndex() {
        return depthIndex;
    }

    /**
     * Sets the depth (layer) index of this position.
     *
     * @param depthIndex the new depth index
     */
    public void setDepthIndex(int depthIndex) {
        this.depthIndex = depthIndex;
    }

    /**
     * Checks if the given coordinates match this position.
     *
     * @param depth the depth to compare
     * @param row the row to compare
     * @param column the column to compare
     * @return true if the given coordinates are equal to this position, false if not
     */
    public boolean isEquals(int depth , int row , int column){
        return rowIndex == row && columnIndex == column && depthIndex == depth;
    }

    /**
     * Returns a string representation of the position
     *
     * @return a string representation of this position
     */
    public String toString(){
        return "{" + depthIndex + "," + rowIndex + "," + columnIndex + "}" ;
    }

}
