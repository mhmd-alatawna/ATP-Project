package algorithms.mazeGenerators;

/**
 * A  maze generator that creates mazes with random walls and empty spaces.
 */
public class SimpleMazeGenerator extends AMazeGenerator {
    double wallPercentage;

    /**
     * Constructor to initialize the maze generator
     *
     * @param wallPercentage the percentage of the maze that should be walls
     */
    public SimpleMazeGenerator(double wallPercentage) {
        this.wallPercentage = wallPercentage;
    }

    /**
     * Default constructor that initializes the maze generator with a default wall percentage of 50%.
     */
    public SimpleMazeGenerator() {
        this.wallPercentage = 0.5;
    }

    /**
     * Generates a maze with random walls and empty spaces based on the wall percentage.
     * Ensures there is at least one valid path from start to goal.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return the generated maze, or null if the properites are invalid
     */
    @Override
    public Maze generate(int rows, int cols) {
        if (rows <= 0 || cols <= 0)
            return null; // Return null if dimensions are invalid

        // Randomly generate the start and goal positions
        Position start = new Position((int) (Math.random() * rows), 0);
        Position end = new Position((int) (Math.random() * rows), cols - 1);

        Maze result = new Maze(rows, cols, start, end);

        // Fill the maze with walls and empty spaces based on wallPercentage
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((int) (Math.random() * 101) + 1 - (int) (100 * wallPercentage) < 0) {
                    result.getMatrix()[i][j] = 1; // Wall (1)
                } else {
                    result.getMatrix()[i][j] = 0; // Empty space (0)
                }
            }
        }

        // Ensure there is at least one valid solution
        correctMaze(result);

        return result;
    }

    /**
     * make surte that there is a valid path
     * It guarantees a  path from the start to the goal
     * @param maze the maze to correct, ensuring a valid path
     */
    private void correctMaze(Maze maze) {
        // Make sure the path between start and goal is clear
        if (maze.getStartPosition().getRowIndex() <= maze.getGoalPosition().getRowIndex()) {
            // Create a path vertically from start to goal
            for (int i = maze.getStartPosition().getRowIndex(); i <= maze.getGoalPosition().getRowIndex(); i++) {
                maze.getMatrix()[i][0] = 0; // Set the first column as a path
            }
        } else {
            // Create a path vertically from goal to start
            for (int i = maze.getGoalPosition().getRowIndex(); i <= maze.getStartPosition().getRowIndex(); i++) {
                maze.getMatrix()[i][0] = 0; // Set the first column as a path
            }
        }

        // Make sure the entire row at the goal position is clear (horizontally)
        for (int i = 0; i < maze.getMatrix()[0].length; i++) {
            maze.getMatrix()[maze.getGoalPosition().getRowIndex()][i] = 0; // Clear goal row
        }
    }
}
