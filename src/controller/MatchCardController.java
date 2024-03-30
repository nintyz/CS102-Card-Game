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
 * controller.MatchCardController.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class controls the FX elements of the application and intergrates the
 * game logic into them
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
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

        // return to main method when captureAttempt is null (capture is invalid)
        if (capture == null) {
            return;
        }

        /*
         * checks if current player has reached the winning score, if yes switch to end
         * game scene
         */
        if (GameUtil.winningScoreReached(players)) {
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
        GameUtil.replaceMissingHandCard(poolCards, deck, players);
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
            invalidCaptureAlert();
            return capture;
        }

        validCaptureAlert(capture);

        // remove selected cards from pool and hand
        for (Card poolCard : selectedPoolCard) {
            poolCards.remove(poolCard);
        }

        currentPlayer.getHand().remove(selectedHandCard);

        // replace hand cards and pool cards after a successful capture
        GameUtil.replaceMissingHandCard(poolCards, deck, players);
        GameUtil.replaceMissingPoolCards(poolCards, deck, players);

        currentPlayer.setTotalScore(capture.getScore());

        switchPlayer();

        return capture;
    }

    private void invalidCaptureAlert() {
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.setTitle("Oh No!");
        alert.setHeaderText("Invalid Capture! Please try again!");
        alert.setContentText("Click close to return to game screen.");

        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }

    private void validCaptureAlert(Capture capture) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Congratulations!");

        DialogPane dialogPane = alert.getDialogPane();

        // alert header
        Label headerLabel = validCaptureAlertHeader(capture);

        // alert content (captured cards)
        FlowPane imagePane = validCaptureAlertCaptures(capture);

        // stack headerLabel and imagePane vertically
        GridPane grid = new GridPane();
        grid.add(headerLabel, 0, 0);
        grid.add(imagePane, 0, 1);

        dialogPane.setHeader(grid);

        alert.setContentText("Click close to end your turn.");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }

    private Label validCaptureAlertHeader(Capture capture) {

        Label headerLabel = new Label(String.format("%s of value %.1f captured successfully!",
                capture.getCaptureName(), capture.getScore()));

        headerLabel.setAlignment(Pos.CENTER);

        headerLabel.setMaxWidth(Double.MAX_VALUE);
        headerLabel.setMaxHeight(Double.MAX_VALUE);

        headerLabel.setStyle("-fx-font-size: 20px;");

        return headerLabel;
    }

    private FlowPane validCaptureAlertCaptures(Capture capture) {

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

    private void clearBoard(FlowPane flowPane, boolean isHandBoard) {

        for (int i = 0; i < flowPane.getChildren().size(); i++) {
            BorderPane borderPane = (BorderPane) flowPane.getChildren().get(i);

            ImageView imageView = (ImageView) borderPane.getChildren().get(0);

            if (!isHandBoard) {
                // Remove the click listener and the image
                imageView.setOnMouseClicked(null);
                imageView.setImage(null);

            }

            // Reset the state of the border of the ImageView
            borderPane.pseudoClassStateChanged(imageViewBorder, false);

        }

        if (!isHandBoard) {
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

            setMatchButtonState((Card) imageView.getUserData(), borderPane);
        });

    }

    private void setMatchButtonState(Card selectedCard, BorderPane borderPane) {
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

// private MatchCardController controller = this;

// private final int PLAYER_COUNT = 2;
// private final int POOL_CARD_COUNT = 10;
// private final int HAND_CARD_COUNT = 4;
// private final int WINNING_SCORE = 7;

/**
 * Function to update the winning text label in the end game scene
 */

// private void updateWinningTextLabel(int playerId) {
// String winnerText = "Player " + (playerId + 1) + " wins!";
// controller.winningTextLabel.setText(winnerText);
// }

// private void updateWinningCaptureLabel(String captureName) {
// controller.winningCaptureLabel.setText(captureName);
// }

// /**
// * Function to update the captured cards in the end game scene
// */

// private void populateCapturedCards(Card[] capturedCards) {
// HBox endingCapture = controller.endingCapture;
// endingCapture.getChildren().clear(); // Clear previous captured cards

// for (Card card : capturedCards) {
// ImageView cardImageView = new ImageView(new Image("file:resources/img/" +
// card.getCardImage()));
// cardImageView.setFitHeight(169.0);
// cardImageView.setFitWidth(117.0);
// endingCapture.getChildren().add(cardImageView);
// }
// }

// private void switchScene(ActionEvent event, String sceneName) throws
// IOException {

// Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

// FXMLLoader loader = new FXMLLoader(new File("resources/view/" +
// sceneName).toURI().toURL());
// Parent mainParent = loader.load();
// Scene mainScene = new Scene(mainParent);

// stage.setScene(mainScene);
// stage.show();

// controller = (MatchCardController) loader.getController();

// }

// private ArrayList<Card> initializeCardPool() {

// ArrayList<Card> poolCards = new ArrayList<Card>();

// for (int i = 0; i < POOL_CARD_COUNT; i++) {
// poolCards.add(deck.dealCard());
// }

// return poolCards;
// }

// private ArrayList<Player> initializePlayers() {
// ArrayList<Player> players = new ArrayList<Player>();

// // Create players
// for (int i = 0; i < PLAYER_COUNT; i++) {
// players.add(new Player(i));
// }

// // Distribute cards to players
// for (int i = 0; i < (HAND_CARD_COUNT * PLAYER_COUNT); i++) {
// Player currentPlayer = players.get(i % PLAYER_COUNT);
// currentPlayer.getHand().add(deck.dealCard());
// }

// // Print hands of each player after distribution
// for (Player player : players) {
// System.out.println("Player's hand: " + player.getHand());
// }

// return players;

// }

/**
 * Function to update the score labels in the end game scene
 */

// private void updateScoreLabels(Player currentPlayer, double
// currentPlayerScore, Player nextPlayer,
// double nextPlayerScore) {

// String currentPlayerScoreText = getPlayerScoreText(currentPlayer,
// currentPlayerScore, nextPlayerScore);
// String nextPlayerScoreText = getPlayerScoreText(currentPlayer,
// nextPlayerScore, currentPlayerScore);

// controller.playerOneScoreLabel.setText(currentPlayerScoreText);
// controller.playerTwoScoreLabel.setText(nextPlayerScoreText);
// }

// SceneController.setMatchCardController(controller);
// SceneController.updateScoreLabels(currentPlayer, currentPlayerScore,
// nextPlayer, nextPlayerScore);
// SceneController.updateWinningTextLabel(currentPlayer.getPlayerId());
// SceneController.updateWinningCaptureLabel(capture.getCaptureName());
// SceneController.populateCapturedCards(capture.getCaptureCards());

// private void clearSelectedCards() {

// // Clear selected cards after each player's turn
// for (Player player : players) {
// player.getSelectedCards().clear();
// player.getSelectedHandCards().clear();
// }

// }

// private Deck initializeDeck() {
// Deck deck = new Deck(Suit.VALUES, Rank.VALUES);
// deck.shuffle();

// return deck;
// }

/**
 * This function compares player scores and switches scene when either player
 * has hit or exceeded the
 * winning score
 */
// private boolean winningScoreReached(ActionEvent event, Capture capture)
// throws IOException {

// if (GameUtil.winningScoreReached(capture, players)) {

// controller = SceneController.switchEndScene(event, players.get(0),
// players.get(0).getTotalScore(), players.get(1),
// players.get(1).getTotalScore(), capture);

// return true;
// }

// return false;
// }

// private void resetHand(Player player) {
// ArrayList<Card> currentHand = player.getHand();
// currentHand.clear();

// while (currentHand.size() < HAND_CARD_COUNT) {
// currentHand.add(deck.dealCard());
// }

// }

// private void resetPoolCards() {
// poolCards.clear();

// while (poolCards.size() < POOL_CARD_COUNT) {
// poolCards.add(deck.dealCard());
// }

// }

// private void replaceCardPool() {
// while (poolCards.size() < POOL_CARD_COUNT) {
// /**
// * refills the deck and resets the pool and handcards of the players with new
// * cards when deck is empty
// */
// if (deck.isEmpty()) {
// deck = new Deck(Suit.VALUES, Rank.VALUES);
// deck.shuffle();

// GameUtil.resetPoolCards(poolCards, deck);
// for (Player p : players) {
// GameUtil.resetHand(p, deck);
// }

// break;
// }

// poolCards.add(deck.dealCard());

// }
// }

// private void replaceHandCard(Player currentPlayer) {
// while (currentPlayer.getHand().size() < HAND_CARD_COUNT) {

// if (deck.isEmpty()) {
// deck = new Deck(Suit.VALUES, Rank.VALUES);
// deck.shuffle();

// GameUtil.resetPoolCards(poolCards, deck);
// for (Player p : players) {
// GameUtil.resetHand(p, deck);
// }

// break;
// }

// currentPlayer.getHand().add(deck.dealCard());

// }
// }

// public String getPlayerScoreText(Player player, double score1, double score2)
// {
// return player.getPlayerId() == 0 ? formatScore(score1) : formatScore(score2);
// }

// private String formatScore(double score) {
// return String.valueOf(DECFORMAT.format(score));
// }