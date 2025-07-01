package org.example.algorithms.maze3D;

/**
 *  class for 3D maze generators.
 */
public abstract class AMaze3DGenerator implements IMazeGenerator3D {

    /**
     * Measuress time it takes to generate a 3D maze
     *
     * @param depth the number of layers
     * @param rows  the number of rows in every layer
     * @param cols  the number of columns in every layer
     * @return the time in milliseconds took to generate the maze
     */
    @Override
    public long measureAlgorithmTimeMillis(int depth, int rows, int cols) {
        long before = System.currentTimeMillis(); // Start time
        this.generate(depth, rows, cols);         // Generate maze
        long after = System.currentTimeMillis();  // End time
        return after - before;                    // Duration
    }
}
