package algorithms.maze3D;

public abstract class AMaze3DGenerator implements IMazeGenerator3D{
    @Override
    public long measureAlgorithmTimeMillis(int depth , int rows , int cols) {
        long before = System.currentTimeMillis() ;
        this.generate(depth ,rows, cols);
        long after = System.currentTimeMillis() ;
        return after - before ;
    }
}
