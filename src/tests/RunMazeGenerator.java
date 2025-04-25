package tests;
import algorithms.mazeGenerators.*;

/**
 * The RunMazeGenerator class is used to test different maze generation algorithms.
 */
public class RunMazeGenerator {

    /**
     * The main method that runs the maze generation test for three different maze generators.
     */
    public static void main(String[] args) {
        // Testing with three different maze generators
        testMazeGenerator(new EmptyMazeGenerator());
        testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());
    }

    /**
     * This method tests the maze generator by measuring the time
     * printing the generated maze
     *
     * @param mazeGenerator The maze generator to be tested.
     */
    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
        // Prints the time it takes the algorithm to run
        System.out.println(String.format("Maze generation time(ms): %s ", mazeGenerator.measureAlgorithmTimeMillis(100/*rows*/,100/*columns*/)));

        // Generate another maze with the given dimensions
        Maze maze = mazeGenerator.generate(100/*rows*/, 100/*columns*/);

        // Prints the maze structure
        maze.print();

        // Get the maze entrance (start position)
        Position startPosition = maze.getStartPosition();

        // Print the start position
        System.out.println(String.format("Start Position: %s", startPosition)); // format "{row,column}"

        // Prints the maze exit position (goal position)
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));
    }
}
