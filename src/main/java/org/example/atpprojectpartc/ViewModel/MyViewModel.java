package org.example.atpprojectpartc.ViewModel;

import java.io.File;
import java.net.UnknownHostException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.algorithms.mazeGenerators.Maze;
import org.example.algorithms.search.MazeState;
import org.example.atpprojectpartc.Model.Direction;
import org.example.atpprojectpartc.Model.IModel;
import org.example.atpprojectpartc.Model.MazeDTO;
import org.example.atpprojectpartc.Model.Player;

public class MyViewModel extends Observable implements Observer {
    IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.addObserver(this);
    }

    public MazeDTO getMaze() {
        return model.getMaze();
    }

    public void generateMaze(int rows, int columns) throws ServerException {
        model.generateMaze(rows, columns);
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public void solveMaze() throws UnknownHostException {
        model.solveMaze();
    }

    public ArrayList<MazeState> getSolutionPath() {
        return model.getSolutionPath();
    }

    public void movePlayer(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case KeyCode.DIGIT2 -> {
                model.movePlayer(Direction.LEFT);
            }
            case KeyCode.DIGIT8 -> {
                model.movePlayer(Direction.RIGHT);
            }
            case KeyCode.DIGIT6 -> {
                model.movePlayer(Direction.UP);
            }
            case KeyCode.DIGIT4 -> {
                model.movePlayer(Direction.DOWN);
            }
            case KeyCode.DIGIT3 -> {
                model.movePlayer(Direction.LEFT_UP);
            }
            case KeyCode.DIGIT1 -> {
                model.movePlayer(Direction.RIGHT_UP);
            }
            case KeyCode.DIGIT9 -> {
                model.movePlayer(Direction.LEFT_DOWN);
            }
            case KeyCode.DIGIT7 -> {
                model.movePlayer(Direction.RIGHT_DOWN);
            }

            case KeyCode.UP -> {
                model.movePlayer(Direction.UP);
            }
            case KeyCode.DOWN -> {
                model.movePlayer(Direction.DOWN);
            }
            case KeyCode.LEFT -> {
                model.movePlayer(Direction.LEFT);
            }
            case KeyCode.RIGHT -> {
                model.movePlayer(Direction.RIGHT);
            }
        }
    }

    public void movePlayer(Direction direction) {
        model.movePlayer(direction);
    }

    public Player getPlayer() {
        return model.getPlayer();
    }

    public void resetPlayerPosition() {
        model.resetPlayerPosition();
    }

    public void writeToFile(File file) {
        model.writeToFile(file);
    }

    public void readFromFile(File file) {
        model.readFromFile(file);
    }

    public int getSteps(){
        return model.getSteps();
    }
}