package algorithms.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    LinkedList<AState> queue;
    HashMap<AState , AState> closedList;
    HashMap<AState , AState> openList;
    int numberOfNodesEvaluated ;

    public BreadthFirstSearch() {
        queue = new LinkedList<>();
        closedList = new HashMap<>();
        openList = new HashMap<>();
        numberOfNodesEvaluated = 0 ;
    }

    @Override
    public Solution solve(ISearchable searchable) {
        AState goal = null ;
        AState startState = searchable.getStartState() , goalState = searchable.getGoalState() ;
        if(goalState.equals(startState)){
            return goalState.backTrack();
        }
        addState(startState);
        openList.put(startState, startState);
        while(!isEmpty() && goal == null){
            AState state = popState();
            openList.remove(state) ;
            numberOfNodesEvaluated ++ ;
            ArrayList<AState> arr = searchable.getAllPossibleStates(state) ;
            for(AState s : arr){
                AState closedListResult = closedList.get(s) ;
                if(closedListResult == null) {
                    if (s.equals(goalState)) {
                        goal = s;
                        break;
                    }
                    handleNeighbour(s);
                }
            }
            closedList.put(state, state);
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

    protected void handleNeighbour(AState s){
        AState openListResult = openList.get(s) ;
        if(openListResult == null){
            addState(s);
            openList.put(s, s);
        }else{
            if(openListResult.getCost() > s.getCost())
                openListResult.setCost(s.getCost());
        }
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
