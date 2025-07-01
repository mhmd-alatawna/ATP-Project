package org.example.algorithms.search;

import java.util.ArrayList;

/**
 * ISearchable interface defines the methods required for a searchable problem.
 */
public interface ISearchable {

    /**
     * Method to get the start state of the search problem.
     * @return The start state of the problem.
     */
    AState getStartState();

    /**
     * Method to get the goal state of the search problem.
     * @return The goal state of the problem.
     */
    AState getGoalState();

    /**
     * Method to get all possible states that can be reached from the givenne state
     * @param state The current state from which you need to retrieve all possible next states.
     * @return A list of all possible states that can be reached from the state that was given.
     */
    ArrayList<AState> getAllPossibleStates(AState state);
}
