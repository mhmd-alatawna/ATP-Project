package tests;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.util.ArrayList;

/**
 * The RunSearchOnMaze class is used to generate a maze and solve it using different search algorithms.
 */
public class RunSearchOnMaze {

    /**
     * The main method that generates a maze and solves it while using diffrent algoritmhics.
     */
    public static void main(String[] args) {
        // Creating an instance of the maze generator
        IMazeGenerator mg = new MyMazeGenerator();
        // Generating a 30x30 maze
        Maze maze = mg.generate(30, 30);
        // Wrapping the generated maze into a SearchableMaze object
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        // Solving the maze using different search algorithms
        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BestFirstSearch());
    }

    /**
     *  Solves the maze with the given algorithm and prints the path and stats.
     *
     * @param domain The maze/problem to solve
     * @param searcher The algorithm used to solve it
     */
    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
        // Solving the problem using the provided searcher
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
