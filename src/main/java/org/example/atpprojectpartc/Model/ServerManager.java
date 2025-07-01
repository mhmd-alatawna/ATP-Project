package org.example.atpprojectpartc.Model;

import org.example.Server.IServerStrategy;
import org.example.Server.Server;
import org.example.Server.ServerStrategyGenerateMaze;
import org.example.Server.ServerStrategySolveSearchProblem;
import org.example.atpprojectpartc.View.MainConfigurations;

import java.util.Observable;

public class ServerManager extends Observable implements IServerManagerModel{
    private static Server generatingMazesServer;
    private static Server solvingMazesServer;
    private boolean isGeneratingStarted = false ;
    private boolean isSolvingStarted = false ;
    private MainConfigurations config = MainConfigurations.getInstance() ;
    private static ServerManager instance = null ;

    private ServerManager() {
        IServerStrategy generatingStrategy = new ServerStrategyGenerateMaze();
        this.generatingMazesServer = new Server(config.getGeneratingServerPort() , config.getServerInterval() , generatingStrategy) ;
        IServerStrategy solvingStrategy = new ServerStrategySolveSearchProblem();
        this.solvingMazesServer = new Server(config.getSolvingServerPort() , config.getServerInterval() , solvingStrategy) ;
    }

    public static ServerManager getInstance(){
        if(instance == null){
            instance = new ServerManager();
        }
        return instance;
    }

    public void startGeneratingMazesServer() {
        if(!isGeneratingStarted) {
            IServerStrategy generatingStrategy = new ServerStrategyGenerateMaze();
            this.generatingMazesServer = new Server(config.getGeneratingServerPort() , config.getServerInterval() , generatingStrategy) ;
            new Thread(() -> {generatingMazesServer.start();}).start();
            setChanged();
            notifyObservers(Change.generatingServerStarted);
        }
        isGeneratingStarted = true ;
    }

    public void startSolvingMazesServer() {
        if(!isSolvingStarted) {
            IServerStrategy solvingStrategy = new ServerStrategySolveSearchProblem();
            this.solvingMazesServer = new Server(config.getSolvingServerPort() , config.getServerInterval() , solvingStrategy) ;
            new Thread(() -> {solvingMazesServer.start();}).start();
            setChanged();
            notifyObservers(Change.solvingServerStarted);
        }
        isSolvingStarted = true ;
    }

    public void stopGeneratingMazesServer() {
        if(isGeneratingStarted) {
            generatingMazesServer.stop();
            setChanged();
            notifyObservers(Change.generatingServerStopped);
        }
        isGeneratingStarted = false ;
    }
    public void stopSolvingMazesServer() {
        if(isSolvingStarted) {
            solvingMazesServer.stop();
            setChanged();
            notifyObservers(Change.solvingServerStopped);
        }
        isSolvingStarted = false ;
    }

    public static void shutDownServerManager() {
        if(generatingMazesServer != null)
            generatingMazesServer.stop();
        if(solvingMazesServer != null)
            solvingMazesServer.stop();
    }
}
