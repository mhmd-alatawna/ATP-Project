package algorithms.mazeGenerators;

public class Position {
    int rowIndex, columnIndex;

    public Position(int row, int col) {
        this.rowIndex = row;
        this.columnIndex = col;
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

    public boolean isEquals(int row, int col) {
        return rowIndex == row && columnIndex == col;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Position) {
            return rowIndex == ((Position)other).rowIndex && columnIndex == ((Position)other).columnIndex;
        }
        return false ;
    }
    @Override
    public String toString(){
        return "{" + rowIndex + "," + columnIndex + "}" ;
    }
}
