package org.example.atpprojectpartc.View.Drawers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimpleWallDrawer implements RectangleDrawer{
    @Override
    public void draw(double x, double y, double width, double height, GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(x, y, width, height);
    }
}
