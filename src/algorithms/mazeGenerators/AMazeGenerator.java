package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    public long measureAlgorithmTimeMillis(int rows , int cols) {
        long before = System.currentTimeMillis() ;
        this.generate(rows, cols);
        long after = System.currentTimeMillis() ;
        return after - before ;
    }
}
