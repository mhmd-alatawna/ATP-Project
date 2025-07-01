package org.example.atpprojectpartc.View;

import org.example.algorithms.search.MazeState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.atpprojectpartc.Model.MazeDTO;
import org.example.atpprojectpartc.Model.Player;
import org.example.atpprojectpartc.View.Drawers.*;
import org.example.atpprojectpartc.ViewModel.GeneralHelper;

import java.util.ArrayList;

public class MazeDisplayer extends Canvas {
    public static int rectangleWidth = 30;
    public static int rectangleHeight = 30;
    private RectangleDrawer playerDrawer ;
    private RectangleDrawer wallDrawer ;
    private PathDrawer pathDrawer ;

    public MazeDisplayer() {
        super();
        defaultInitializer();
    }
    public MazeDisplayer(int width, int height) {
        super(width, height);
        defaultInitializer();
    }

    public MazeDisplayer(int width , int height , RectangleDrawer playerDrawer , RectangleDrawer wallDrawer , PathDrawer pathDrawer) {
        super(width, height);
        defaultInitializer();
        if(playerDrawer != null)
            this.playerDrawer = playerDrawer;
        if(wallDrawer != null)
            this.wallDrawer = wallDrawer;
        if(pathDrawer != null)
            this.pathDrawer = pathDrawer;
    }

    public void drawMaze(MazeDTO mazeDTO , Player player) {
        int[][] matrix = mazeDTO.getMatrix();
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        gc.setFill(Color.BLACK);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                double x = j * rectangleWidth;
                double y = i * rectangleHeight;
                if(matrix[i][j] == 1)
                    wallDrawer.draw(x , y , rectangleWidth , rectangleHeight , gc);
            }
        }

        drawStartPosition(mazeDTO);
        drawGoalPosition(mazeDTO);
        drawPlayer(player);

        gc.setFill(Color.BLACK);
        gc.strokeLine(0, 0, 0, getHeight());
        gc.strokeLine(0, getHeight(), getWidth(), getHeight());
        gc.strokeLine(getWidth(), getHeight(), getWidth(), 0);
        gc.strokeLine(getWidth(), 0, 0, 0);
    }
    private void drawPlayer(Player player) {
        int px = player.getPosition().getColumnIndex() ;
        int py = player.getPosition().getRowIndex() ;
        double x = px * rectangleWidth;
        double y = py * rectangleHeight;
        playerDrawer.draw(x , y , rectangleWidth , rectangleHeight , getGraphicsContext2D());
    }
    private void drawStartPosition(MazeDTO mazeDTO) {
        double x = mazeDTO.getStartPosition().getColumnIndex() * rectangleWidth;
        double y = mazeDTO.getStartPosition().getRowIndex() * rectangleHeight;
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillOval(x + rectangleWidth * 0.1, y + rectangleHeight * 0.1, rectangleWidth * 0.8, rectangleHeight * 0.8);
        gc.setFill(Color.WHITE);
        gc.fillOval(x + rectangleWidth * 0.2, y + rectangleHeight * 0.2, rectangleWidth * 0.6, rectangleHeight * 0.6);
        gc.setFill(Color.BLACK);
    }
    private void drawGoalPosition(MazeDTO mazeDTO) {
        double x = mazeDTO.getGoalPosition().getColumnIndex() * rectangleWidth;
        double y = mazeDTO.getGoalPosition().getRowIndex() * rectangleHeight;
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillOval(x + rectangleWidth * 0.1, y + rectangleHeight * 0.1, rectangleWidth * 0.8, rectangleHeight * 0.8);
    }

    public void drawMazeWithPath(MazeDTO mazeDTO , Player player , ArrayList<MazeState> path) {
        drawMaze(mazeDTO , player);
        pathDrawer.draw(rectangleWidth , rectangleHeight , path , getGraphicsContext2D());
        drawStartPosition(mazeDTO);
        drawGoalPosition(mazeDTO);
        drawPlayer(player);
    }

    private void defaultInitializer(){
        this.wallDrawer = new SimpleWallDrawer();
        this.pathDrawer = new SimplePathDrawer();
        this.playerDrawer = new SimplePlayerDrawer();
    }
}
