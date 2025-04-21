package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{
    PriorityQueue<AState> priorityQueue;

    public BestFirstSearch() {
        numberOfNodesEvaluated = 0 ;
        priorityQueue = new PriorityQueue<AState>(new Comparator<AState>() {
            @Override
            public int compare(AState o1, AState o2) {
                return o1.cost - o2.cost;
            }
        });
    }


    @Override
    public String getName() {
        return "BestFirstSearch";
    }

    @Override
    protected void addState(AState state){
        priorityQueue.add(state);
    }

    @Override
    protected AState popState(){
        return priorityQueue.remove();
    }

    @Override
    protected boolean isEmpty(){
        return priorityQueue.isEmpty() ;
    }


}
