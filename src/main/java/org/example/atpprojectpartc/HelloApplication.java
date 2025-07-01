package org.example.atpprojectpartc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.atpprojectpartc.View.MainConfigurations;

import java.util.ArrayList;

public class HelloApplication extends Application {
//    private static Logger logger = LogManager.getLogger(HelloApplication.class);
    public static ArrayList<Stage> allStages = new ArrayList<>();
    public static ArrayList<Alert> allAlerts = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        MainConfigurations.fillWithDefault();
        try {
            allStages.add(stage);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/atpprojectpartc/View/StartView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 480, 480);
            stage.setTitle("maze game");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            showExceptionWindow();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showExceptionWindow() {
        Stage newStage = new Stage(); // Create new window
        allStages.add(newStage);
        VBox layout = new VBox(new Label("An exception have been thrown please try again"));
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, MainConfigurations.getInstance().getPopWindowWidth(), MainConfigurations.getInstance().getPopWindowHeight());
        newStage.setScene(scene);
        newStage.setTitle("New Window");
        newStage.show(); // Show the window
    }
}