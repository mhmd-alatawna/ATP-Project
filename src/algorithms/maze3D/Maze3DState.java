package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.MazeState;

public class Maze3DState extends AState {
    int row ;
    int column ;
    int depth ;

    public Maze3DState(int depth , int row, int column, int cost , Maze3DState cameFrom) {
        super(cost , cameFrom);
        this.row = row;
        this.column = column;
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "{" + depth + "," + row + "," + column + "}";
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Maze3DState){
            return ((Maze3DState) other).depth == depth && ((Maze3DState) other).row == row && ((Maze3DState) other).column == column;
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

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public int hashCode() {
        return depth * 1000 + row * 100 + column;
    }
}
