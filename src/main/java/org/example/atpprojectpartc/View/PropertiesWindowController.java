package org.example.atpprojectpartc.View;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.atpprojectpartc.HelloApplication;
import org.example.atpprojectpartc.ViewModel.GeneralHelper;

import java.io.IOException;
import java.util.Optional;

public class PropertiesWindowController {

    public TextField iconSize;
    public TextField windowWidth;
    public TextField windowHeight;
    public TextField popWindowWidth;
    public TextField popWindowHeight;
    public TextField threadPoolSize;
    public ChoiceBox<String> generatingAlgorithm;
    public ChoiceBox<String> searchingAlgorithm;
    public ChoiceBox<String> wallDrawer;
    public ChoiceBox<String> playerDrawer;
    public ChoiceBox<String> pathDrawer;
    public TextField generatingServerPort;
    public TextField solvingServerPort;
    public TextField serverInterval;

    private MainConfigurations config = MainConfigurations.getInstance();
    public static Stage showingStage;
    @FXML
    public void initialize() {
        generatingAlgorithm.getItems().addAll(GeneralHelper.getAllMazeGeneratingAlgorithms()) ;
        searchingAlgorithm.getItems().addAll(GeneralHelper.getAllMazeSolvingAlgorithms()) ;
        wallDrawer.getItems().addAll(GeneralHelper.getAllWallDrawers()) ;
        playerDrawer.getItems().addAll(GeneralHelper.getAllPlayerDrawers()) ;
        pathDrawer.getItems().addAll(GeneralHelper.getAllPathDrawers()) ;

        iconSize.setPromptText(String.valueOf(config.getIconSize()));
        windowWidth.setPromptText(String.valueOf(config.getWindowWidth()));
        windowHeight.setPromptText(String.valueOf(config.getWindowHeight()));
        popWindowWidth.setPromptText(String.valueOf(config.getPopWindowWidth()));
        popWindowHeight.setPromptText(String.valueOf(config.getPopWindowHeight()));
        threadPoolSize.setPromptText(String.valueOf(config.getThreadPoolSize()));
        generatingAlgorithm.setValue(config.getMazeGeneratingAlgorithm());
        searchingAlgorithm.setValue(config.getMazeSearchingAlgorithm());
        wallDrawer.setValue(config.getWallDrawer());
        playerDrawer.setValue(config.getPlayerDrawer());
        pathDrawer.setValue(config.getPathDrawer());
        generatingServerPort.setPromptText(String.valueOf(config.getGeneratingServerPort()));
        solvingServerPort.setPromptText(String.valueOf(config.getSolvingServerPort()));
        serverInterval.setPromptText(String.valueOf(config.getServerInterval()));
    }

    public void confirm() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        HelloApplication.allAlerts.add(alert);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if(!iconSize.getText().isEmpty())
                config.setIconSize(Integer.parseInt(iconSize.getText()));
            if(!windowWidth.getText().isEmpty())
                config.setWindowWidth(Integer.parseInt(windowWidth.getText()));
            if(!windowHeight.getText().isEmpty())
                config.setWindowHeight(Integer.parseInt(windowHeight.getText()));
            if(!popWindowWidth.getText().isEmpty())
                config.setPopWindowWidth(Integer.parseInt(popWindowWidth.getText()));
            if(!popWindowHeight.getText().isEmpty())
                config.setPopWindowHeight(Integer.parseInt(popWindowHeight.getText()));
            if(!threadPoolSize.getText().isEmpty())
                config.setThreadPoolSize(Integer.parseInt(threadPoolSize.getText()));
            config.setMazeGeneratingAlgorithm(generatingAlgorithm.getValue());
            config.setMazeSearchingAlgorithm(searchingAlgorithm.getValue());
            config.setWallDrawer(wallDrawer.getValue());
            config.setPlayerDrawer(playerDrawer.getValue());
            config.setPathDrawer(pathDrawer.getValue());
            if(!generatingServerPort.getText().isEmpty())
                config.setGeneratingServerPort(Integer.parseInt(generatingServerPort.getText()));
            if(!solvingServerPort.getText().isEmpty())
                config.setSolvingServerPort(Integer.parseInt(solvingServerPort.getText()));
            if(!serverInterval.getText().isEmpty())
                config.setServerInterval(Integer.parseInt(serverInterval.getText()));

            showingStage.close();
        }
    }
}
