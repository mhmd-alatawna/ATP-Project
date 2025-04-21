package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    Maze maze;
    final int normalStepCost = 0;
    final int diagonalStepCost = 0;

    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        Position s = maze.getStartPosition();
        return new MazeState(s.getRowIndex(), s.getColumnIndex(), assignCost(s.getRowIndex() , s.getColumnIndex() , 0), null);
    }

    @Override
    public AState getGoalState() {
        Position g = maze.getGoalPosition();
        return new MazeState(g.getRowIndex(), g.getColumnIndex(), 0, null);
    }

    //note that cost is unspecified
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        if (state instanceof MazeState) {
            MazeState st = (MazeState) state;
            ArrayList<AState> arr = new ArrayList<>();
            boolean left = false, right = false, up = false, down = false;
            int r = st.getRow(), c = st.getColumn();
            int parentCost = st.getCost() - assignCost(st.getRow() , st.getColumn() , 0) ;

            if (r + 1 < maze.getMatrix().length && maze.getMatrix()[r + 1][c] == 0) {
                arr.add(new MazeState(r + 1, c, parentCost + assignCost(r + 1 , c , normalStepCost), st));
                down = true;
            }
            if (c + 1 < maze.getMatrix()[0].length && maze.getMatrix()[r][c + 1] == 0) {
                arr.add(new MazeState(r, c + 1, parentCost + assignCost(r , c + 1 , normalStepCost), st));
                right = true;
            }
            if (c - 1 >= 0 && maze.getMatrix()[r][c - 1] == 0) {
                arr.add(new MazeState(r, c - 1, parentCost + assignCost(r , c - 1 , normalStepCost), st));
                left = true;
            }
            if (r - 1 >= 0 && maze.getMatrix()[r - 1][c] == 0) {
                arr.add(new MazeState(r - 1, c, parentCost + assignCost(r - 1 , c , normalStepCost), st));
                up = true;
            }
            handleDiagonals(left, right, up, down, arr, st);
            return arr;
        }
        return null;
    }

    private void handleDiagonals(boolean left, boolean right, boolean up, boolean down, ArrayList<AState> arr, MazeState st) {
        int r = st.getRow(), c = st.getColumn();
        int parentCost = st.getCost() - assignCost(st.getRow() , st.getColumn() , 0) ;

        if (left || up) {
            if (c - 1 >= 0 && r - 1 >= 0 && maze.getMatrix()[r - 1][c - 1] == 0)
                arr.add(new MazeState(r - 1, c - 1, parentCost + assignCost(r - 1 , c - 1 , diagonalStepCost), st));
        }
        if (right || up) {
            if (c + 1 < maze.getMatrix()[0].length && r - 1 >= 0 && maze.getMatrix()[r - 1][c + 1] == 0)
                arr.add(new MazeState(r - 1, c + 1, parentCost + assignCost(r - 1 , c + 1 , diagonalStepCost), st));
        }
        if (right || down) {
            if (c + 1 < maze.getMatrix()[0].length && r + 1 < maze.getMatrix().length && maze.getMatrix()[r + 1][c + 1] == 0)
                arr.add(new MazeState(r + 1, c + 1, parentCost + assignCost(r + 1 , c + 1 , diagonalStepCost), st));
        }
        if (left || down) {
            if (r + 1 < maze.getMatrix().length && c - 1 >= 0 && maze.getMatrix()[r + 1][c - 1] == 0)
                arr.add(new MazeState(r + 1, c - 1,parentCost + assignCost(r + 1 , c - 1 , diagonalStepCost), st));
        }
    }

    private int assignCost(int r , int c , int estimatedFirstCost){
        MazeState goal = (MazeState) getGoalState();
        int distance = Math.abs(r - goal.row) + Math.abs(c - goal.column);
        return estimatedFirstCost + distance ;
    }

}
