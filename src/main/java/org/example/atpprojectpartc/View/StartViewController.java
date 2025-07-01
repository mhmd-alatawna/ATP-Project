package org.example.atpprojectpartc.View;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Server.Server;
import org.example.Server.ServerStrategyGenerateMaze;
import org.example.Server.ServerStrategySolveSearchProblem;
import org.example.atpprojectpartc.HelloApplication;
import org.example.atpprojectpartc.Model.ServerManager;
import org.example.atpprojectpartc.ViewModel.ServerManagerViewModel;

import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class StartViewController implements Observer {
    public Button startGame;
    public Button manageServer;
    public VBox topLayout;
    private VBox slidingLayout ;
    private ServerManagerViewModel serverManagerViewModel;
    public static Stage pickImageStage ;
    public static Stage startStage ;

    @FXML
    public void initialize() {
        serverManagerViewModel = new ServerManagerViewModel(ServerManager.getInstance()) ;
        topLayout.getStylesheets().add(getClass().getResource("/org/example/atpprojectpartc/View/MainCSS.css").toExternalForm());
        initSlidingLayout();
    }


    public void startGameAction() throws IOException {
        Stage stage = new Stage();
        HelloApplication.allStages.add(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/atpprojectpartc/View/MyView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MyViewController myViewController = fxmlLoader.getController();
        myViewController.setStartStage(stage) ;
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            myViewController.stageHeightChanged(newValue.doubleValue());
        });
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            myViewController.stageWidthChanged(newValue.doubleValue());
        });
        stage.setTitle("game");
        stage.setScene(scene);
        stage.show();
    }

    public void manageServerAction(){
        Stage newStage = new Stage(); // Create new window
        HelloApplication.allStages.add(newStage);
        VBox container = new VBox();
        container.getStylesheets().add(getClass().getResource(("/org/example/atpprojectpartc/View/MainCSS.css")).toExternalForm());
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        //top HBox (with start buttons)
        HBox topHBox = new HBox();
        topHBox.setAlignment(Pos.CENTER);
        topHBox.setPadding(new Insets(10));
        topHBox.setSpacing(10);

        Button button1 = new Button("start generating server");
        button1.setOnAction(event -> {
            serverManagerViewModel.startGeneratingMazesServer();
        });
        topHBox.getChildren().add(button1);

        Button button2 = new Button("start solving server");
        button2.setOnAction(event -> {
            serverManagerViewModel.startSolvingMazesServer();
        });
        topHBox.getChildren().add(button2);


        //bottom HBox (with stop buttons)
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.setPadding(new Insets(10));
        bottomHBox.setSpacing(10);

        Button button3 = new Button("stop generating server");
        button3.setOnAction(event -> {
            serverManagerViewModel.stopGeneratingMazesServer();
        });
        bottomHBox.getChildren().add(button3);

        Button button4 = new Button("stop solving server");
        button4.setOnAction(event -> {
            serverManagerViewModel.stopSolvingMazesServer();
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

    public void initSlidingLayout() {
        slidingLayout = new VBox(10);
        slidingLayout.setAlignment(Pos.CENTER);
        slidingLayout.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-color: #ccc;");
        slidingLayout.setVisible(false); // not shown at first

        TextField field1 = new TextField();
        field1.setPromptText("Rows");
        TextField field2 = new TextField();
        field2.setPromptText("Columns");
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            try {
                int rows = Integer.parseInt(field1.getText());
                int columns = Integer.parseInt(field2.getText());
                MyViewController.mazeRows = rows;
                MyViewController.mazeColumns = columns;
                startGameAction();
            } catch (Exception e) {
                HelloApplication.showExceptionWindow();
            }
        }) ;

        slidingLayout.getChildren().addAll(field1, field2, submitButton);

        StackPane slideContainer = new StackPane(slidingLayout);
        slideContainer.setMinHeight(0);
        slideContainer.setMaxHeight(0);  // Initially hidden
        slideContainer.setPrefHeight(0);
        slideContainer.setClip(new Rectangle(0, 0, MainConfigurations.getInstance().getWindowWidth(), 0));  // Clipping for smooth animation

        startGame.setOnAction(e -> {
            if (slidingLayout.isVisible()) {
                collapse(slideContainer, slidingLayout);
            } else {
                expand(slideContainer, slidingLayout);
            }
        });
        topLayout.getChildren().add(slideContainer);
    }

    private void expand(StackPane container, Region content) {
        content.setVisible(true);
        content.applyCss();
        content.layout();

        double targetHeight = content.prefHeight(-1);
        Rectangle clip = (Rectangle) container.getClip();

        Timeline expand = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(container.maxHeightProperty(), targetHeight),
                        new KeyValue(container.prefHeightProperty(), targetHeight),
                        new KeyValue(clip.heightProperty(), targetHeight)
                )
        );
        expand.play();
    }

    private void collapse(StackPane container, Region content) {
        Rectangle clip = (Rectangle) container.getClip();

        Timeline collapse = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(container.maxHeightProperty(), 0),
                        new KeyValue(container.prefHeightProperty(), 0),
                        new KeyValue(clip.heightProperty(), 0)
                )
        );
        collapse.setOnFinished(e -> content.setVisible(false));
        collapse.play();
    }
}
