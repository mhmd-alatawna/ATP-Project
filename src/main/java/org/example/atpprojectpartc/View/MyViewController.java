package org.example.atpprojectpartc.View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.algorithms.search.MazeState;
import org.example.atpprojectpartc.HelloApplication;
import org.example.atpprojectpartc.Model.*;
import org.example.atpprojectpartc.View.Drawers.PathDrawer;
import org.example.atpprojectpartc.View.Drawers.RectangleDrawer;
import org.example.atpprojectpartc.ViewModel.GeneralHelper;
import org.example.atpprojectpartc.ViewModel.MyViewModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class MyViewController implements IView , Observer {
    private static final double PI = 3.14159 ;
    public Menu fileMenu;
    public Menu optionsMenu;
    public Menu exitMenu ;
    public Menu helpMenu;
    public Menu aboutMenu;
    public MenuBar menuBar;
    public Menu soundMenu;

    public MenuItem newItem;
    public MenuItem saveItem;
    public MenuItem loadItem;
    public MenuItem propertiesItem;
    public MenuItem exitItem;
    public MenuItem gameRulesItem;
    public MenuItem algorithmsItem;
    public MenuItem contributorsItem;
    public MenuItem unlinkItem;
    public MenuItem pickAudio;
    public MenuItem startPlaying;
    public MenuItem stopPlaying;


    public MazeBoardGame mazeBoardGame;
    public VBox topLayout;

    private MyViewModel viewModel;

    public static int mazeRows = 20 ;
    public static int mazeColumns = 20 ;

    public MainConfigurations config = MainConfigurations.getInstance();
    private File lastLinkedMazeFile;
    private Media audioMedia ;
    private Media winGameMedia ;

    private boolean isFirst = true ;
    private double firstDiff = -1;
    private Stage startStage ;

    @FXML
    protected void initialize() {
        audioMedia = new Media(getClass().getResource("/org/example/atpprojectpartc/gameMusic.mp3").toString());
        winGameMedia = new Media(getClass().getResource("/org/example/atpprojectpartc/winGameMusic.mp3").toString());

        MazeDisplayer.rectangleWidth = config.getWindowWidth() / mazeColumns ;
        MazeDisplayer.rectangleHeight = (config.getWindowHeight() - 80) / mazeRows ;

        PathDrawer pathDrawer = GeneralHelper.getPathDrawer(config.getPathDrawer()) ;
        RectangleDrawer playerDrawer = GeneralHelper.getPlayerDrawer(config.getPlayerDrawer()) ;
        RectangleDrawer wallDrawer = GeneralHelper.getWallDrawer(config.getWallDrawer()) ;
        mazeBoardGame = new MazeBoardGame(config.getWindowWidth() , config.getWindowHeight() + MazeBoardGame.buttonsHeight - 80 , playerDrawer , wallDrawer , pathDrawer) ;

        viewModel = new MyViewModel(new MyModel()) ;
        viewModel.addObserver(this);
        try {
            viewModel.generateMaze(mazeRows, mazeColumns);
        }catch (Exception e){
            HelloApplication.showExceptionWindow();
            return;
        }
        initMazeBoardGame();
        topLayout.getChildren().add(mazeBoardGame) ;

        ImageView fileImage = new ImageView(getClass().getResource("/org/example/atpprojectpartc/fileIcon.png").toString()) ;
        fileImage.setFitHeight(config.getIconSize());
        fileImage.setFitWidth(config.getIconSize());
        fileMenu.setGraphic(fileImage);

        ImageView optionsImage = new ImageView(getClass().getResource("/org/example/atpprojectpartc/optionsIcon.png").toString()) ;
        optionsImage.setFitHeight(config.getIconSize());
        optionsImage.setFitWidth(config.getIconSize());
        optionsMenu.setGraphic(optionsImage);

        ImageView exitImage = new ImageView(getClass().getResource("/org/example/atpprojectpartc/exitIcon.png").toString()) ;
        exitImage.setFitHeight(config.getIconSize());
        exitImage.setFitWidth(config.getIconSize());
        exitMenu.setGraphic(exitImage);

        ImageView helpImage = new ImageView(getClass().getResource("/org/example/atpprojectpartc/helpIcon.png").toString()) ;
        helpImage.setFitHeight(config.getIconSize());
        helpImage.setFitWidth(config.getIconSize());
        helpMenu.setGraphic(helpImage);

        ImageView aboutImage = new ImageView(getClass().getResource("/org/example/atpprojectpartc/aboutIcon.png").toString()) ;
        aboutImage.setFitHeight(config.getIconSize());
        aboutImage.setFitWidth(config.getIconSize());
        aboutMenu.setGraphic(aboutImage);

        ImageView soundImage = new ImageView(getClass().getResource("/org/example/atpprojectpartc/soundIcon.jpg").toString()) ;
        soundImage.setFitHeight(config.getIconSize());
        soundImage.setFitWidth(config.getIconSize());
        soundMenu.setGraphic(soundImage);

        initFileMenuItems();
        initPropertiesMenuItems();
        initExitMenuItems();
        initSoundMenuItems();
    }

    private void initFileMenuItems(){
        newItem.setOnAction(action -> {
            try {
                viewModel.generateMaze(mazeRows, mazeColumns);
            }catch (Exception e){
                HelloApplication.showExceptionWindow();
                return;
            }
        });
        loadItem.setOnAction(action -> {
            File selectedFile = lastLinkedMazeFile;
            if(lastLinkedMazeFile == null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open a File");
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("Maze Files", "*.maze")
                );

                selectedFile = fileChooser.showOpenDialog(null);
            }
            if (selectedFile != null) {
                viewModel.readFromFile(selectedFile);
                lastLinkedMazeFile = selectedFile;
            }
        });

        saveItem.setOnAction(action -> {
            File selectedFile = lastLinkedMazeFile;
            if(lastLinkedMazeFile == null) {
                // Step 1: Create a FileChooser for saving
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Your File");

                // Step 2: Set default file name and extension filter
                fileChooser.setInitialFileName("maze.maze");
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("Maze Files", "*.maze")
                );

                // Step 3: Show the save dialog
                selectedFile = fileChooser.showSaveDialog(null);

                // Step 4: Append ".maze" if user did not specify it
                if (!selectedFile.getName().toLowerCase().endsWith(".maze")) {
                    selectedFile = new File(selectedFile.getAbsolutePath() + ".maze");
                }
            }
            // Step 5: Write to the new file
            viewModel.writeToFile(selectedFile);
            lastLinkedMazeFile = selectedFile;
        });

        unlinkItem.setOnAction(action -> {
            lastLinkedMazeFile = null;
        });
    }

    private void initPropertiesMenuItems(){
        propertiesItem.setOnAction(action -> {
            try {
                Stage stage = new Stage();
                HelloApplication.allStages.add(stage);
                PropertiesWindowController.showingStage = stage ;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/atpprojectpartc/View/PropertiesWindowView.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("properties");
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                HelloApplication.showExceptionWindow();
                return;
            }
        });
    }

    private void initExitMenuItems(){
        exitItem.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("are you sure ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                for (Stage stage : HelloApplication.allStages) {
                    stage.close();
                }
                for (Alert a : HelloApplication.allAlerts) {
                    a.close();
                }
                ServerManager.shutDownServerManager();
            }
        });
    }

    private void initSoundMenuItems(){
        pickAudio.setOnAction(action -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Audio File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.aac")
            );
            audioMedia = new Media(fileChooser.showOpenDialog(null).toURI().toString());
        });

        startPlaying.setOnAction(action -> {
            MediaManager.playAudioRepeat(audioMedia);
        });

        stopPlaying.setOnAction(action -> {
            MediaManager.stopAll();
        });
    }

    public void initMazeBoardGame() {
        mazeBoardGame.setMazeAndPlayer(viewModel.getMaze() , viewModel.getPlayer());
        EventHandler<ActionEvent> solveHandler = event -> {
            try {
                viewModel.solveMaze();
            }catch (Exception e){
                HelloApplication.showExceptionWindow();
                return;
            }
        };
        EventHandler<ActionEvent> resetHandler = event -> {
            mazeBoardGame.updateSolution(null);
            viewModel.resetPlayerPosition() ;
        };
        mazeBoardGame.addButtonsListeners(solveHandler , resetHandler);
        mazeBoardGame.addKeyPressedListener(event -> {
            viewModel.movePlayer(event) ;
            event.consume();
        });
        mazeBoardGame.addSolvedListener(new CustomListener() {
            @Override
            public void update() {
                MediaManager.playAudio(winGameMedia);
                Stage newStage = new Stage(); // Create new window
                VBox layout = new VBox(new Label("Hello from the new window!"));
                layout.setAlignment(Pos.CENTER);
                layout.setPadding(new Insets(20));

                Scene scene = new Scene(layout, MainConfigurations.getInstance().getPopWindowWidth(), MainConfigurations.getInstance().getPopWindowHeight());
                newStage.setScene(scene);
                newStage.setTitle("New Window");
                newStage.show(); // Show the window
            }
        });

        mazeBoardGame.addMouseDraggedListener(event -> {
            double[] playerCoordinates = mazeBoardGame.getPlayerCoordinates();
            double xx = event.getX();
            double yy = event.getY();
            if(xx < 0)
                xx = 0 ;
            if(yy < 0)
                yy = 0 ;
            if(xx > mazeBoardGame.getDisplayerBounds().getWidth())
                xx = mazeBoardGame.getDisplayerBounds().getWidth() ;
            if(yy > mazeBoardGame.getDisplayerBounds().getHeight())
                yy = mazeBoardGame.getDisplayerBounds().getHeight() ;

            double deltaX = xx - playerCoordinates[0];
            double deltaY = yy - playerCoordinates[1];
            double w = MazeDisplayer.rectangleWidth ;
            double h = MazeDisplayer.rectangleHeight ;
            double acceptableLength = Math.sqrt(w * w + h * h) / 2;
            Direction d = getDirection(deltaX , deltaY , acceptableLength) ;
            if(d != Direction.STAY_IN_PLACE){
                viewModel.movePlayer(d) ;
            }
        });
    }

    public Direction getDirection(double x , double y , double acceptableLength){
        double length = Math.sqrt(x*x + y*y) ;
        if(length < acceptableLength)
            return Direction.STAY_IN_PLACE;
        y = y / length ;
        x = x / length ;
        double angle = Math.atan2(y , x);
        angle += PI / 8 ;
        if(angle < 0)
            angle += 2*PI ;
        int toCheck = (int) (angle / (PI / 4));
        switch (toCheck){
            case 0 -> {return Direction.RIGHT;}
            case 1 -> {return Direction.RIGHT_DOWN;}
            case 2 -> {return Direction.DOWN;}
            case 3 -> {return Direction.LEFT_DOWN;}
            case 4 -> {return Direction.LEFT;}
            case 5 -> {return Direction.LEFT_UP;}
            case 6 -> {return Direction.UP;}
            case 7 -> {return Direction.RIGHT_UP;}
        }
        return Direction.RIGHT; //DEFAULT value for testing (if always right thus problem)
    }

    @Override
    public void update(Observable o, Object arg) {
        Change change = (Change) arg;
        switch (change) {
            case mazeGenerated -> {reDrawMaze();}
            case solutionChanged -> {solutionGenerated();}
            case playerMoved -> {updatePlayer();}
            case mazeChanged -> {reDrawMaze();}
        }
    }

    public void reDrawMaze(){
        MazeDTO mazeDTO = viewModel.getMaze() ;
        Player player = viewModel.getPlayer() ;
        mazeBoardGame.setMazeAndPlayer(mazeDTO , player);
    }
    public void solutionGenerated(){
        ArrayList<MazeState> solutionPath = viewModel.getSolutionPath();
        mazeBoardGame.updateSolution(solutionPath);
    }
    public void updatePlayer(){
        mazeBoardGame.updatePlayer(viewModel.getPlayer());
    }
    public void stageHeightChanged(double height){
        if(isFirst) {
            firstDiff = height - config.getWindowHeight();
            isFirst = false;
        }
        MazeDisplayer.rectangleHeight = (int) (height - firstDiff - 80) / mazeRows ;
        mazeBoardGame.resizeHeight(height + MazeBoardGame.buttonsHeight - firstDiff - 80);
        reDrawMaze();
    }
    public void stageWidthChanged(double width){
        MazeDisplayer.rectangleWidth = (int) width / mazeColumns ;
        mazeBoardGame.resizeWidth(width);
        reDrawMaze();
    }

    public void setStartStage(Stage stage){
        this.startStage = stage ;
    }
}