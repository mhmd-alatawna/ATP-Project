package algorithms.search;

import algorithms.mazeGenerators.Maze;

public class MazeState extends AState {
    int row ;
    int column ;

    public MazeState(int row, int column, int cost , MazeState cameFrom) {
        super(cost , cameFrom);
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "{" + row + "," + column + "}";
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof MazeState){
            return ((MazeState) other).row == row && ((MazeState) other).column == column;
        }
        return false ;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public int hashCode() {
        return row * 1000 + column;
    }
}
