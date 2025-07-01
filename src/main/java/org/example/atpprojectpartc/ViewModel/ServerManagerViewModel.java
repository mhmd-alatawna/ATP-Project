package org.example.atpprojectpartc.ViewModel;

import org.example.Server.IServerStrategy;
import org.example.Server.Server;
import org.example.Server.ServerStrategyGenerateMaze;
import org.example.Server.ServerStrategySolveSearchProblem;
import org.example.atpprojectpartc.Model.IServerManagerModel;
import org.example.atpprojectpartc.View.MainConfigurations;

import java.util.Observable;
import java.util.Observer;

public class ServerManagerViewModel extends Observable implements Observer {
    private IServerManagerModel serverManagerModel;
    public ServerManagerViewModel(IServerManagerModel serverManagerModel) {
        this.serverManagerModel = serverManagerModel;
        this.serverManagerModel.addObserver(this);
    }
    public void startGeneratingMazesServer() {
        serverManagerModel.startGeneratingMazesServer() ;
    }
    public void startSolvingMazesServer() {
        serverManagerModel.startSolvingMazesServer() ;
    }
    public void stopGeneratingMazesServer() {
        serverManagerModel.stopGeneratingMazesServer() ;
    }
    public void stopSolvingMazesServer() {
        serverManagerModel.stopSolvingMazesServer() ;
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
