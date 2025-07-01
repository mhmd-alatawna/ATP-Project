package org.example.algorithms.mazeGenerators;

// Abstract class for maze generators, implementing IMazeGenerator interface
/**
 * A class for maze generators
 * This class provides a method to measure the time taken to generate a maze.
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * Measures the time taken to generate a maze with the specified number of rows and columns.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return how much time taken to generate the maze in milliseconds
     */
    public long measureAlgorithmTimeMillis(int rows , int cols) {
        long before = System.currentTimeMillis();  // Record time before generation
        this.generate(rows, cols);  // Generate the maze
        long after = System.currentTimeMillis();  // Record time after generation
        return after - before;  // Return the time taken in milliseconds
    }
}
