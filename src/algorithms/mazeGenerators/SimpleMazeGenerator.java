package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator{
    double wallPercentage ;

    public SimpleMazeGenerator(double wallPercentage) {
        this.wallPercentage = wallPercentage;
    }

    public SimpleMazeGenerator() {
        this.wallPercentage = 0.5 ;
    }

    @Override
    public Maze generate(int rows, int cols) {
        if(rows <= 0 || cols <= 0)
            return null;
        Position start = new Position((int)(Math.random() * rows), 0);
        Position end = new Position((int)(Math.random() * rows), cols - 1);
        Maze result = new Maze(rows, cols , start , end);
        for(int i = 0 ; i < rows ; i ++){
            for(int j = 0 ; j < cols ; j ++){
                if((int)(Math.random() * 101) + 1 - (int)(100 * wallPercentage) < 0)
                    result.getMatrix()[i][j] = 1 ;
                else
                    result.getMatrix()[i][j] = 0 ;
            }
        }
        correctMaze(result);
        return result;
    }

    //this function makes sure there is at least one solution
    private void correctMaze(Maze maze) {
        if(maze.getStartPosition().getRowIndex() <= maze.getGoalPosition().getRowIndex()) {
            for (int i = maze.getStartPosition().getRowIndex(); i <= maze.getGoalPosition().getRowIndex(); i++) {
                maze.getMatrix()[i][0] = 0;
            }
        }else{
            for (int i = maze.getGoalPosition().getRowIndex(); i <= maze.getStartPosition().getRowIndex(); i++) {
                maze.getMatrix()[i][0] = 0;
            }
        }
        for(int i = 0 ; i < maze.getMatrix()[0].length ; i++) {
            maze.getMatrix()[maze.getGoalPosition().getRowIndex()][i] = 0 ;
        }
    }
}
