package org.example.atpprojectpartc.View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    public Label stepsLabel ;
    public Label timeLabel ;
    private Timeline timeline ;
    private int seconds = 0;


    @FXML
    protected void initialize() {
        HBox gameStats = new HBox();
        gameStats.setPadding(new Insets(15));
        gameStats.setAlignment(Pos.CENTER);
        gameStats.setSpacing(10);
        stepsLabel = new Label("Steps - 0");
        timeLabel = new Label("Time - 00:00");
        gameStats.getChildren().add(stepsLabel);
        gameStats.getChildren().add(timeLabel);

        topLayout.getChildren().add(gameStats) ;

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
        initHelpMenuItems();
        initAboutMenuItems();
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

    private void initHelpMenuItems(){
        gameRulesItem.setOnAction(action -> {
            String rules = "Rules :\n" +
                    "1) you may move the player via arrows\n" +
                    "2) you may move the player via Numpad\n" +
                    "3) you may move the player by dragging the character\n" +
                    "4) the black tiles are walls which can't be penetrated\n" +
                    "5) a player have the ability to move in all 4 directions\n" +
                    "6) a player have the ability to move in all 4 diagonal directions\n" +
                    "7) when dragging the player character , you can't pass throught walls\n" +
                    "8) there is a timer under the game board to keep you comparative ;)\n" +
                    "9) there is steps counter under the game board as well\n" +
                    "10) when the game is over a window will pop with information about time and steps";
            Stage stage = new Stage();
            HelloApplication.allStages.add(stage);
            VBox layout = new VBox(new Label(rules));
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(20));

            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.setTitle("Rules");
            stage.show(); // Show the window
        });
    }

    private void initAboutMenuItems(){
        algorithmsItem.setOnAction(action -> {
            String description = "Algorithms :\n" +
                    "this application is very rich with algorithms ,from maze generators\n" +
                    "to searching algorithms and drawing algorithms.\n" +
                    "the user can easily switch between the algorithms via the properties\n" +
                    "menu , there is a variety of algorithms for each task .\n" +
                    "there are 5 possible categories :\n" +
                    "generating algorithms , searching algorithms , player drawers , maze wall drawers\n" +
                    "and maze solution path drawers .\n" +
                    "the switching between the algorithms and the scalability of each category\n" +
                    "is made possible via well engineered structure such that uses variety of \n" +
                    "popular design patterns .\n" ;
            Stage stage = new Stage();
            HelloApplication.allStages.add(stage);
            VBox layout = new VBox(new Label(description));
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(20));

            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.setTitle("Algorithms");
            stage.show(); // Show the window
        });

        contributorsItem.setOnAction(action -> {
            String description = "Contributors :\n" +
                    "we are students from BGU university studying Information Systems Engineering\n" +
                    "Mohammad Alatawna , Danial Wiesel we both are in our second year of our degrees\n" +
                    "we are highly motivated engineers and this project was developed as a final homework\n" +
                    "project for a course \n";
            Stage stage = new Stage();
            HelloApplication.allStages.add(stage);
            VBox layout = new VBox(new Label(description));
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(20));

            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.setTitle("Algorithms");
            stage.show(); // Show the window
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
                String str = "Congratulation !! you solved the maze in " +  secondsToClock(seconds) + " , and in " + viewModel.getSteps() + " Steps !";
                VBox layout = new VBox(new Label(str));
                layout.setAlignment(Pos.CENTER);
                layout.setPadding(new Insets(20));

                Scene scene = new Scene(layout);
                newStage.setScene(scene);
                newStage.setTitle("Won");
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
            case mazeGenerated -> {reDrawMaze();resetParams();}
            case solutionChanged -> {solutionGenerated();}
            case playerMoved -> {updatePlayer();}
            case mazeChanged -> {reDrawMaze();resetParams();}
        }
    }

    private void resetParams(){
        if(timeline != null) {
            timeline.stop();
            seconds = 0 ;
            timeLabel.setText("Timer - 00:00");
        }
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
            timeLabel.setText("Timer - " + secondsToClock(seconds));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // keep running forever
        timeline.play(); // start the timer

        stepsLabel.setText("Steps - 0");
    }

    private String secondsToClock(int seconds){
        int minutes = seconds / 60 ;
        String result = "" ;
        if(minutes < 10)
            result += "0" ;
        result += minutes + ":" ;
        if(seconds < 10)
            result += "0" ;
        result += seconds ;
        return result ;
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
        stepsLabel.setText("Steps - " + viewModel.getSteps()) ;
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
}