package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {
    Maze3D maze;
    final int normalStepCost = 0;

    public SearchableMaze3D(Maze3D maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        Position3D s = maze.getStartPosition();
        return new Maze3DState(s.getDepthIndex() , s.getRowIndex(), s.getColumnIndex(), assignCost(s.getDepthIndex() , s.getRowIndex() , s.getColumnIndex() , 0), null);
    }

    @Override
    public AState getGoalState() {
        Position3D g = maze.getGoalPosition();
        return new Maze3DState(g.getDepthIndex() , g.getRowIndex(), g.getColumnIndex(), 0, null);
    }

    //note that cost is unspecified
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        if (state instanceof Maze3DState) {
            Maze3DState st = (Maze3DState) state;
            ArrayList<AState> arr = new ArrayList<>();
            int d = st.getDepth() , r = st.getRow(), c = st.getColumn();
            int parentCost = st.getCost() - assignCost(st.getDepth() , st.getRow() , st.getColumn() , 0) ;

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
        return null;
    }
    private int assignCost(int d , int r , int c , int estimatedFirstCost){
        Maze3DState goal = (Maze3DState) getGoalState();
        int distance = Math.abs(d - goal.getDepth()) + Math.abs(r - goal.row) + Math.abs(c - goal.column);
        return estimatedFirstCost + distance ;
    }

}
