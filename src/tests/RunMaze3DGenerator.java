package tests;

import algorithms.maze3D.*;

/**
 * The RunMaze3DGenerator class is used to test the maze generation algorithm

 */
public class RunMaze3DGenerator {

    /**
     * The main method that runs the maze generation test.
     */
    public static void main(String[] args) {
        // Call the testMazeGenerator method with an instance of MyMaze3DGenerator
        testMazeGenerator(new MyMaze3DGenerator());
    }

    /**
     * This method tests the maze generator by time
     * printing the generated maze
     *
     * @param mazeGenerator The maze generator to be tested.
     */
    private static void testMazeGenerator(IMazeGenerator3D mazeGenerator) {
        // Prints the time it takes the algorithm to run
        System.out.println(String.format("Maze generation time(ms): %s ", mazeGenerator.measureAlgorithmTimeMillis(100 , 100 , 100)));

        // Generate another maze
        Maze3D maze = mazeGenerator.generate(5 , 5, 5);

        // Prints the maze
        maze.print();

        // Get the maze entrance
        Position3D startPosition = maze.getStartPosition();

        // Print the start position
        System.out.println(String.format("Start Position: %s", startPosition)); // format "{row,column}"

        // Prints the maze exit position
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));
    }
}
