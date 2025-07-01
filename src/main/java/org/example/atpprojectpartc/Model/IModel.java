package org.example.atpprojectpartc.Model;



import org.example.algorithms.search.MazeState;

import java.io.File;
import java.net.UnknownHostException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Observer;

public interface IModel {
    void addObserver(Observer o);
    MazeDTO getMaze();
    void generateMaze(int rows , int columns) throws ServerException;
    void solveMaze() throws UnknownHostException;
    ArrayList<MazeState> getSolutionPath() ;
    void movePlayer(Direction direction);
    Player getPlayer() ;
    void resetPlayerPosition() ;
    void writeToFile(File file) ;
    void readFromFile(File file) ;
    int getSteps() ;
}
