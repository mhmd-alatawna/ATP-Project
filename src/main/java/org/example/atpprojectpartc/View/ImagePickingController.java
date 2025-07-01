package org.example.atpprojectpartc.View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

public class ImagePickingController {
    public static File playerImage ;
    public static File wallImage ;
    public Button pickPlayerImageButton;
    public Button confirmButton;
    public Button pickWallImageButton;

    @FXML
    public void initialize() {
        initButton(playerImage , pickPlayerImageButton);
        initButton(wallImage , pickWallImageButton);
    }

    public void pickPlayerImage() {
        playerImage = pickImageAndShow(pickPlayerImageButton) ;
    }

    public void pickWallImage() {
        wallImage = pickImageAndShow(pickWallImageButton) ;
    }

    private void initButton(File file , Button button) {
        ImageView fileImage = new ImageView(getClass().getResource("/org/example/atpprojectpartc/pickImageBackground.png").toString()) ;
        if(file != null)
            fileImage = new ImageView(file.toURI().toString());
        fileImage.setFitHeight(100);
        fileImage.setFitWidth(100);
        button.setGraphic(fileImage);
    }
    private File pickImageAndShow(Button button) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        File result = fileChooser.showOpenDialog(null);

        ImageView fileImage = new ImageView(result.toURI().toString()) ;
        fileImage.setFitHeight(100);
        fileImage.setFitWidth(100);
        button.setGraphic(fileImage);
        return result;
    }

    public void confirm() {
        StartViewController.pickImageStage.close();
    }
}
