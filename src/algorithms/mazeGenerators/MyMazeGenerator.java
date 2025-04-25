package algorithms.mazeGenerators;

import java.util.ArrayList;

/**
 * This class generates a maze using Prim algo.
 */
public class MyMazeGenerator extends AMazeGenerator {

    // Constructor
    /**
     * Constructor
     */
    public MyMazeGenerator() {}

    /**
     * Generates a maze
     * The maze is being created with walls and then Prim algorithm is used to find out paths.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return a generated maze with start and end positions, or null if properties are invalid
     */
    @Override
    public Maze generate(int rows, int cols) {
        if(rows <= 0 || cols <= 0)
            return null;

        // Define start and end positions
        Position start = new Position(0, 0);
        Position end = new Position((int)(Math.random() * rows), cols - 1);
        Maze result = new Maze(rows, cols , start , end);

        fillWithOnes(result.getMatrix());  // Fill the maze with walls (1)

        // Make sure that start is open (0)
        result.getMatrix()[start.getRowIndex()][start.getColumnIndex()] = 0 ;

        primAlgoImplementation(result.getMatrix());  // Apply Prim's algorithm for maze generation

        // Make sure that end is open (0)
        result.getMatrix()[end.getRowIndex()][end.getColumnIndex()] = 0 ;

        // Handle case where the end position is surrounded by walls
        if(cols > 1)
            result.getMatrix()[end.getRowIndex()][end.getColumnIndex() - 1] = 0 ;
        else if(rows > 1)
            result.getMatrix()[end.getRowIndex() - 1][end.getColumnIndex()] = 0 ;

        return result;  // Return the generated maze
    }

    /**
     * Implementation of Prim algo to generate a maze.
     *
     * @param matrix the maze matrix to modify during the generation process
     */
    private void primAlgoImplementation(int[][] matrix) {
        ArrayList<Position> yetToHandle = new ArrayList<>();
        addNeighbours(matrix , yetToHandle , 0 , 0);  // Start from the top-left corner

        // While there are positions to handle
        while(!yetToHandle.isEmpty()){
            int randomIndex = (int) (Math.random() * yetToHandle.size());
            Position p = yetToHandle.remove(randomIndex);  // Get a random position
            int r = p.getRowIndex() , c = p.getColumnIndex();
            matrix[r][c] = 0;  // Open the cell

            findGoodNeighbour(matrix, r, c);  // Open a neighboring cell
            addNeighbours(matrix, yetToHandle, r, c);  // Add neighbors to the list
        }
    }

    /**
     * Adds valid neighboring positions
     *
     * @param matrix the maze matrix
     * @param yetToHandle the list of positions yet to be processed
     * @param r the row index of the current position
     * @param c the column index of the current position
     */
    private void addNeighbours(int[][] matrix , ArrayList<Position> yetToHandle, int r, int c){
        if(r + 2 < matrix.length && matrix[r + 2][c] == 1)
            addWithoutDuplicate(yetToHandle , new Position(r + 2, c)) ;
        if(r - 2 >= 0 && matrix[r - 2][c] == 1)
            addWithoutDuplicate(yetToHandle , new Position(r - 2, c)) ;
        if(c + 2 < matrix[0].length && matrix[r][c + 2] == 1)
            addWithoutDuplicate(yetToHandle , new Position(r, c + 2)) ;
        if(c - 2 >= 0 && matrix[r][c - 2] == 1)
            addWithoutDuplicate(yetToHandle , new Position(r, c - 2)) ;
    }

    /**
     * Adds a position to the list of positions yet to be handled without duplicates.
     * If the position is already in the list, it is not added again.
     *
     * @param yetToHandle the list of positions yet to be processed
     * @param toAdd the position to add
     */
    private void addWithoutDuplicate(ArrayList<Position> yetToHandle , Position toAdd){
        boolean found = false;
        for(Position p : yetToHandle){
            if (p.equals(toAdd)) {  // Check if the position is already in the list
                found = true;
                break;
            }
        }
        if(!found)
            yetToHandle.add(toAdd);  // Add position if it's not a duplicate
    }

    /**
     * Finds a neighboring position that can be opened and modifies the maze matrix.
     * A neighboring cell is valid if it is adjacent to an open cell
     *
     * @param matrix the maze matrix
     * @param r the row index of the current position
     * @param c the column index of the current position
     */
    private void findGoodNeighbour(int[][] matrix , int r , int c){
        ArrayList<Position> possibilities = new ArrayList<>();
        if(r + 2 < matrix.length && matrix[r + 2][c] == 0)
            possibilities.add(new Position(r + 1, c));
        if(r - 2 >= 0 && matrix[r - 2][c] == 0)
            possibilities.add(new Position(r - 1, c));
        if(c + 2 < matrix[0].length && matrix[r][c + 2] == 0)
            possibilities.add(new Position(r, c + 1));
        if(c - 2 >= 0 && matrix[r][c - 2] == 0)
            possibilities.add(new Position(r, c - 1));

        int randomIndex = (int) (Math.random() * possibilities.size());  // Choose a random possibility
        Position p = possibilities.get(randomIndex);
        matrix[p.getRowIndex()][p.getColumnIndex()] = 0 ;  // Open the chosen neighbor
    }

    /**
     * Fills the maze matrix with walls
     * @param matrix the maze matrix to fill with walls
     */
    private void fillWithOnes(int[][] matrix){
        for(int i = 0 ; i < matrix.length ; i++){
            for(int j = 0 ; j < matrix[i].length ; j++){
                matrix[i][j] = 1 ;  // Set all cells to 1 (walls)
            }
        }
    }
}
