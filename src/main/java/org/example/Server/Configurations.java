package org.example.Server;

import java.io.*;
import java.util.Properties;

public class Configurations {
    public final String filePath = "config.properties";
    private static Configurations instance;
    private static Properties prop ;
    private Configurations() {
        prop = new Properties() ;
    }
    public static Configurations getInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
        return instance;
    }

    public boolean setThreadPoolLimit(int newLimit){
        try (OutputStream output = new FileOutputStream(filePath)) {

            // set the properties value
            prop.setProperty("threadPoolSize", "" + newLimit);
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            return false ;
        }
        return true ;
    }

    public boolean setMazeGeneratingAlgorithm(String newAlgorithm){
        try (OutputStream output = new FileOutputStream(filePath)) {

            // set the properties value
            prop.setProperty("mazeGeneratingAlgorithm", newAlgorithm);
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            return false ;
        }
        return true ;
    }

    public boolean setMazeSearchingAlgorithm(String newAlgorithm){
        try (OutputStream output = new FileOutputStream(filePath)) {

            // set the properties value
            prop.setProperty("mazeSearchingAlgorithm", newAlgorithm);
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            return false ;
        }
        return true ;
    }

    public int getThreadPoolLimit(){
        try (InputStream input = new FileInputStream(filePath)) {
            prop.load(input);
            return Integer.parseInt(prop.getProperty("threadPoolSize"));
        } catch (Exception ex) {
            return -1 ;
        }
    }

    public String getMazeGeneratingAlgorithm(){
        try (InputStream input = new FileInputStream(filePath)) {
            prop.load(input);
            return prop.getProperty("mazeGeneratingAlgorithm");
        } catch (Exception ex) {
            return "" ;
        }
    }

    public String getMazeSearchingAlgorithm(){
        try (InputStream input = new FileInputStream(filePath)) {
            prop.load(input);
            return prop.getProperty("mazeSearchingAlgorithm");
        } catch (Exception ex) {
            return "" ;
        }
    }

    public static void fillWithDefault(){
        Configurations c = Configurations.getInstance();
        if(!c.setThreadPoolLimit(10))
            System.out.println("Error: setThreadPoolLimit failed");
        if(!c.setMazeSearchingAlgorithm("BestFirstSearch"))
            System.out.println("Error: setMazeSearchingAlgorithm failed");
        if(!c.setMazeGeneratingAlgorithm("MyMazeGenerator"))
            System.out.println("Error: setMazeGeneratingAlgorithm failed");
    }
}
