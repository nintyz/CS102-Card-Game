/**
 * controller.MatchCardController.java
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.capture.Capture;
import model.card.Card;
import model.game.Deck;
import model.game.Player;
import util.GameUtil;
import util.InitializeUtil;
import util.SceneUtil;

/**
 * This class controls the FX elements of the main game scene and integrates the
 * game logic into them
 */
public class MatchCardController implements Initializable {

    private Deck deck = InitializeUtil.initializeDeck();
    private ArrayList<Player> players = InitializeUtil.initializePlayers(deck);
    private ArrayList<Card> poolCards = InitializeUtil.initializeCardPool(deck);

    private PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");

    @FXML
    private Label startGameLabel;

    @FXML
    private FlowPane cardPool;

    @FXML
    private Button discardButton;

    @FXML
    private Button gameButton;

    @FXML
    private FlowPane handCard;

    @FXML
    private HBox handView;

    @FXML
    private Label playerLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Button startGameButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        handView.setVisible(false);
    }

    @FXML
    void startGame(ActionEvent event) {
        startGameButton.setVisible(false);
        startGameLabel.setVisible(false);
        handView.setVisible(true);

        populateBoard(poolCards, false);
        switchPlayer();
    }

    @FXML
    void matchButton(ActionEvent event) throws IOException {
        Player currentPlayer = players.get(1);

        Capture capture = attemptCapture(currentPlayer);

        if (capture == null) {
            return;
        }

        if (GameUtil.reachedWinningScore(players)) {
            switchToEndScene(event, capture);
            return;
        }

        populateBoard(poolCards, false);
        System.out.println("Deck size: " + deck.getNumberOfCardsRemaining());
    }

    @FXML
    void discardButton(ActionEvent event) {
        Player currentPlayer = players.get(1);
        Card selectedHandCard = currentPlayer.getSelectedHandCards().get(0);
        currentPlayer.getHand().remove(selectedHandCard);

        currentPlayer.getSelectedCards().clear();
        deck = GameUtil.replaceMissingCards(poolCards, deck, players, true);
        populateBoard(poolCards, false);

        switchPlayer();
        System.out.println("Deck size: " + deck.getNumberOfCardsRemaining());
    }

    @FXML
    void quitGame(ActionEvent event) {
        Platform.exit();
    }

    private Capture attemptCapture(Player currentPlayer) {
        Card selectedHandCard = currentPlayer.getSelectedHandCards().get(0);
        ArrayList<Card> selectedPoolCard = currentPlayer.getSelectedCards();

        Capture capture = Capture.returnHighestCapture(selectedHandCard, selectedPoolCard);

        // create and show invalidCaptureAlert when capture is null (i.e capture is
        // invalid), else show validCaptureAlert
        if (capture == null) {
            showInvalidCaptureAlert();
            return capture;
        }

        showValidCaptureAlert(capture);

        // remove selected cards from pool and hand
        for (Card poolCard : selectedPoolCard) {
            poolCards.remove(poolCard);
        }

        currentPlayer.getHand().remove(selectedHandCard);

        // replace hand cards and pool cards after a successful capture
        deck = GameUtil.replaceMissingCards(poolCards, deck, players, true);
        deck = GameUtil.replaceMissingCards(poolCards, deck, players, false);

        currentPlayer.setTotalScore(capture.getScore());

        switchPlayer();

        return capture;
    }

    private void showInvalidCaptureAlert() {
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.setTitle("Oh No!");
        alert.setHeaderText("Invalid Capture! Please try again!");
        alert.setContentText("Click close to return to game screen.");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:resources/img/black_joker.png"));

        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }

    private void showValidCaptureAlert(Capture capture) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Congratulations!");

        DialogPane dialogPane = alert.getDialogPane();
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image("file:resources/img/black_joker.png"));

        // alert header
        Label headerLabel = getValidCaptureAlertHeader(capture);

        // alert content (captured cards)
        FlowPane imagePane = getValidCaptureAlertCaptures(capture);

        // stack headerLabel and imagePane vertically
        GridPane grid = new GridPane();
        grid.add(headerLabel, 0, 0);
        grid.add(imagePane, 0, 1);

        dialogPane.setHeader(grid);

        alert.setContentText("Click close to end your turn.");
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }

    private Label getValidCaptureAlertHeader(Capture capture) {
        Label headerLabel = new Label(String.format("%s of value %.1f captured successfully!",
                capture.getCaptureName(), capture.getScore()));

        headerLabel.setAlignment(Pos.CENTER);

        headerLabel.setMaxWidth(Double.MAX_VALUE);
        headerLabel.setMaxHeight(Double.MAX_VALUE);

        headerLabel.setStyle("-fx-font-size: 20px;");

        return headerLabel;
    }

    private FlowPane getValidCaptureAlertCaptures(Capture capture) {
        FlowPane imagePane = new FlowPane();
        imagePane.setAlignment(Pos.CENTER);
        imagePane.setHgap(4);

        for (Card c : capture.getCaptureCards()) {
            ImageView cardImage = new ImageView("file:resources/img/" + c.getCardImage());
            cardImage.setFitHeight(120);
            cardImage.setFitWidth(120);
            cardImage.setPreserveRatio(true);

            imagePane.getChildren().add(cardImage);
        }

        return imagePane;
    }

    /**
     * This method fills up either the cardPool or handCard flowPane with images of the cards passed into
     * the cards parameter
     */
    private void populateBoard(List<Card> cards, Boolean isPlayer) {
        FlowPane pool = isPlayer ? handCard : cardPool;
        clearBoard(pool, false);

        for (int i = 0; i < cards.size(); i++) {
            BorderPane borderPane = (BorderPane) pool.getChildren().get(i);

            ImageView imageView = (ImageView) borderPane.getChildren().get(0);
            imageView.setImage(new Image("file:resources/img/" + cards.get(i).getCardImage()));
            imageView.setUserData(cards.get(i));

            registerClickListener(borderPane, imageView);
        }
    }

    /**
     * This method empties the cardPool or handCard flowPane of card images
     */
    private void clearBoard(FlowPane flowPane, boolean isHand) {
        for (int i = 0; i < flowPane.getChildren().size(); i++) {
            BorderPane borderPane = (BorderPane) flowPane.getChildren().get(i);

            ImageView imageView = (ImageView) borderPane.getChildren().get(0);

            if (!isHand) {
                // Remove the click listener and the image
                imageView.setOnMouseClicked(null);
                imageView.setImage(null);

            }

            // Reset the state of the border of the ImageView
            borderPane.pseudoClassStateChanged(imageViewBorder, false);
        }

        if (!isHand) {
            for (Player player : players) {
                player.getSelectedCards().clear();
                player.getSelectedHandCards().clear();
            }
        }
    }

    private void registerClickListener(BorderPane borderPane, ImageView imageView) {
        // Assign a css class to the borderPane.
        borderPane.getStyleClass().add("image-view-wrapper");

        // Create a property to hold the state of the border of borderPane
        BooleanProperty imageViewBorderActive = new SimpleBooleanProperty() {
            @Override
            protected void invalidated() {
                borderPane.pseudoClassStateChanged(imageViewBorder, get());
            }

        };

        // register a click listener
        imageView.setOnMouseClicked(event -> {

            System.out.println("You clicked on card " + imageView.getUserData());
            imageViewBorderActive.set(!imageViewBorderActive.get());

            setMatchDiscardButtonState((Card) imageView.getUserData(), borderPane);
        });
    }

    private void setMatchDiscardButtonState(Card selectedCard, BorderPane borderPane) {
        Player currentPlayer = players.get(1);

        boolean isSelected = currentPlayer.getSelectedCards().contains(selectedCard);
        boolean isHandSelected = currentPlayer.getSelectedHandCards().contains(selectedCard);

        // clear border of all hand cards
        if (borderPane.getParent().equals(handCard)) {
            clearBoard(handCard, true);
        }

        // Set the state of the border of the selected card if its from the hand.
        // As player can only select 1 card from the hand at a time, we clear the
        // selected card each time a new card is selected
        if (currentPlayer.getHand().contains(selectedCard)) {
            borderPane.pseudoClassStateChanged(imageViewBorder, !isHandSelected);
            currentPlayer.getSelectedHandCards().clear();
        }

        if (isHandSelected || isSelected) {
            currentPlayer.removeSelectedCard(selectedCard);
        } else {
            currentPlayer.addSelectedCard(selectedCard);
        }

        System.out.println("Selected pool cards: " + currentPlayer.getSelectedCards());
        System.out.println("Selected hand cards: " + currentPlayer.getSelectedHandCards());

        gameButton.setDisable(currentPlayer.getSelectedCards().isEmpty() ||
                currentPlayer.getSelectedHandCards().isEmpty());

        discardButton.setDisable(currentPlayer.getSelectedHandCards().isEmpty() ||
                currentPlayer.getSelectedCards().isEmpty() == false);
    }

    /**
     * This method sets the player view and score view to first player on first run,
     * then switches to the next player on subsequent runs
     **/
    private void switchPlayer() {
        Player player = players.get(0);

        // Switch player label
        playerLabel.setText(Integer.toString(player.getPlayerId() + 1));

        // Switch player score
        scoreLabel.setText(String.valueOf(SceneUtil.getDecFormat().format(player.getTotalScore())));

        // Populate hand with the new player's hand
        populateBoard(player.getHand(), true);

        // Switch player
        players.add(players.get(0));
        players.remove(0);

        discardButton.setDisable(true);
        gameButton.setDisable(true);
    }

    private void switchToEndScene(ActionEvent event, Capture capture) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(new File(SceneUtil.END_SCENE).toURI().toURL());
        Parent mainParent = loader.load();
        Scene mainScene = new Scene(mainParent);

        // Set parameters required for the end game scene
        EndGameController endGameController = (EndGameController) loader.getController();
        endGameController.setCurrentPlayer(players.get(0));
        endGameController.setNextPlayer(players.get(1));
        endGameController.setCapture(capture);

        stage.setScene(mainScene);
        stage.show();
    }
}