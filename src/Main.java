import algorithms.mazeGenerators.*;
import algorithms.search.*;

public class Main {
    public static void main(String[] args) {
        for(int i = 0 ; i < 1 ; i ++) {
            Maze maze = new MyMazeGenerator().generate(30, 30);
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

//            ISearchingAlgorithm BFS2 = new BreadthFirstSearch();
//            Solution solution3 = BFS2.solve(problem);
//            System.out.println(BFS2.getNumberOfNodesEvaluated());
//            System.out.println(solution3);

        }
//        testPrimAlgorithm(new MyMazeGenerator() , 100 , 100);
    }

    private static void testPrimAlgorithm(MyMazeGenerator mazeGenerator , int rows , int cols) {
        Maze maze = mazeGenerator.generate(rows, cols);
        int r = maze.getStartPosition().getRowIndex() ;
        int c = maze.getStartPosition().getColumnIndex() ;
        for(int i = 0 ; i < rows ; i ++){
            for(int j = 0 ; j < cols ; j ++){
                if(r + 2*i < rows && c + j*2 < cols) {
                    Position position = new Position(r + i * 2, c + j * 2);
                    maze.setGoalPosition(position);
                    ISearchable problem = new SearchableMaze(maze);
                    ISearchingAlgorithm BFS = new BreadthFirstSearch();
                    ISearchingAlgorithm bestFirstSearch = new BestFirstSearch();
                    ISearchingAlgorithm DFS = new DepthFirstSearch();
                    Solution solution1 = BFS.solve(problem);
                    Solution solution2 = bestFirstSearch.solve(problem);
                    Solution solution3 = DFS.solve(problem);
                    if(solution1 == null || solution2 == null || solution3 == null) {
                        System.out.println("problem");
                    }else{
                        System.out.println("good");
                    }
                }
            }
        }
    }
}