package algorithms.mazeGenerators;

public class Maze {
    Position startPosition, goalPosition;
    int[][] matrix ;

    public Maze(int rows, int columns) {
        matrix = new int[rows][columns];
    }

    public Maze(int rows , int columns , Position startPosition, Position goalPosition) {
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
        matrix = new int[rows][columns];
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void print(){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if(startPosition.isEquals(i , j))
                    System.out.print("S ");
                else if(goalPosition.isEquals(i , j))
                    System.out.print("E ");
                else
                    System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
