package algorithms.maze3D;

import java.util.ArrayList;

public class MyMaze3DGenerator extends AMaze3DGenerator{
    public MyMaze3DGenerator() {}

    @Override
    public Maze3D generate(int depth, int rows, int cols) {
        if(rows <= 0 || cols <= 0 || depth < 0)
            return null;

        Position3D start = new Position3D(0, 0 , 0);
        Position3D end = new Position3D((int)(Math.random() * depth),  (int)(Math.random() * rows) , cols - 1);
        Maze3D result = new Maze3D(depth ,rows, cols , start , end);
        fillWithOnes(result.getMatrix());
        //make sure that start is 0
        result.getMatrix()[start.getDepthIndex()][start.getRowIndex()][start.getColumnIndex()] = 0 ;

        primAlgoImplementation(result.getMatrix());
        //make sure that end is 0
        result.getMatrix()[end.getDepthIndex()][end.getRowIndex()][end.getColumnIndex()] = 0 ;

        //in case there is no solution due to end position being surrounded by 1
        if(cols > 1)
            result.getMatrix()[end.getDepthIndex()][end.getRowIndex()][end.getColumnIndex() - 1] = 0 ;
        else if(rows > 1)
            result.getMatrix()[end.getDepthIndex()][end.getRowIndex() - 1][end.getColumnIndex()] = 0 ;
        else if(depth > 1)
            result.getMatrix()[end.getDepthIndex() - 1][end.getRowIndex()][end.getColumnIndex()] = 0 ;
        return result;
    }

    private void primAlgoImplementation(int[][][] matrix) {
        ArrayList<Position3D> yetToHandle = new ArrayList<>();
        addNeighbours(matrix , yetToHandle , 0 ,0 , 0);
        while(!yetToHandle.isEmpty()){
            int randomIndex = (int) (Math.random() * yetToHandle.size());
            Position3D p = yetToHandle.remove(randomIndex);
            int d = p.getDepthIndex() , r = p.getRowIndex() , c = p.getColumnIndex() ;
            matrix[d][r][c] = 0;
            findGoodNeighbour(matrix, d, r, c);
            addNeighbours(matrix, yetToHandle , d , r, c);
        }
    }

    private void addNeighbours(int[][][] matrix , ArrayList<Position3D> yetToHandle , int d , int r, int c){
        if(r + 2 < matrix[0].length && matrix[d][r + 2][c] == 1)
            addWithoutDuplicate(yetToHandle , new Position3D(d,r + 2, c)) ;
        if(r - 2 >= 0 && matrix[d][r - 2][c] == 1)
            addWithoutDuplicate(yetToHandle , new Position3D(d,r - 2, c)) ;
        if(c + 2 < matrix[0][0].length && matrix[d][r][c + 2] == 1)
            addWithoutDuplicate(yetToHandle , new Position3D(d,r, c + 2)) ;
        if(c - 2 >= 0 && matrix[d][r][c - 2] == 1)
            addWithoutDuplicate(yetToHandle , new Position3D(d,r, c - 2)) ;
        if(d + 2 < matrix.length && matrix[d + 2][r][c] == 1)
            addWithoutDuplicate(yetToHandle , new Position3D(d + 2,r, c)) ;
        if(d - 2 >= 0 && matrix[d - 2][r][c] == 1)
            addWithoutDuplicate(yetToHandle , new Position3D(d - 2,r, c)) ;
    }

    private void addWithoutDuplicate(ArrayList<Position3D> yetToHandle , Position3D toAdd){
        boolean found = false ;
        for(Position3D p : yetToHandle){
            if (p.equals(toAdd)) {
                found = true;
                break;
            }
        }
        if(!found)
            yetToHandle.add(toAdd);
    }

    private void findGoodNeighbour(int[][][] matrix , int d , int r , int c){
        ArrayList<Position3D> possibilities = new ArrayList<>();
        if(r + 2 < matrix[0].length && matrix[d][r + 2][c] == 0)
            possibilities.add(new Position3D(d,r + 1, c));
        if(r - 2 >= 0 && matrix[d][r - 2][c] == 0)
            possibilities.add(new Position3D(d,r - 1, c));
        if(c + 2 < matrix[0][0].length && matrix[d][r][c + 2] == 0)
            possibilities.add(new Position3D(d,r, c + 1));
        if(c - 2 >= 0 && matrix[d][r][c - 2] == 0)
            possibilities.add(new Position3D(d,r, c - 1));
        if(d + 2 < matrix.length && matrix[d + 2][r][c] == 0)
            possibilities.add(new Position3D(d + 1,r, c)) ;
        if(d - 2 >= 0 && matrix[d - 2][r][c] == 0)
            possibilities.add(new Position3D(d - 1,r, c)) ;

        int randomIndex = (int) (Math.random() * possibilities.size());
        Position3D p = possibilities.get(randomIndex);
        matrix[p.getDepthIndex()][p.getRowIndex()][p.getColumnIndex()] = 0 ;
    }

    private void fillWithOnes(int[][][] matrix){
        for(int k = 0 ; k < matrix.length ; k ++) {
            for (int i = 0; i < matrix[k].length; i++) {
                for (int j = 0; j < matrix[k][i].length; j++) {
                    matrix[k][i][j] = 1;
                }
            }
        }
    }
}
