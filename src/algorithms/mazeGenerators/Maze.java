package algorithms.mazeGenerators;

// Class representing a maze with start and goal positions, and a matrix for the maze structure
/**
 * Class representing a maze, including the maze grid, start position, and goal position.
 * This class provides methods to get and set the maze's start and goal positions,
 * as well as the maze's matrix representation.
 */
public class Maze {
    Position startPosition, goalPosition;  // Start and goal positions
    int[][] matrix;  // 2D array representing the maze grid

    /**
     * Constructor that create a maze
     * Initializes the maze matrix with the specified dimensions.
     *
     * @param rows the number of rows
     * @param columns the number of columns
     */
    public Maze(int rows, int columns) {
        matrix = new int[rows][columns];  // Initialize the maze matrix
    }

    /**
     * Constructor to create a maze with start and goal positions, and given rows and columns.
     * Initializes the maze matrix with the specified dimensions
     *
     * @param rows the number of rows
     * @param columns the number of columns
     * @param startPosition the starting position in the maze
     * @param goalPosition the goal - end - position in the maze
     */
    public Maze(int rows , int columns , Position startPosition, Position goalPosition) {
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
        matrix = new int[rows][columns];  // Initialize the maze matrix
    }

    /**
     * Getter for the start position
     *
     * @return the start position
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * Setter for the start position of the maze.
     *
     * @param startPosition the start position to set
     */
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Getter for the goal position of the maze.
     *
     * @return the goal position
     */
    public Position getGoalPosition() {
        return goalPosition;
    }

    /**
     * Setter for the goal position of the maze.
     *
     * @param goalPosition the goal position to set
     */
    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }

    /**
     * Getter for the maze matrix, representing the maze structure.
     *
     * @return the 2D array representing the maze
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Setter for the maze matrix, which represents the maze structure.
     *
     * @param matrix the 2D array representing the new maze structure
     */
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Method to print the maze
     * The method displays the maze structure and the position
     */
    public void print(){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if(startPosition.isEquals(i , j))  // Print 'S' for start
                    System.out.print("S ");
                else if(goalPosition.isEquals(i , j))  // Print 'E' for goal
                    System.out.print("E ");
                else
                    System.out.print(matrix[i][j] + " ");  // Print maze cell value
            }
            System.out.println();  // Move to the next line after each row
        }
    }
}
