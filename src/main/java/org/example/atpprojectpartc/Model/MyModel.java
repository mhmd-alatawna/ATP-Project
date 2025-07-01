package org.example.atpprojectpartc.Model;

import org.example.Client.Client;
import org.example.IO.MyCompressorOutputStream;
import org.example.IO.MyDecompressorInputStream;
import org.example.algorithms.mazeGenerators.*;
import org.example.algorithms.search.*;
import org.example.atpprojectpartc.Model.ClientStrategies.MazeGeneratingStrategy;
import org.example.atpprojectpartc.Model.ClientStrategies.MazeSolvingStrategy;
import org.example.atpprojectpartc.View.MainConfigurations;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Observable;

public class MyModel extends Observable implements IModel {
    Maze maze;
    ArrayList<MazeState> solutionPath;
    Player player ;
    MainConfigurations config = MainConfigurations.getInstance();
    int steps = 0;
    public MyModel() {
    }

    public MazeDTO getMaze() {
        return new MazeDTO(maze.getMatrix() , maze.getMatrix().length, maze.getMatrix()[0].length
                ,maze.getStartPosition() , maze.getGoalPosition());
    }

    public void generateMaze(int rows , int columns) throws ServerException {
        boolean hasMazeChanged = true;
        try {
            MazeGeneratingStrategy strategy = new MazeGeneratingStrategy(rows , columns);
            Client client = new Client(InetAddress.getLocalHost(), config.getGeneratingServerPort(), strategy);
            client.communicateWithServer();
            if(strategy.getMaze() != null)
                this.maze = strategy.getMaze();
            else
                hasMazeChanged = false;
        } catch (UnknownHostException e) {
            throw new ServerException("general connection exception") ;
        }

//        IMazeGenerator generator = new MyMazeGenerator() ;
//        this.maze = generator.generate(rows, columns);
        if(hasMazeChanged) {
            this.player = new Player(maze.getStartPosition());
            this.steps = 0;
            setChanged();
            notifyObservers(Change.mazeGenerated);
        }else
            throw new ServerException("failed to generate maze due to connection failure") ;
    }

    public void solveMaze() throws UnknownHostException {
        MazeSolvingStrategy strategy = new MazeSolvingStrategy(maze);
        Client client = new Client(InetAddress.getLocalHost(), config.getSolvingServerPort(), strategy);
        client.communicateWithServer();
        Solution solution = strategy.getSolution();
//        ISearchingAlgorithm solver = new DepthFirstSearch() ;
//        Solution solution = solver.solve(new SearchableMaze(maze));
        ArrayList<MazeState> arr = new ArrayList<>() ;
        for(AState state : solution.getSolutionPath())
            arr.add((MazeState)state) ;
        this.solutionPath = arr ;
        setChanged();
        notifyObservers(Change.solutionChanged);
    }

    public ArrayList<MazeState> getSolutionPath(){
        return solutionPath;
    }

    public void movePlayer(Direction direction){
        int deltaX = 0 ;
        int deltaY = 0 ;
        switch (direction){
            case UP -> {deltaY = -1 ;deltaX = 0;}
            case DOWN -> {deltaY = 1 ;deltaX = 0;}
            case LEFT -> {deltaY = 0 ;deltaX = -1;}
            case RIGHT -> {deltaY = 0 ;deltaX = 1;}
            case LEFT_UP -> {deltaY = -1 ;deltaX = -1;}
            case LEFT_DOWN -> {deltaY = 1 ;deltaX = -1;}
            case RIGHT_UP -> {deltaY = -1 ;deltaX = 1;}
            case RIGHT_DOWN -> {deltaY = 1 ;deltaX = 1;}
        }
        if(isLegalMove(player , deltaX , deltaY , maze)){
            steps ++ ;
            Position position = new Position(player.position.getRowIndex() + deltaY , player.position.getColumnIndex() + deltaX);
            player.setPosition(position);
            setChanged();
            notifyObservers(Change.playerMoved);
        }
    }

    private boolean isLegalMove(Player player , int deltaX , int deltaY , Maze maze){
        int y = player.getPosition().getRowIndex() + deltaY ;
        int x = player.getPosition().getColumnIndex() + deltaX;
        if(maze.getMatrix().length <= y || y < 0)
            return false;
        if(maze.getMatrix()[y].length <= x || x < 0)
            return false;
        return maze.getMatrix()[y][x] == 0 ;
    }

    public Player getPlayer() {
        return player ;
    }

    public void resetPlayerPosition(){
        player.setPosition(maze.getStartPosition());
        steps = 0 ;
        setChanged();
        notifyObservers(Change.playerMoved);
    }

    private void changeMaze(Maze maze) {
        this.maze = maze;
        this.player = new Player(maze.getStartPosition());
        steps = 0 ;
        setChanged();
        notifyObservers(Change.mazeChanged);
    }

    public void writeToFile(File file){
        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(file));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(File file){
        byte[] savedMazeBytes = new byte[0];
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new FileInputStream(file));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Maze loadedMaze = new Maze(savedMazeBytes);
        changeMaze(loadedMaze);
    }

    public int getSteps() {
        return steps;
    }

}
