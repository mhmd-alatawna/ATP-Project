package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{
    public EmptyMazeGenerator() {}

    @Override
    public Maze generate(int rows, int cols) {
        if(rows <= 0 || cols <= 0)
            return null;
        Position start = new Position((int)(Math.random() * rows), 0);
        Position end = new Position((int)(Math.random() * rows), cols - 1);
        return new Maze(rows , cols , start , end);
    }
}
