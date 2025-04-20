package algorithms.search;

import java.util.ArrayList;

public class Solution {
    ArrayList<AState> solutionPath ;

    public Solution() {
        solutionPath = new ArrayList<>();
    }

    public ArrayList<AState> getSolutionPath() {
        return solutionPath;
    }

    public void addToStartOfSolutionPath(AState state){
        solutionPath.addFirst(state);
    }

    public void addToEndOfSolutionPath(AState state){
        solutionPath.addLast(state);
    }

    @Override
    public String toString() {
        return solutionPath.toString();
    }
}
