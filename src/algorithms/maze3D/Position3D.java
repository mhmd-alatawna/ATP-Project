package algorithms.maze3D;

public class Position3D {
    int rowIndex;
    int columnIndex;
    int depthIndex;

    public Position3D(int depthIndex , int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.depthIndex = depthIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getDepthIndex() {
        return depthIndex;
    }

    public void setDepthIndex(int depthIndex) {
        this.depthIndex = depthIndex;
    }

    public boolean isEquals(int depth , int row , int column){
        return rowIndex == row && columnIndex == column && depthIndex == depth;
    }

    public String toString(){
        return "{" + depthIndex + "," + rowIndex + "," + columnIndex + "}" ;
    }

}
