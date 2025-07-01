package org.example.atpprojectpartc.View.Drawers;

import javafx.scene.canvas.GraphicsContext;
import org.example.algorithms.search.MazeState;

import java.util.ArrayList;

public interface PathDrawer {
    void draw(double width , double height , ArrayList<MazeState> path , GraphicsContext graphicsContext) ;
}
