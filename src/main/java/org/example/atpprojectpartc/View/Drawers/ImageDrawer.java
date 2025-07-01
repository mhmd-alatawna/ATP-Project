package org.example.atpprojectpartc.View.Drawers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.example.atpprojectpartc.View.ImagePickingController;

import java.io.File;

public class ImageDrawer implements RectangleDrawer{
    private Image image;
    public ImageDrawer(File imageFile) {
        image = new Image(imageFile.toURI().toString());
    }
    @Override
    public void draw(double x, double y, double width, double height, GraphicsContext graphicsContext) {
        graphicsContext.drawImage(image, x, y, width, height);
    }
}
