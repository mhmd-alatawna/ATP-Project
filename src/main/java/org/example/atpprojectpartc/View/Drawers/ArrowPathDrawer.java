package org.example.atpprojectpartc.View.Drawers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.algorithms.search.MazeState;

import java.util.ArrayList;

public class ArrowPathDrawer implements PathDrawer {
    private final double PI = 3.14159 ;
    @Override
    public void draw(double width, double height, ArrayList<MazeState> path, GraphicsContext graphicsContext) {
        for(int i = 0 ; i < path.size() - 1; i++){
            double angle = getAngle(path.get(i) , path.get(i + 1)) ;
            double y = path.get(i).getRow() * height;
            double x = path.get(i).getColumn() * width;
            drawArrow(x , y , width , height , angle , graphicsContext);
        }
    }

    private void drawArrow(double startX, double startY , double width , double height, double angle , GraphicsContext gc) {
        gc.setFill(Color.BLACK);

        double length = Math.min(width , height);
        double middleX = startX + width / 2;
        double middleY = startY + height / 2;
        if(angle % 90 != 0)
            length = (int) (length * Math.sqrt(2)) ;

        length = length * 3/4 ;

        double adjAngle = angle / 180.0 * PI ;
        double relativeEndX = middleX + Math.cos(adjAngle) * length / 2;
        double relativeEndY = middleY + Math.sin(adjAngle) * length / 2;
        double relativeStartX = middleX + Math.cos(adjAngle + PI) * length / 2 ;
        double relativeStartY = middleY + Math.sin(adjAngle + PI) * length / 2 ;


        double headLength = height / 4;
        double relativeTopX = relativeEndX + Math.cos(adjAngle + 3.0 / 4.0 * PI) * headLength;
        double relativeTopY = relativeEndY + Math.sin(adjAngle + 3.0 / 4.0 * PI) * headLength;
        double relativeBottomX = relativeEndX + Math.cos(adjAngle + 5.0 / 4.0 * PI) * headLength;
        double relativeBottomY = relativeEndY + Math.sin(adjAngle + 5.0 / 4.0 * PI) * headLength;

        gc.setFill(Color.BLACK);
        gc.strokeLine(relativeStartX, relativeStartY, relativeEndX, relativeEndY);
        gc.strokeLine(relativeTopX , relativeTopY, relativeEndX, relativeEndY);
        gc.strokeLine(relativeBottomX , relativeBottomY, relativeEndX, relativeEndY);
    }

    private double getAngle(MazeState curr , MazeState next ) {
        int deltaY = next.getRow() - curr.getRow() ;
        int deltaX = next.getColumn() - curr.getColumn() ;
        if(deltaX > 0 && deltaY == 0)
            return 0 ; //right
        else if(deltaX > 0 && deltaY > 0)
            return 45 ; //right-bottom
        else if(deltaX > 0 && deltaY < 0)
            return -45 ; //right-top
        else if(deltaX < 0 && deltaY == 0)
            return 180 ; //left
        else if(deltaX < 0 && deltaY > 0)
            return 180 - 45 ; //left-bottom
        else if(deltaX < 0 && deltaY < 0)
            return 180 + 45 ; //left-top
        else if(deltaX == 0 && deltaY > 0)
            return 90 ; //bottom
        else if(deltaX == 0 && deltaY < 0)
            return 270 ;
        return -1 ;
    }
}
