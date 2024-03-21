import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Load the main game FXML(UI) file
        FXMLLoader loader = new FXMLLoader(new File("resources/view/match-cards.fxml").toURI().toURL());
        Parent mainParent = loader.load();
        Scene mainScene = new Scene(mainParent);

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
