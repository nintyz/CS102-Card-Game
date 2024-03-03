import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Load the FXML(UI) file
        FXMLLoader loader = new FXMLLoader(new File("resources/view/match-cards.fxml").toURI().toURL());
        Scene scene = new Scene(loader.load());

        // Set the parameters of the stage(game window)
        stage.setTitle("Matching Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
