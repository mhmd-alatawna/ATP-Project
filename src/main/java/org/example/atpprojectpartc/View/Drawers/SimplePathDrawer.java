package org.example.atpprojectpartc.View.Drawers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.algorithms.search.MazeState;

import java.util.ArrayList;

public class SimplePathDrawer implements PathDrawer{
    @Override
    public void draw(double width, double height, ArrayList<MazeState> path, GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.BLUE);
        for(MazeState state : path) {
            double x = (state.getColumn()) * width;
            double y = (state.getRow()) * height;
            graphicsContext.fillRect(x , y , width , height);
        }
    }
}
