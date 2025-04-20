import algorithms.mazeGenerators.*;
import algorithms.search.*;

public class Main {
    public static void main(String[] args) {
        for(int i = 0 ; i < 100 ; i ++) {
            Maze maze = new SimpleMazeGenerator().generate(10, 10);
            ISearchable problem = new SearchableMaze(maze);
            ISearchingAlgorithm BFS = new BreadthFirstSearch();
            Solution solution1 = BFS.solve(problem);
            maze.print();
            System.out.println(BFS.getNumberOfNodesEvaluated());
            System.out.println(solution1);
            ISearchingAlgorithm DFS = new BestFirstSearch() ;
            Solution solution2 = DFS.solve(problem);
            System.out.println(DFS.getNumberOfNodesEvaluated());
            System.out.println(solution2);

        }
    }
}