package controller;
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import model.cardModel.Card;
import model.gameModel.Player;
import model.captureModel.*;

/**
 * This class handles the switching of scenes between the main game scene and end scene and updating of JavaFX elements
**/ 

public class SceneController{
    private static MatchCardController controller;
    private static final String MAIN_SCENE = "resources/view/match-cards.fxml";
    private static final String END_SCENE = "resources/view/end-game-scene.fxml";

    public static void setMatchCardController(MatchCardController newController) {
        controller = newController;
    }

    public static MatchCardController switchEndScene(ActionEvent event, Player currentPlayer, double currentPlayerScore, Player nextPlayer,
                                                     double nextPlayerScore, Capture capture) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(new File(END_SCENE).toURI().toURL());
        Parent mainParent = loader.load();
        Scene mainScene = new Scene(mainParent);

        stage.setScene(mainScene);
        stage.show();
        controller = (MatchCardController) loader.getController();

        updateScoreLabels(currentPlayer, currentPlayerScore, nextPlayer, nextPlayerScore);
        updateWinningTextLabel(currentPlayer.getPlayerId());
        updateWinningCaptureLabel(capture.getCaptureName());
        populateCapturedCards(capture.getCaptureCards());

        return controller;
        
    }

    public static MatchCardController switchMainScene(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(new File(MAIN_SCENE).toURI().toURL());
        Parent mainParent = loader.load();
        Scene mainScene = new Scene(mainParent);

        stage.setScene(mainScene);
        stage.show();

        return (MatchCardController) loader.getController();
        
    }

    /**
     * Function to update the score labels in the end game scene
     */

    public static void updateScoreLabels(Player currentPlayer, double currentPlayerScore, Player nextPlayer,
            double nextPlayerScore) {

        String currentPlayerScoreText = controller.getPlayerScoreText(currentPlayer, currentPlayerScore, nextPlayerScore);
        String nextPlayerScoreText = controller.getPlayerScoreText(currentPlayer, nextPlayerScore, currentPlayerScore);

        controller.getPlayerOneScoreLabel().setText(currentPlayerScoreText);
        controller.getPlayerTwoScoreLabel().setText(nextPlayerScoreText);
    }

    /**
     * Function to update the winning text label in the end game scene
     */

    public static void updateWinningTextLabel(int playerId) {
        String winnerText = "Player " + (playerId + 1) + " wins!";
        controller.getWinningTextLabel().setText(winnerText);
    }

    public static void updateWinningCaptureLabel(String captureName) {
        controller.getWinningCaptureLabel().setText(captureName);
    }

    /**
     * Function to update the captured cards in the end game scene
     */

    public static void populateCapturedCards(Card[] capturedCards) {
        HBox endingCapture = controller.getEndingCapture();
        endingCapture.getChildren().clear(); // Clear previous captured cards

        for (Card card : capturedCards) {
            ImageView cardImageView = new ImageView(new Image("file:resources/img/" + card.getCardImage()));
            cardImageView.setFitHeight(169.0);
            cardImageView.setFitWidth(117.0);
            endingCapture.getChildren().add(cardImageView);
        }
    }

}
