/**
 * view.App.java
 * 
 * @version 1.0
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * Last modified: 31 Mar 2024
 */

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.SceneUtil;

/**
 * This class is the entry point of the JavaFX application
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        
        // Load the main game FXML(UI) file
        Parent mainParent = FXMLLoader.load(new File(SceneUtil.MAIN_SCENE).toURI().toURL());
        Scene mainScene = new Scene(mainParent);

        // Settings for stage (game window)
        stage.setTitle("Matching Game");
        stage.getIcons().add(new Image("file:resources/img/black_joker.png"));
        stage.setResizable(false);

        stage.setScene(mainScene);
        stage.show();

        // Exit the application when the window is closed
        stage.setOnCloseRequest(e -> Platform.exit());
    }

    public static void main(String[] args) {
        launch();
    }

}
