package org.example.algorithms.search;

/**
 * ISearchingAlgorithm interface defines the structure for searching algorithms.
 */
public interface ISearchingAlgorithm {

    /**
     * Method to solve the search problem for a given searchable object.
     * @param searchable The searchable object containing the problem that need to be solved
     * @return The solution
     */
    Solution solve(ISearchable searchable);

    /**
     * Method to get the name of the algorithm.
     * @return The name of the algorithm.
     */
    String getName();

    /**
     * Method to get the number of nodes that being judge during the search.
     * @return The number of nodes that was being judge
     */
    int getNumberOfNodesEvaluated();
}
