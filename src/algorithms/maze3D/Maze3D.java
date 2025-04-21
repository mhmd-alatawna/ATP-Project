package algorithms.maze3D;

public class Maze3D {
    Position3D startPosition;
    Position3D goalPosition;
    int[][][] matrix ;

    public Maze3D(int depth , int rows , int columns , Position3D startPosition, Position3D goalPosition) {
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
        matrix = new int[depth][rows][columns];
    }

    public Position3D getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position3D startPosition) {
        this.startPosition = startPosition;
    }

    public Position3D getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(Position3D goalPosition) {
        this.goalPosition = goalPosition;
    }

    public int[][][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][][] matrix) {
        this.matrix = matrix;
    }
    public void print(){
        for(int k = 0 ; k < matrix.length ; k++) {
            if(k != 0)
                System.out.println("---------------------------------------");
            for (int i = 0; i < matrix[k].length; i++) {
                for (int j = 0; j < matrix[k][i].length; j++) {
                    if (startPosition.isEquals(k , i, j))
                        System.out.print("S ");
                    else if (goalPosition.isEquals(k , i, j))
                        System.out.print("E ");
                    else
                        System.out.print(matrix[k][i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}
