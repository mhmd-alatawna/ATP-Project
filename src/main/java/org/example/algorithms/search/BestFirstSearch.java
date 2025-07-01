package org.example.algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * BestFirstSearch class mplements the Best-First Search algorithm.
 */
public class BestFirstSearch extends BreadthFirstSearch {
    // Priority queue to hold the states to be evaluated, prioritized by their cost
    PriorityQueue<AState> priorityQueue;

    /**
     * Constructor to initialize the BestFirstSearch algorithm.
     * Initializes the number of nodes evaluated and creates the priority queue for state messurment.
     */
    public BestFirstSearch() {
        // Initialize the number of nodes evaluated
        numberOfNodesEvaluated = 0;

        // Create the priority queue with a custom comparator to compare states by cost
        priorityQueue = new PriorityQueue<AState>(new Comparator<AState>() {
            @Override
            public int compare(AState o1, AState o2) {
                // Compare states by their cost (used for prioritization)
                return o1.cost - o2.cost;
            }
        });
    }

    /**
     * method to return the name of the algorithm.
     * @return The name of the algorithm
     */
    @Override
    public String getName() {
        return "BestFirstSearch";
    }

    /**
     * method to add a state to the priority queue for evaluation.
     * @param state The state to be added to the priority queue.
     */
    @Override
    protected void addState(AState state) {
        priorityQueue.add(state);
    }

    /**
     * method to remove and return the state with the highest priority (lowest cost) from the priority queue.
     * @return The state with the highest priority (lowest cost).
     */
    @Override
    protected AState popState() {
        return priorityQueue.remove();
    }

    /**
     * method to check if the priority queue is empty.
     * @return True if the priority queue is empty, false otherwise.
     */
    @Override
    protected boolean isEmpty() {
        return priorityQueue.isEmpty();
    }
}
