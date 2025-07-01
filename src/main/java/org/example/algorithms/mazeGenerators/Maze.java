package org.example.algorithms.mazeGenerators;

// Class representing a maze with start and goal positions, and a matrix for the maze structure

import java.io.Serializable;

/**
 * Class representing a maze, including the maze grid, start position, and goal position.
 * This class provides methods to get and set the maze's start and goal positions,
 * as well as the maze's matrix representation.
 */
public class Maze implements Serializable {
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

    public Maze(byte[] array) {
        int row = fromByteArrayToInt(array , 0) ;
        int column = fromByteArrayToInt(array , 4) ;
        startPosition = new Position(row, column);
        row = fromByteArrayToInt(array , 8) ;
        column = fromByteArrayToInt(array , 12) ;
        goalPosition = new Position(row, column);
        row = fromByteArrayToInt(array , 16) ;
        column = fromByteArrayToInt(array , 20) ;
        matrix = new int[row][column];

        for(int i = 24 ; i < Math.min(array.length , 24 + row * column) ; i++) {
            int r = (i - 24) / matrix[0].length;
            int c = (i - 24) % matrix[0].length;
            matrix[r][c] = array[i];
        }
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

    public byte[] toByteArray(){
        //first 28 bytes represent meta-data about the maze
        int columns = 0 ;
        if(matrix.length > 0)
            columns = this.matrix[0].length ;
        byte[] bytes = new byte[matrix.length * columns + 24];
        fill(bytes , fromIntToByteArray(this.startPosition.rowIndex) , 0) ;
        fill(bytes , fromIntToByteArray(this.startPosition.columnIndex) , 4) ;
        fill(bytes , fromIntToByteArray(this.goalPosition.rowIndex) , 8) ;
        fill(bytes , fromIntToByteArray(this.goalPosition.columnIndex) , 12) ;
        fill(bytes , fromIntToByteArray(this.matrix.length) , 16) ;//matrix row count
        fill(bytes , fromIntToByteArray(columns) , 20) ;//matrix column count

        //here we save the matrix itself
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                bytes[24 + i * matrix[i].length + j] = (byte) (matrix[i][j] & 0xFF) ;
            }
        }
        return bytes ;
    }

    private void fill(byte[] destination , byte[] source , int start){
        for(int i = 0; i < source.length; i++){
            destination[start + i] = source[i];
        }
    }
    private byte[] fromIntToByteArray(int x) {
        byte[] arr = new byte[4];
        arr[3] = (byte) (x);
        arr[2] = (byte) (x >> 8);
        arr[1] = (byte) (x >> 16);
        arr[0] = (byte) (x >> 24);
        return arr;
    }
    private int fromByteArrayToInt(byte[] arr , int start) {
        return (arr[start + 3] & 0xFF) | ((arr[start + 2] & 0xFF) << 8) | ((arr[start + 1] & 0xFF) << 16) | ((arr[start] & 0xFF) << 24);
    }
}
