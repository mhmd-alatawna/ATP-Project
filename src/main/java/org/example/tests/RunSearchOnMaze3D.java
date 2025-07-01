package org.example.tests;

import org.example.algorithms.maze3D.*;
import org.example.algorithms.search.*;

import java.util.ArrayList;

/**
 * The RunSearchOnMaze3D class generates a 3D maze and solves it using various search algorithms.
 */
public class RunSearchOnMaze3D {

    /**
     * The main method generates a 3D maze and solves it using different algorithms.
     *
     */
    public static void main(String[] args) {
        // Creating an instance of the 3D maze generator
        IMazeGenerator3D mg = new MyMaze3DGenerator();
        // Generating a 30x30x30 3D maze
        Maze3D maze = mg.generate(30, 30, 30);
        // Wrapping the generated 3D maze into a SearchableMaze3D object
        SearchableMaze3D searchableMaze = new SearchableMaze3D(maze);

        // Solving the 3D maze using different search algorithms
        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BestFirstSearch());
    }
    /**
     * Solves the 3D maze with the given search algorithm and prints the results,
     * including the evaluated nodes and the solution path.
     *
     * @param domain The problem to solve
     * @param searcher The search algorithm to use
     */
    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
        // Solving the 3D maze problem using the provided searcher
        Solution solution = searcher.solve(domain);

        // Printing the number of nodes evaluated by the search algorithm
        System.out.printf("'%s' algorithm - nodes evaluated: %s%n", searcher.getName(), searcher.getNumberOfNodesEvaluated());

        // Printing the solution path
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        // Iterating over the solution path and printing each state
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s", i, solutionPath.get(i)));
        }
    }
}
