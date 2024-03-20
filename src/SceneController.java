import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SceneController{
    private Scene nextScene;

    public void setNextScene(Scene nextScene) {
        this.nextScene = nextScene;
    }

    public void openNextScene(ActionEvent actionEvent) {
        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        currentStage.setScene(nextScene);
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
