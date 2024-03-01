import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        File fxmlFile = new File("D:\\School\\Sem 2\\CS102 Programming Fundamentals\\Project\\CS102-Card-Game\\resources\\match-cards.fxml");
        // FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/resources/match-cards.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("resources/match-cards.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlFile.toURI().toURL());
        Scene scene = new Scene(loader.load());
        stage.setTitle("Matching Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
