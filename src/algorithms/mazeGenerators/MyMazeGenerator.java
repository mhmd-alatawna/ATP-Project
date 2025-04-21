package algorithms.mazeGenerators;

import java.util.ArrayList;

public class MyMazeGenerator extends AMazeGenerator {
    public MyMazeGenerator() {}

    @Override
    public Maze generate(int rows, int cols) {
        if(rows <= 0 || cols <= 0)
            return null;

        Position start = new Position(0, 0);
        Position end = new Position((int)(Math.random() * rows), cols - 1);
        Maze result = new Maze(rows, cols , start , end);
        fillWithOnes(result.getMatrix());
        //make sure that start is 0
        result.getMatrix()[start.getRowIndex()][start.getColumnIndex()] = 0 ;

        primAlgoImplementation(result.getMatrix());
        //make sure that end is 0
        result.getMatrix()[end.getRowIndex()][end.getColumnIndex()] = 0 ;

        //in case there is no solution due to end position being surrounded by 1
        if(cols > 1)
            result.getMatrix()[end.getRowIndex()][end.getColumnIndex() - 1] = 0 ;
        else if(rows > 1)
            result.getMatrix()[end.getRowIndex() - 1][end.getColumnIndex()] = 0 ;
        return result;
    }

    private void primAlgoImplementation(int[][] matrix) {
        ArrayList<Position> yetToHandle = new ArrayList<>();
        addNeighbours(matrix , yetToHandle , 0 , 0);
        while(!yetToHandle.isEmpty()){
            int randomIndex = (int) (Math.random() * yetToHandle.size());
            Position p = yetToHandle.remove(randomIndex);
            int r = p.getRowIndex() , c = p.getColumnIndex() ;
            matrix[r][c] = 0;
            findGoodNeighbour(matrix, r, c);
            addNeighbours(matrix, yetToHandle, r, c);
        }
    }

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

    private void addWithoutDuplicate(ArrayList<Position> yetToHandle , Position toAdd){
        boolean found = false ;
        for(Position p : yetToHandle){
            if (p.equals(toAdd)) {
                found = true;
                break;
            }
        }
        if(!found)
            yetToHandle.add(toAdd);
    }

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

        int randomIndex = (int) (Math.random() * possibilities.size());
        Position p = possibilities.get(randomIndex);
        matrix[p.getRowIndex()][p.getColumnIndex()] = 0 ;
    }

    private void fillWithOnes(int[][] matrix){
        for(int i = 0 ; i < matrix.length ; i++){
            for(int j = 0 ; j < matrix[i].length ; j++){
                matrix[i][j] = 1 ;
            }
        }
    }
}