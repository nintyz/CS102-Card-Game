/**
 * controller.EndGameController.java
 * 
 * @version 1.0
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * Last modified: 31 Mar 2024
 */

package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.capture.Capture;
import model.card.Card;
import model.game.Player;
import util.GameUtil;
import util.SceneUtil;

 /**
  * This class controls the FX elements of the end game scene and intergrates the
  * game logic into them
  */
public class EndGameController implements Initializable {

    GameUtil gameUtil = new GameUtil();

    private Player currentPlayer;
    private Player nextPlayer;
    private Capture capture;

    @FXML
    private HBox endingCapture;

    @FXML
    private HBox handView;

    @FXML
    private Label playerOneScoreLabel;

    @FXML
    private Label playerTwoScoreLabel;

    @FXML
    private Button quitButton;

    @FXML
    private Label winningCaptureLabel;

    @FXML
    private Label winningTextLabel;

    @FXML
    void quitGame(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void restartGame(ActionEvent event) throws IOException {
        switchToMainScene(event);
        System.out.println("New Game Started!");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Delay the processing of the initialize function until some undefined point in
        // the future, based on an internal timer that JavaFX uses to process events.
        // https://stackoverflow.com/a/68370305

        Platform.runLater(() -> {
            updateScoreLabels();
            updateWinningTextLabel();
            updateWinningCaptureLabel();
            populateWinningCapturedCards();

        });

    }

    /**
     * Function to update the score labels in the end game scene
     */
    private void updateScoreLabels() {
        double currentPlayerScore = currentPlayer.getTotalScore();
        double nextPlayerScore = nextPlayer.getTotalScore();

        String playerOneScore = SceneUtil.getPlayerScoreText(currentPlayer, currentPlayerScore, nextPlayerScore);
        String playerTwoScore = SceneUtil.getPlayerScoreText(currentPlayer, nextPlayerScore, currentPlayerScore);

        playerOneScoreLabel.setText(playerOneScore);
        playerTwoScoreLabel.setText(playerTwoScore);
    }

    /**
     * Function to update the winning text label in the end game scene
     */
    private void updateWinningTextLabel() {
        String winnerText = "Player " + (currentPlayer.getPlayerId() + 1) + " wins!";
        winningTextLabel.setText(winnerText);
    }

    private void updateWinningCaptureLabel() {
        winningCaptureLabel.setText(capture.getCaptureName());
    }

    /**
     * Function to update the captured cards in the end game scene
     */
    private void populateWinningCapturedCards() {

        endingCapture.getChildren().clear(); // Clear previous captured cards

        for (Card card : capture.getCaptureCards()) {
            ImageView cardImageView = new ImageView(new Image("file:resources/img/" + card.getCardImage()));
            cardImageView.setFitHeight(169.0);
            cardImageView.setFitWidth(117.0);
            endingCapture.getChildren().add(cardImageView);
        }
    }

    private void switchToMainScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(new File(SceneUtil.MAIN_SCENE).toURI().toURL());
        Parent mainParent = loader.load();
        Scene mainScene = new Scene(mainParent);

        stage.setScene(mainScene);
        stage.show();
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void setCapture(Capture capture) {
        this.capture = capture;
    }

}
