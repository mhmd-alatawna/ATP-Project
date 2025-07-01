package org.example.atpprojectpartc.View;

import java.io.*;
import java.util.Properties;

public class MainConfigurations {
    public final String filePath = "config.properties";
    private static MainConfigurations instance;
    private static Properties prop ;

    private MainConfigurations() {
        prop = new Properties() ;
    }
    public static MainConfigurations getInstance() {
        if (instance == null) {
            instance = new MainConfigurations();
        }
        return instance;
    }

    public boolean setThreadPoolSize(int newLimit){
        return writeProperty("threadPoolSize", newLimit);
    }



    public int getThreadPoolSize(){
        return readIntegerProperty("threadPoolSize");
    }
    public String getMazeGeneratingAlgorithm() {
        return readStringProperty("mazeGeneratingAlgorithm");
    }
    public String getMazeSearchingAlgorithm() {
        return readStringProperty("mazeSearchingAlgorithm");
    }

    public static void fillWithDefault(){
        MainConfigurations c = MainConfigurations.getInstance();
        if(c.getIconSize() == -1 && !c.setIconSize(13))
            System.out.println("Error: setIconSize failed");
        if(c.getWindowWidth() == -1 && !c.setWindowWidth(480))
            System.out.println("Error: setWindowWidth failed");
        if(c.getWindowHeight() == -1 && !c.setWindowHeight(480))
            System.out.println("Error: setWindowHeight failed");
        if(c.getPopWindowWidth() == -1 && !c.setPopWindowWidth(200))
            System.out.println("Error: setPopWindowWidth failed");
        if(c.getPopWindowHeight() == -1 && !c.setPopWindowHeight(200))
            System.out.println("Error: setPopWindowHeight failed");
        if(c.getMazeGeneratingAlgorithm().isEmpty() && !c.setMazeGeneratingAlgorithm("MyMazeGenerator"))
            System.out.println("Error: setMazeSearchingAlgorithm failed");
        if(c.getMazeSearchingAlgorithm().isEmpty() && !c.setMazeSearchingAlgorithm("BestFirstSearch"))
            System.out.println("Error: setMazeGeneratingAlgorithm failed");
        if(c.getThreadPoolSize() == -1 && !c.setThreadPoolSize(10))
            System.out.println("Error: setThreadPoolSize failed");
        if(c.getWallDrawer().isEmpty() && !c.setWallDrawer("SimpleWallDrawer"))
            System.out.println("Error: setSimpleWallDrawer failed");
        if(c.getPlayerDrawer().isEmpty() && !c.setPlayerDrawer("SimplePlayerDrawer"))
            System.out.println("Error: setSimplePlayerDrawer failed");
        if(c.getPathDrawer().isEmpty() && !c.setPathDrawer("ArrowPathDrawer"))
            System.out.println("Error: setArrowPathDrawer failed");
        if(c.getGeneratingServerPort() == -1 && !c.setGeneratingServerPort(5400))
            System.out.println("Error: setGeneratingServerPort failed");
        if(c.getSolvingServerPort() == -1 && !c.setSolvingServerPort(5401))
            System.out.println("Error: setSolvingServerPort failed");
        if(c.getServerInterval() == -1 && !c.setServerInterval(1000))
            System.out.println("Error: setServerInterval failed");
    }

    public int getIconSize() {
        return readIntegerProperty("iconSize");
    }

    public boolean setIconSize(int iconSize) {
        return writeProperty("iconSize", iconSize);
    }

    public int getWindowWidth() {
        return readIntegerProperty("windowWidth");
    }

    public boolean setWindowWidth(int windowWidth) {
        return writeProperty("windowWidth", windowWidth);
    }

    public int getWindowHeight() {
        return readIntegerProperty("windowHeight");
    }

    public boolean setWindowHeight(int windowHeight) {
        return writeProperty("windowHeight", windowHeight);
    }

    public int getPopWindowWidth() {
        return readIntegerProperty("popWindowWidth");
    }

    public boolean setPopWindowWidth(int popWindowWidth) {
        return writeProperty("popWindowWidth", popWindowWidth);
    }

    public int getPopWindowHeight() {
        return readIntegerProperty("popWindowHeight");
    }

    public boolean setPopWindowHeight(int popWindowHeight) {
        return writeProperty("popWindowHeight", popWindowHeight);
    }


    public boolean setMazeGeneratingAlgorithm(String generatingAlgorithm) {
        return writeProperty("mazeGeneratingAlgorithm", generatingAlgorithm);
    }


    public boolean setMazeSearchingAlgorithm(String searchingAlgorithm) {
        return writeProperty("mazeSearchingAlgorithm", searchingAlgorithm);
    }

    public String getWallDrawer() {
        return readStringProperty("wallDrawer");
    }

    public boolean setWallDrawer(String wallDrawer) {
        return writeProperty("wallDrawer", wallDrawer);
    }

    public String getPlayerDrawer() {
        return readStringProperty("playerDrawer");
    }

    public boolean setPlayerDrawer(String playerDrawer) {
        return writeProperty("playerDrawer", playerDrawer);
    }

    public String getPathDrawer() {
        return readStringProperty("pathDrawer");
    }

    public boolean setPathDrawer(String pathDrawer) {
        return writeProperty("pathDrawer", pathDrawer);
    }

    public int getGeneratingServerPort() {
        return readIntegerProperty("generatingServerPort");
    }

    public boolean setGeneratingServerPort(int generatingServerPort) {
        return writeProperty("generatingServerPort", generatingServerPort);
    }

    public int getSolvingServerPort() {
        return readIntegerProperty("solvingServerPort");
    }
    public boolean setSolvingServerPort(int solvingServerPort) {
        return writeProperty("solvingServerPort", solvingServerPort);
    }
    public int getServerInterval() {
        return readIntegerProperty("serverInterval");
    }
    public boolean setServerInterval(int serverInterval) {
        return writeProperty("serverInterval", serverInterval);
    }

    private boolean writeProperty(String key, String value){
        try (OutputStream output = new FileOutputStream(filePath)) {

            // set the properties value
            prop.setProperty(key, value);
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            return false;
        }
        return true ;
    }
    private boolean writeProperty(String key, int value){
        try (OutputStream output = new FileOutputStream(filePath)) {
            // set the properties value
            prop.setProperty(key, "" + value);
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            return false ;
        }
        return true ;
    }
    private int readIntegerProperty(String key){
        try (InputStream input = new FileInputStream(filePath)) {
            prop.load(input);
            return Integer.parseInt(prop.getProperty(key));
        } catch (Exception ex) {
            return -1 ;
        }
    }
    private String readStringProperty(String key){
        try (InputStream input = new FileInputStream(filePath)) {
            prop.load(input);
            String str = prop.getProperty(key);
            if(str == null)
                return "" ;
            return str ;
        } catch (Exception ex) {
            return "" ;
        }
    }
}
