package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    LinkedList<AState> queue;
    HashMap<AState , Boolean> hashMap ;
    int numberOfNodesEvaluated ;

    public BreadthFirstSearch() {
        queue = new LinkedList<>();
        hashMap = new HashMap<>();
        numberOfNodesEvaluated = 0 ;
    }

    @Override
    public Solution solve(ISearchable searchable) {
        AState goal = null ;

        if(searchable.getGoalState().equals(searchable.getStartState())){
            return searchable.getGoalState().backTrack();
        }
        hashMap.put(searchable.getStartState(), Boolean.TRUE);
        addState(searchable.getStartState());
        while(!isEmpty() && goal == null){
            AState state = popState();
            numberOfNodesEvaluated ++ ;
            for(AState s : searchable.getAllPossibleStates(state)){
                if(hashMap.get(s) == null) {
                    if(s.equals(searchable.getGoalState())){
                        goal = s ;
                        break;
                    }
                    addState(s);
                    hashMap.put(s, Boolean.TRUE);
                }
            }
        }

        if(goal == null)
            return null;
        return goal.backTrack();
    }

    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return numberOfNodesEvaluated ;
    }

    protected void addState(AState state){
        queue.addFirst(state);
    }

    protected AState popState(){
        return queue.removeLast() ;
    }

    protected boolean isEmpty(){
        return queue.isEmpty() ;
    }

}
