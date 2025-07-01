package org.example.atpprojectpartc.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Server.Server;
import org.example.Server.ServerStrategyGenerateMaze;
import org.example.Server.ServerStrategySolveSearchProblem;
import org.example.atpprojectpartc.HelloApplication;
import org.example.atpprojectpartc.Model.ServerManager;
import org.example.atpprojectpartc.ViewModel.ServerManagerViewModel;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class StartViewController implements Observer {
    public Button startGame;
    public Button manageServer;
//    public Server generatingServer , solvingServer ;
    private ServerManagerViewModel serverManagerViewModel;
    public static Stage pickImageStage ;

    @FXML
    public void initialize() {
        serverManagerViewModel = new ServerManagerViewModel(ServerManager.getInstance()) ;
    }


    public void startGameAction() throws IOException {
        Stage stage = new Stage();
        HelloApplication.allStages.add(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/atpprojectpartc/View/MyView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 480);
        stage.setTitle("game");
        stage.setScene(scene);
        stage.show();
    }

    public void manageServerAction(){
        Stage newStage = new Stage(); // Create new window
        HelloApplication.allStages.add(newStage);
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        //top HBox (with start buttons)
        HBox topHBox = new HBox();
        topHBox.setAlignment(Pos.CENTER);
        topHBox.setPadding(new Insets(20));

        Button button1 = new Button("start generating server");
        button1.setOnAction(event -> {
            serverManagerViewModel.startGeneratingMazesServer();
//            if(generatingServer != null)
//                generatingServer.stop();
//            ServerStrategyGenerateMaze strategy = new ServerStrategyGenerateMaze();
//            generatingServer = new Server(ApplicationProperties.generatingServerPort , ApplicationProperties.serverInterval , strategy) ;
//            new Thread(() -> {generatingServer.start();}).start() ;
        });
        topHBox.getChildren().add(button1);

        Button button2 = new Button("start solving server");
        button2.setOnAction(event -> {
            serverManagerViewModel.startSolvingMazesServer();
//            if(solvingServer != null)
//                solvingServer.stop();
//            ServerStrategySolveSearchProblem strategy = new ServerStrategySolveSearchProblem();
//            solvingServer = new Server(ApplicationProperties.solvingServerPort , ApplicationProperties.serverInterval , strategy) ;
//            new Thread(() -> {solvingServer.start();}).start() ;
        });
        topHBox.getChildren().add(button2);


        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.setPadding(new Insets(20));

        Button button3 = new Button("stop generating server");
        button3.setOnAction(event -> {
            serverManagerViewModel.stopGeneratingMazesServer();
//            if(generatingServer != null)
//                generatingServer.stop();
        });
        bottomHBox.getChildren().add(button3);

        Button button4 = new Button("stop solving server");
        button4.setOnAction(event -> {
            serverManagerViewModel.stopSolvingMazesServer();
//            if(solvingServer != null)
//                solvingServer.stop();
        });
        bottomHBox.getChildren().add(button4);

        container.getChildren().addAll(topHBox, bottomHBox);
        Scene scene = new Scene(container);
        newStage.setScene(scene);
        newStage.setTitle("pop");
        newStage.show(); // Show the window
    }

    public void pickImageAction() throws IOException {
        Stage stage = new Stage();
        pickImageStage = stage ;
        HelloApplication.allStages.add(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/atpprojectpartc/View/ImagePickingView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("image picker");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
