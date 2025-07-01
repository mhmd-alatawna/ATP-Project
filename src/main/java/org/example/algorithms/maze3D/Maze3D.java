package org.example.algorithms.maze3D;

/**
 * class that represents a 3D maze composed of multiple layers, rows, and columns.
 */
public class Maze3D {
    Position3D startPosition; // Start point
    Position3D goalPosition;  // End point
    int[][][] matrix;         // Maze grid

    /**
     * Constructs a new 3D maze
     *
     * @param depth         the number of layers
     * @param rows          the number of rows in each layer
     * @param columns       the number of columns in each layer
     * @param startPosition the starting position
     * @param goalPosition  the goal (ending) position
     */
    public Maze3D(int depth, int rows, int columns, Position3D startPosition, Position3D goalPosition) {
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
        matrix = new int[depth][rows][columns];
    }

    /**
     * Returns the start position of th e maze
     *
     * @return the start position
     */
    public Position3D getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the start position of the maze.
     *
     * @param startPosition the new start position
     */
    public void setStartPosition(Position3D startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Returns the goal (end) position of the maze.
     *
     * @return the goal position
     */
    public Position3D getGoalPosition() {
        return goalPosition;
    }

    /**
     * Sets the goal position of the maze.
     *
     * @param goalPosition the new goal position
     */
    public void setGoalPosition(Position3D goalPosition) {
        this.goalPosition = goalPosition;
    }

    /**
     * Returns the 3D matrix representing the maze
     *
     * @return the maze matrix
     */
    public int[][][] getMatrix() {
        return matrix;
    }

    /**
     * Sets the 3d matrix representing the maze
     *
     * @param matrix the new maze matrix
     */
    public void setMatrix(int[][][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Prints the 3D maze by layers
     * Marks the start position with 'S' and the goal position with 'E'.
     */
    public void print() {
        for (int k = 0; k < matrix.length; k++) {
            if (k != 0)
                System.out.println("---------------------------------------");
            for (int i = 0; i < matrix[k].length; i++) {
                for (int j = 0; j < matrix[k][i].length; j++) {
                    if (startPosition.isEquals(k, i, j))
                        System.out.print("S ");
                    else if (goalPosition.isEquals(k, i, j))
                        System.out.print("E ");
                    else
                        System.out.print(matrix[k][i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}
