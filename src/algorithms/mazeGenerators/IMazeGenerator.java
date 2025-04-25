package algorithms.mazeGenerators;

/**
 * Interface for defining methods used for generating mazes.
 * and measuring the time taken to generate them.
 */
public interface IMazeGenerator {

    /**
     * Generates a maze
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return a Maze object representing the generated maze
     */
    Maze generate(int rows , int cols);

    /**
     * Measures the time taken to generate a maze
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return the time taken to generate the maze in milliseconds
     */
    long measureAlgorithmTimeMillis(int rows , int cols);
}
