import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Load the main game FXML(UI) file
        FXMLLoader loader1 = new FXMLLoader(new File("resources/view/match-cards.fxml").toURI().toURL());
        Parent mainParent = loader1.load();
        Scene mainScene = new Scene(mainParent);

        // //load end scene FXML(UI) file
        // FXMLLoader loader2 = new FXMLLoader(new File("resources/view/match-cards-end-game-scene.fxml").toURI().toURL());
        // Parent endParent = loader2.load();
        // Scene endScene = new Scene(endParent);

        // // injecting end scene into controller of first scene 
        // SceneController mainSceneController = (SceneController) loader1.getController();
        // mainSceneController.setNextScene(endScene);

        // SceneController endSceneController = (SceneController) loader2.getController();
        // endSceneController.setNextScene(mainScene);

        // Set the parameters of the stage(game window)
        stage.setTitle("Matching Game");
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
