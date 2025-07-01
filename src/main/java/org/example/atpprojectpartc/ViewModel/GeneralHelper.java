package org.example.atpprojectpartc.ViewModel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.algorithms.mazeGenerators.EmptyMazeGenerator;
import org.example.algorithms.mazeGenerators.IMazeGenerator;
import org.example.algorithms.mazeGenerators.MyMazeGenerator;
import org.example.algorithms.mazeGenerators.SimpleMazeGenerator;
import org.example.algorithms.search.*;
import org.example.atpprojectpartc.Model.Direction;
import org.example.atpprojectpartc.View.Drawers.*;
import org.example.atpprojectpartc.View.ImagePickingController;

import java.util.ArrayList;

public class GeneralHelper {

    public static String[] getAllMazeGeneratingAlgorithms() {
        return new String[]{"MyMazeGenerator" , "SimpleMazeGenerator" , "EmptyMazeGenerator"} ;
    }

    public static String[] getAllMazeSolvingAlgorithms() {
        return new String[]{"BestFirstSearch" , "DepthFirstSearch" , "BreadthFirstSearch"} ;
    }

    public static String[] getAllPathDrawers(){
        return new String[]{"SimplePathDrawer" , "ArrowPathDrawer"} ;
    }

    public static String[] getAllPlayerDrawers(){
        return new String[]{"SimplePlayerDrawer" , "ImageDrawer"} ;
    }

    public static String[] getAllWallDrawers(){
        return new String[]{"SimpleWallDrawer" , "ImageDrawer"} ;
    }

    public static IMazeGenerator getMazeGenerator(String algorithm) {
        switch (algorithm){
            case "MyMazeGenerator" -> {return new MyMazeGenerator();}
            case "SimpleMazeGenerator" -> {return new SimpleMazeGenerator();}
            case "EmptyMazeGenerator" -> {return new EmptyMazeGenerator();}
            default -> throw new IllegalArgumentException("Unknown generator: " + algorithm);
        }
    }

    public static ISearchingAlgorithm getSearchingAlgorithm(String algorithm) {
        switch (algorithm){
            case "BestFirstSearch" -> {return new BestFirstSearch();}
            case "DepthFirstSearch" -> {return new DepthFirstSearch();}
            case "BreadthFirstSearch" -> {return new BreadthFirstSearch();}
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }

    public static PathDrawer getPathDrawer(String pathDrawer) {
        switch (pathDrawer){
            case "SimplePathDrawer" -> {return new SimplePathDrawer();}
            case "ArrowPathDrawer" -> {return new ArrowPathDrawer();}
            default -> throw new IllegalArgumentException("Unknown path drawer: " + pathDrawer);
        }
    }

    public static RectangleDrawer getPlayerDrawer(String playerDrawer) {
        switch (playerDrawer){
            case "SimplePlayerDrawer" -> {return new SimplePlayerDrawer();}
            case "ImageDrawer" -> {return new ImageDrawer(ImagePickingController.playerImage);}
            default -> throw new IllegalArgumentException("Unknown player drawer: " + playerDrawer);
        }
    }

    public static RectangleDrawer getWallDrawer(String wallDrawer){
        switch (wallDrawer){
            case "SimpleWallDrawer" -> {return new SimpleWallDrawer();}
            case "ImageDrawer" -> {return new ImageDrawer(ImagePickingController.wallImage);}
            default -> throw new IllegalArgumentException("Unknown wall drawer: " + wallDrawer);
        }
    }
}
