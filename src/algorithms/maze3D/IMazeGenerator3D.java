package algorithms.maze3D;

/**
 * Interface for 3D maze generators.
 * Provides methods for generating 3D mazes and caculating algorithm performance.
 */
public interface IMazeGenerator3D {

    /**
     * Generates a new 3D maze
     *
     * @param depth  the number of layers
     * @param row    the number of rows in each layer
     * @param column the number of columns in each layer
     * @return a generated maze3d object
     */
    Maze3D generate(int depth, int row, int column);

    /**
     * Measures the time in milliseconds
     *
     * @param depth  the number of layers
     * @param row    the number of rows in each layer
     * @param column the number of columns in each layer
     * @return the duration in milliseconds to generate the maze
     */
    long measureAlgorithmTimeMillis(int depth, int row, int column);
}
