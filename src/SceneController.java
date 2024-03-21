import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * SceneController class handles the switching of scenes between the main game scene and end scene
**/ 

public class SceneController{
    private static final String endScenePath = "resources/view/end-game-scene.fxml";
    private static final String mainScenePath = "resources/view/match-cards.fxml";
    
    public static void switchEndScene(Stage stage) {
        try {
                FXMLLoader loader = new FXMLLoader(new File(endScenePath).toURI().toURL());
                Scene scene = new Scene(loader.load());
                stage.close();
                stage.setTitle("Game Over");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    public static void switchStartScene(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(new File(mainScenePath).toURI().toURL());
            Scene scene = new Scene(loader.load());
            stage.close();
            stage.setTitle("Matching Game");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();;
        }
    }


    
    // public void switchToGameScene() {

    //     try {
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/view/match-cards.fxml"));
    //         Stage stage = new Stage();
    //         stage.setScene(new Scene(loader.load())); 
    //         // Set the title for the second scene 
    //         stage.setTitle("Matching Game"); 
    //         // Show the second scene 
    //         stage.show();
    //     } catch (IOException e) {
    //         System.out.println(e.getMessage());
    //     }
    // }

    // public void switchToEndScene(Stage stage) {
    //     //new File("resources/view/match-cards-end-scene.fxml").toURI().toURL())
    //     try {
    //         FXMLLoader loader = new FXMLLoader(new File("resources/view/match-cards-end-game-scene.fxml").toURI().toURL());
            
    //         Scene scene = new Scene(loader.load());
    //         System.out.println("setting the scene...");
    //         stage.setScene(scene); 
    //         System.out.println("setting the scene2...");
    //         // Set the title for the second scene 
    //         stage.setTitle("End Game"); 
    //         // Show the second scene 
    //         stage.show();
    //     } catch (IOException e) {
    //         System.out.println(e.getMessage());
    //     }

    // }
}
