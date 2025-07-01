package org.example.atpprojectpartc.View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.algorithms.mazeGenerators.Position;
import org.example.algorithms.search.MazeState;
import org.example.atpprojectpartc.Model.MazeDTO;
import org.example.atpprojectpartc.Model.Player;
import org.example.atpprojectpartc.View.Drawers.PathDrawer;
import org.example.atpprojectpartc.View.Drawers.RectangleDrawer;

import java.util.ArrayList;

public class MazeBoardGame extends VBox {
    public static int buttonsHeight = 50 ;
    private MazeDisplayer displayer;
    private MazeDTO maze;
    private Player player;
    private ArrayList<MazeState> solutionPath ;
    private Button solveButton ;
    private Button resetButton ;
    private CustomListener solvedListener ;

    public MazeBoardGame(int width , int height , RectangleDrawer playerDrawer , RectangleDrawer wallDrawer , PathDrawer pathDrawer) {
        super();
        displayer = new MazeDisplayer(width , height - buttonsHeight, playerDrawer , wallDrawer , pathDrawer);
        maze = null ;

        this.setAlignment(Pos.CENTER);
        this.getChildren().add(displayer);
        HBox buttonsLayout = new HBox();
        buttonsLayout.setAlignment(Pos.CENTER);

        solveButton = new Button("solve") ;
        solveButton.setMaxWidth((double) width / 2);
        HBox.setHgrow(solveButton , Priority.ALWAYS);
        buttonsLayout.getChildren().add(solveButton) ;

        resetButton = new Button("reset") ;
        resetButton.setMaxWidth((double) width / 2);
        HBox.setHgrow(resetButton , Priority.ALWAYS);
        buttonsLayout.getChildren().add(resetButton) ;

        this.getChildren().add(buttonsLayout) ;
    }

    public void setMazeAndPlayer(MazeDTO maze , Player player) {
        this.maze = maze ;
        this.player = player ;
        this.solutionPath = null ;
        displayer.drawMaze(maze , player);
    }

    public void updateSolution(ArrayList<MazeState> solutionPath) {
        this.solutionPath = solutionPath ;
        if(solutionPath != null)
            displayer.drawMazeWithPath(maze , player , solutionPath) ;
        else
            displayer.drawMaze(maze , player) ;
    }

    public void addButtonsListeners(EventHandler<ActionEvent> solveButtonHandler , EventHandler<ActionEvent> resetButtonHandler) {
        solveButton.setOnAction(solveButtonHandler);
        resetButton.setOnAction(resetButtonHandler);
    }

    public void addKeyPressedListener(EventHandler<KeyEvent> handler){
        displayer.setFocusTraversable(true); // allow it to receive key events
        displayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                displayer.requestFocus();
            }
        });
        displayer.setOnKeyPressed(handler);
    }

    public void addMouseDraggedListener(EventHandler<MouseEvent> handler){
        displayer.setOnMouseDragged(handler);
    }

    public void updatePlayer(Player player){
        this.player = player ;
        if(solutionPath == null)
            displayer.drawMaze(maze , player) ;
        else
            displayer.drawMazeWithPath(maze , player , solutionPath) ;

        if(checkMazeSolved()) {
            if(solvedListener != null)
                solvedListener.update();
        }
    }

    public double[] getPlayerCoordinates(){
        double x = (player.getPosition().getColumnIndex() + 0.5) * MazeDisplayer.rectangleWidth ;
        double y = (player.getPosition().getRowIndex() + 0.5) * MazeDisplayer.rectangleHeight ;
        return new double[]{x , y} ;
    }

    private boolean checkMazeSolved(){
        return maze.getGoalPosition().isEquals(player.getPosition().getRowIndex(), player.getPosition().getColumnIndex()) ;
    }

    public Bounds getDisplayerBounds() {
        return displayer.getBoundsInLocal() ;
    }

    public void addSolvedListener(CustomListener solvedListener){
        this.solvedListener = solvedListener ;
    }

    public void resizeWidth(double width){
        displayer.setWidth(width);
    }

    public void resizeHeight(double height){
        displayer.setHeight(height - buttonsHeight);
    }
}
