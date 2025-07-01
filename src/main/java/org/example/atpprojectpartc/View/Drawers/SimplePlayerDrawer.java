package org.example.atpprojectpartc.View.Drawers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimplePlayerDrawer implements RectangleDrawer{
    @Override
    public void draw(double x, double y, double width, double height, GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.PURPLE);
        graphicsContext.fillRect(x + width / 4, y + height / 4, width /2 , height / 2);
    }
}
