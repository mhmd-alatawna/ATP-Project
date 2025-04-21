package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    //check if the algorithm fails to find a solution when there is a solution
    @Test
    void testFindingSolutions() {
        for(int i = 0 ; i < 1000; i ++) {
            Maze maze = new MyMazeGenerator().generate(10, 10);
            ISearchable problem = new SearchableMaze(maze);
            ISearchingAlgorithm best = new BestFirstSearch();
            Solution s1 = best.solve(problem);
            assertTrue(s1 != null);
        }
    }


    //best should always return the lowest number of evaluated nodes
    @Test
    void testMinimalNumberOfNodesEvaluated(){
        Maze maze = new MyMazeGenerator().generate(10, 10);
        ISearchable problem = new SearchableMaze(maze);
        ISearchingAlgorithm best = new BestFirstSearch();
        ISearchingAlgorithm BFS = new BreadthFirstSearch();
        ISearchingAlgorithm DFS = new DepthFirstSearch();
        Solution s1 = best.solve(problem);
        Solution s2 = BFS.solve(problem);
        Solution s3 = DFS.solve(problem);

        assertTrue(best.getNumberOfNodesEvaluated() <= BFS.getNumberOfNodesEvaluated());
        assertTrue(best.getNumberOfNodesEvaluated() <= DFS.getNumberOfNodesEvaluated());
    }

    //check if best provides a minimal path (assuming BFS does)
    @Test
    void testMinimalPath(){
        Maze maze = new MyMazeGenerator().generate(10, 10);
        ISearchable problem = new SearchableMaze(maze);
        ISearchingAlgorithm best = new BestFirstSearch();
        ISearchingAlgorithm BFS = new BreadthFirstSearch();
        Solution s1 = best.solve(problem);
        Solution s2 = BFS.solve(problem);

        assertTrue(s1.getSolutionPath().size() == s2.getSolutionPath().size());
    }
}