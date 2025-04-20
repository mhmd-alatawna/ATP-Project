package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class DepthFirstSearch implements ISearchingAlgorithm{
    Stack<AState> stack;
    HashMap<AState,Boolean> map;
    int numberOfNodesEvaluated;

    public DepthFirstSearch() {
        stack = new Stack<>();
        map = new HashMap<>();
        numberOfNodesEvaluated = 0 ;
    }
    @Override
    public Solution solve(ISearchable searchable) {
        AState goal = null ;
        if(searchable.getGoalState().equals(searchable.getStartState()))
            return searchable.getGoalState().backTrack();

        stack.add(searchable.getStartState()) ;
        while(!stack.isEmpty()){
            AState state = stack.pop();
            map.put(state , Boolean.TRUE) ;
            numberOfNodesEvaluated++;
            for(AState s : searchable.getAllPossibleStates(state)){
                if(map.get(s) == null){
                    if(s.equals(searchable.getGoalState())){
                        goal = s ;
                        break ;
                    }
                    stack.push(s);
                }
            }
        }
        if(goal == null)
            return null ;
        return goal.backTrack();
    }

    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated;
    }
}
