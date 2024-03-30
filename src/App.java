import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * view.App.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class is the entry point of the application
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
 */

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        
        // Load the main game FXML(UI) file
        Parent mainParent = FXMLLoader.load(new File("resources/view/match-cards.fxml").toURI().toURL());
        Scene mainScene = new Scene(mainParent);

        // Set the parameters of the stage(game window)
        stage.setTitle("Matching Game");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.show();

        // Exit the application when the window is closed
        stage.setOnCloseRequest(e -> Platform.exit());
    }

    public static void main(String[] args) {
        launch();
    }

}
