package org.example.Server;

import org.example.algorithms.mazeGenerators.*;
import org.example.algorithms.search.*;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.example.algorithms.search.DepthFirstSearch;
import org.example.algorithms.search.Solution;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private static String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    private static int id = 1 ;
    public ServerStrategySolveSearchProblem() {

    }

    @Override
    public void serverstrategy(InputStream input, OutputStream output) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(output);
        ObjectInputStream inputStream = new ObjectInputStream(input);
        Maze maze ;
        try{
            maze = (Maze) inputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        Solution solution = getSolution(maze) ;
        if(solution == null) {
            ISearchingAlgorithm searchingAlgorithm = new DepthFirstSearch();
            String str = Configurations.getInstance().getMazeSearchingAlgorithm();
            if(str.equals("BreadthFirstSearch"))
                searchingAlgorithm = new BreadthFirstSearch();
            if(str.equals("BestFirstSearch"))
                searchingAlgorithm = new BestFirstSearch();

            solution = searchingAlgorithm.solve(new SearchableMaze(maze)) ;

            Path filePath = Paths.get(tempDirectoryPath, "solution" + id + ".bin");
            File file = filePath.toFile();
            boolean success1 = file.createNewFile();

            Path mazePath = Paths.get(tempDirectoryPath, "maze" + id + ".bin");
            File mazeFile = mazePath.toFile();
            boolean success2 = mazeFile.createNewFile();
            if (success1 && success2) {
                ObjectOutputStream solutionsFileStream = new ObjectOutputStream(new FileOutputStream(file));
                solutionsFileStream.writeObject(solution);
                solutionsFileStream.close();
                ObjectOutputStream mazeFileStream = new ObjectOutputStream(new FileOutputStream(mazeFile));
                mazeFileStream.writeObject(maze);
                mazeFileStream.close();
            }
            id++;
        }
        outputStream.writeObject(solution);
    }

    private Solution getSolution(Maze maze){
        Path dirPath = Paths.get(tempDirectoryPath);
        try{
            DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "maze_*.bin") ;
            for (Path path : stream) {
                System.out.println("Processing file: " + path.getFileName());
                ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path));
                Maze curr = (Maze) inputStream.readObject() ;
                if(compare(curr.toByteArray() , maze.toByteArray())){
                    String fileName = path.getFileName().toString() ;
                    String computed = fileName.substring(4 , fileName.length() - 4) ;
                    Path solutionPath = Paths.get(tempDirectoryPath, "solution" + computed + ".bin");
                    if(Files.exists(solutionPath)){
                        ObjectInputStream inputStream2 = new ObjectInputStream(Files.newInputStream(solutionPath));
                        return (Solution) inputStream2.readObject();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {}

        return null ;
    }

    private boolean compare(byte[] bytes1 , byte[] bytes2) {
        if(bytes1 == null || bytes2 == null)
            return bytes1 == bytes2;
        if(bytes1.length != bytes2.length)
            return false;
        for(int i = 0 ; i < bytes1.length ; i++){
            if(bytes1[i] != bytes2[i])
                return false;
        }
        return true;
    }
}
