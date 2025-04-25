package algorithms.mazeGenerators;

// Class that generates an empty maze, extending AMazeGenerator
/**
 * A  class that generates an empty maze
 */
public class EmptyMazeGenerator extends AMazeGenerator {

    /**
     * Default constructor
     */
    public EmptyMazeGenerator() {}  // Constructor for EmptyMazeGenerator

    /**
     * Generates an empty maze
     * The start position is randomly selected on the left side, and the end position
     * is randomly selected on the right side of the maze.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return a new Maze object representing the generated empty maze, or null if are invalid
     */
    @Override
    public Maze generate(int rows, int cols) {
        if (rows <= 0 || cols <= 0)  // Check for invalid maze dimensions
            return null;  // Return null for invalid maze size

        // Randomly select a start position on the left side
        Position start = new Position((int)(Math.random() * rows), 0);

        // Randomly select an end position on the right side
        Position end = new Position((int)(Math.random() * rows), cols - 1);

        // Return a new maze object with the generated start and end positions
        return new Maze(rows , cols , start , end);
    }
}
