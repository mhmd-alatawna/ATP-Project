package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    /**
     * Tests that the algorithmsuccessfully finds a solution.
     */
    @Test
    void testFindingSolutions() {
        for (int i = 0; i < 1000; i++) {
            Maze maze = new MyMazeGenerator().generate(10, 10);
            ISearchable problem = new SearchableMaze(maze);
            ISearchingAlgorithm best = new BestFirstSearch();
            Solution s1 = best.solve(problem);
            assertTrue(s1 != null);
        }
    }

    /**
     * Verifiess that BestFirstSearch evaluates fewer or equal nodes compared to BFS and DFS.
     */
    @Test
    void testMinimalNumberOfNodesEvaluated() {
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

    /**
     * Compares BestFirstSearch path length to BFS path length
     */
    @Test
    void testMinimalPath() {
        Maze maze = new MyMazeGenerator().generate(10, 10);
        ISearchable problem = new SearchableMaze(maze);
        ISearchingAlgorithm best = new BestFirstSearch();
        ISearchingAlgorithm BFS = new BreadthFirstSearch();
        Solution s1 = best.solve(problem);
        Solution s2 = BFS.solve(problem);

        assertTrue(s1.getSolutionPath().size() == s2.getSolutionPath().size());
    }

    /**
     * make sure that the solution returned is not null for solvable mazes.
     */
    @Test
    void testSolutionIsNotNull() {
        for (int i = 0; i < 100; i++) {
            Maze maze = new MyMazeGenerator().generate(10, 10);
            Position startPosition = maze.getStartPosition();
            Position goalPosition = maze.getGoalPosition();
            ISearchable problem = new SearchableMaze(maze);
            ISearchingAlgorithm best = new BestFirstSearch();
            Solution solution = best.solve(problem);
            assertNotNull(solution, "The solution should not be null");
        }
    }

    /**
     * make sure that the algorithm can solve larger mazes without failure.
     */
    @Test
    void testLargeMaze() {
        Maze maze = new MyMazeGenerator().generate(50, 50);
        Position startPosition = maze.getStartPosition();
        Position goalPosition = maze.getGoalPosition();
        ISearchable problem = new SearchableMaze(maze);
        ISearchingAlgorithm best = new BestFirstSearch();
        Solution solution = best.solve(problem);
        assertNotNull(solution, "The solution should not be null for a larger maze");
    }

    /**
     * Verifies that the algorithm returns null when no valid path exists.
     */
    @Test
    void testNoSolution() {
        Maze maze = new MyMazeGenerator().generate(10, 10);
        for (int i = 0; i < maze.getMatrix().length; i++) {
            for (int j = 0; j < maze.getMatrix()[i].length; j++) {
                if ((i != 1 || j != 0) && (i != 1 || j != 9)) {
                    maze.getMatrix()[i][j] = 1;
                }
            }
        }

        ISearchable problem = new SearchableMaze(maze);
        ISearchingAlgorithm best = new BestFirstSearch();
        Solution solution = best.solve(problem);

        assertNull(solution, "The solution should be null when there's no path");
    }

    /**
     * Ensures that solving a large maze completes within 60 seconds.
     */
    @Test
    void testLargeMazeTimeLimit() {
        long startTime = System.currentTimeMillis();

        int rows = 1000;
        int cols = 1000;
        Maze maze = new MyMazeGenerator().generate(rows, cols);
        Position startPosition = maze.getStartPosition();
        Position goalPosition = maze.getGoalPosition();
        ISearchable problem = new SearchableMaze(maze);

        ISearchingAlgorithm best = new BestFirstSearch();
        best.solve(problem);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        assertTrue(duration < 60000, "Algorithm took too long to solve (should be under 60 seconds)");
    }

    /**
     * Tests that the start and goal positions in the maze are not the same.
     */
    @Test
    void testStartEqualsGoal() {
        Maze maze = new MyMazeGenerator().generate(10, 10);
        Position startPosition = maze.getStartPosition();
        Position goalPosition = maze.getGoalPosition();

        assertNotEquals(startPosition, goalPosition, "The start and goal positions should not be the same");
    }

    /**
     * make sure that a path is found in a maze with no walls.
     */
    @Test
    void testFullyOpenMaze() {
        int rows = 10, cols = 10;
        Maze maze = new Maze(rows, cols);
        int[][] matrix = maze.getMatrix();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = 0;
            }
        }

        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(rows - 1, cols - 1));

        ISearchable problem = new SearchableMaze(maze);
        ISearchingAlgorithm best = new BestFirstSearch();
        Solution solution = best.solve(problem);

        assertNotNull(solution, "Should find a path in a fully open maze");
    }

    /**
     * make sure that no path is found in a maze where all cells are walls except the start and goal.
     */
    @Test
    void testFullyBlockedMaze() {
        int rows = 10, cols = 10;
        Maze maze = new Maze(rows, cols);
        int[][] matrix = maze.getMatrix();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = 1;
            }
        }

        matrix[0][0] = 0;
        matrix[rows - 1][cols - 1] = 0;

        maze.setStartPosition(new Position(0, 0));
        maze.setGoalPosition(new Position(rows - 1, cols - 1));

        ISearchable problem = new SearchableMaze(maze);
        ISearchingAlgorithm best = new BestFirstSearch();
        Solution solution = best.solve(problem);

        assertNull(solution, "Should not find a path");
    }
}
