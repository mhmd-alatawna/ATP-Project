package org.example.atpprojectpartc.Model.ClientStrategies;

import org.example.Client.IClientStrategy;
import org.example.algorithms.mazeGenerators.Maze;
import org.example.algorithms.mazeGenerators.MyMazeGenerator;
import org.example.algorithms.search.AState;
import org.example.algorithms.search.Solution;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MazeSolvingStrategy implements IClientStrategy {
    Maze maze;
    int rows , columns ;
    Solution solution;
    public MazeSolvingStrategy(Maze maze) {
        this.maze = maze;
        this.rows = maze.getMatrix().length;
        this.columns = maze.getMatrix()[0].length;
    }

    @Override
    public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
            ObjectInputStream fromServer = new ObjectInputStream(inputStream);
            toServer.flush();
            toServer.writeObject(maze); //send maze to server
            toServer.flush();
            solution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Solution getSolution() {
        return solution;
    }
}
