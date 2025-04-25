package algorithms.maze3D;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Maze generator that creates 3D mazes using Prim's algorithm.
 */
public class MyMaze3DGenerator extends AMaze3DGenerator {

    /**
     * Default constructor
     */
    public MyMaze3DGenerator() {}

    /**
     * Generates a 3D maze using Prim's algorithm.
     *
     * @param depth the number of layers
     * @param rows the number of rows in each laye
     * @param cols the number of columns in each layer
     * @return a generated Maze3D object or null if invalid
     */
    @Override
    public Maze3D generate(int depth, int rows, int cols) {
        if(rows <= 0 || cols <= 0 || depth < 0)
            return null;

        Position3D start = new Position3D(0, 0, 0);
        Position3D end = new Position3D((int)(Math.random() * depth), (int)(Math.random() * rows), cols - 1);
        Maze3D result = new Maze3D(depth, rows, cols, start, end);

        fillWithOnes(result.getMatrix());
        result.getMatrix()[start.getDepthIndex()][start.getRowIndex()][start.getColumnIndex()] = 0;
        primAlgoImplementation(result.getMatrix(), start);
        result.getMatrix()[end.getDepthIndex()][end.getRowIndex()][end.getColumnIndex()] = 0;

        if (cols > 1)
            result.getMatrix()[end.getDepthIndex()][end.getRowIndex()][end.getColumnIndex() - 1] = 0;
        else if (rows > 1)
            result.getMatrix()[end.getDepthIndex()][end.getRowIndex() - 1][end.getColumnIndex()] = 0;
        else if (depth > 1)
            result.getMatrix()[end.getDepthIndex() - 1][end.getRowIndex()][end.getColumnIndex()] = 0;

        return result;
    }

    /**
     * Internal method implementing Prim's algorithm
     *
     * @param matrix the 3D matrix which is the maze
     * @param start the starting position for maze
     */
    private void primAlgoImplementation(int[][][] matrix, Position3D start) {
        ArrayList<Position3D> yetToHandle = new ArrayList<>();
        HashMap<String, Boolean> map = new HashMap<>();
        map.put(start.toString(), Boolean.TRUE);

        addNeighbours(matrix, yetToHandle, map, 0, 0, 0);

        while (!yetToHandle.isEmpty()) {
            int randomIndex = (int) (Math.random() * yetToHandle.size());
            Position3D p = yetToHandle.remove(randomIndex);
            int d = p.getDepthIndex(), r = p.getRowIndex(), c = p.getColumnIndex();

            matrix[d][r][c] = 0;
            findGoodNeighbour(matrix, d, r, c);
            addNeighbours(matrix, yetToHandle, map, d, r, c);
        }
    }

    /**
     * Adds valid, unvisited neighborsto the frontier list.
     *
     * @param matrix the 3D maze
     * @param yetToHandle list of cells ready for processing in the future
     * @param map hash map to track visited cells
     * @param d current depth
     * @param r current row
     * @param c current column
     */
    private void addNeighbours(int[][][] matrix, ArrayList<Position3D> yetToHandle, HashMap<String, Boolean> map, int d, int r, int c) {
        if (r + 2 < matrix[0].length && matrix[d][r + 2][c] == 1)
            addWithoutDuplicate(yetToHandle, map, new Position3D(d, r + 2, c));
        if (r - 2 >= 0 && matrix[d][r - 2][c] == 1)
            addWithoutDuplicate(yetToHandle, map, new Position3D(d, r - 2, c));
        if (c + 2 < matrix[0][0].length && matrix[d][r][c + 2] == 1)
            addWithoutDuplicate(yetToHandle, map, new Position3D(d, r, c + 2));
        if (c - 2 >= 0 && matrix[d][r][c - 2] == 1)
            addWithoutDuplicate(yetToHandle, map, new Position3D(d, r, c - 2));
        if (d + 2 < matrix.length && matrix[d + 2][r][c] == 1)
            addWithoutDuplicate(yetToHandle, map, new Position3D(d + 2, r, c));
        if (d - 2 >= 0 && matrix[d - 2][r][c] == 1)
            addWithoutDuplicate(yetToHandle, map, new Position3D(d - 2, r, c));
    }

    /**
     * Adds a cell to the frontier list only if it hasn't been added already.
     *
     * @param yetToHandle frontier list
     * @param map visited map
     * @param toAdd the position to be added
     */
    private void addWithoutDuplicate(ArrayList<Position3D> yetToHandle, HashMap<String, Boolean> map, Position3D toAdd) {
        if (map.get(toAdd.toString()) != Boolean.TRUE) {
            map.put(toAdd.toString(), Boolean.TRUE);
            yetToHandle.add(toAdd);
        }
    }

    /**
     * Connects the current cell with a neighboring cell that has already been carved out.
     *
     * @param matrix the 3D maze matrix
     * @param d current depth
     * @param r current row
     * @param c current column
     */
    private void findGoodNeighbour(int[][][] matrix, int d, int r, int c) {
        ArrayList<Position3D> possibilities = new ArrayList<>();

        if (r + 2 < matrix[0].length && matrix[d][r + 2][c] == 0)
            possibilities.add(new Position3D(d, r + 1, c));
        if (r - 2 >= 0 && matrix[d][r - 2][c] == 0)
            possibilities.add(new Position3D(d, r - 1, c));
        if (c + 2 < matrix[0][0].length && matrix[d][r][c + 2] == 0)
            possibilities.add(new Position3D(d, r, c + 1));
        if (c - 2 >= 0 && matrix[d][r][c - 2] == 0)
            possibilities.add(new Position3D(d, r, c - 1));
        if (d + 2 < matrix.length && matrix[d + 2][r][c] == 0)
            possibilities.add(new Position3D(d + 1, r, c));
        if (d - 2 >= 0 && matrix[d - 2][r][c] == 0)
            possibilities.add(new Position3D(d - 1, r, c));

        int randomIndex = (int) (Math.random() * possibilities.size());
        Position3D p = possibilities.get(randomIndex);
        matrix[p.getDepthIndex()][p.getRowIndex()][p.getColumnIndex()] = 0;
    }

    /**
     * Fills the maze matrix entirely with walls
     *
     * @param matrix the 3D matrix representing the maze
     */
    private void fillWithOnes(int[][][] matrix) {
        for (int k = 0; k < matrix.length; k++) {
            for (int i = 0; i < matrix[k].length; i++) {
                for (int j = 0; j < matrix[k][i].length; j++) {
                    matrix[k][i][j] = 1;
                }
            }
        }
    }
}
