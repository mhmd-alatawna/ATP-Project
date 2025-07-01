package org.example.atpprojectpartc.Model;

import java.util.Observer;

public interface IServerManagerModel {
    void addObserver(Observer o);
    void startGeneratingMazesServer() ;
    void startSolvingMazesServer() ;
    void stopGeneratingMazesServer();
    void stopSolvingMazesServer() ;
}
