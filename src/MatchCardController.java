import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * MatchCardController.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class controls the FX elements of the application and intergrates the game logic into them
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
 */

public class MatchCardController implements Initializable {

    private final int PLAYERCOUNT = 2;
    private final int poolCardCount = 10;
    private final int playerCardCount = 4;
    private final int winningScore = 7;

    private Deck deck = initializeDeck();
    private ArrayList<Player> players = initializePlayers();
    private ArrayList<Card> poolCards = initializeCardPool();

    private PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");

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

    @FXML
    private HBox endingCapture;

    @FXML
    private Label playerOneScore;

    @FXML
    private Label playerTwoScore;

    @FXML
    private Button quitButton;

    @FXML
    private Label winningText;

    @FXML
    void discardButton(ActionEvent event) {
        Player currentPlayer = players.get(1);
        Card selectedHandCard = currentPlayer.getSelectedHandCards().get(0);
        currentPlayer.getHand().remove(selectedHandCard);
        currentPlayer.getSelectedCards().clear();
        replaceHandCard(currentPlayer);
        populateBoard(poolCards, false);

        switchPlayer();
    }

    @FXML
    void matchButton(ActionEvent event) throws IOException {
        System.out.println("current" + players.get(1).getPlayerId());
        System.out.println(players.get(0).getPlayerId());
        Player currentPlayer = players.get(1);

        Capture capture = capture(currentPlayer);

        // return to main method when captureAttempt is null (capture is invalid)
        if (capture == null) {
            return;
        }

        /*
         * checks if current player has reached the winning score, if yes switch to end
         * game scene
         */
        if (players.get(1).getTotalScore() >= winningScore) {
            switchScene(event, "end-game-scene.fxml");

            winningText.setText("Player " + (players.get(1).getPlayerId() + 1) + " wins!");

            return;
        }

        replaceCardPool();
        populateBoard(poolCards, false);
        System.out.println("Deck size: " + deck.getNumberOfCardsRemaining());
    }

    private void switchScene(ActionEvent event, String sceneName) throws IOException {

        Parent root = FXMLLoader.load(new File("resources/view/" + sceneName).toURI().toURL());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void startGame(ActionEvent event) {
        // if (players.get(1).getTotalScore() > 1) {
        // Stage currentStage = (Stage) ((Node)
        // event.getSource()).getScene().getWindow();
        // SceneController.switchStartScene(currentStage);
        // return;
        // }

        startGameButton.setVisible(false);
        handView.setVisible(true);
        populateBoard(poolCards, false);
        switchPlayer();
    }

    @FXML
    void restartGame(ActionEvent event) throws IOException {
        switchScene(event, "match-cards.fxml");
        startGame(event);
    }

    @FXML
    void quitGame(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        handView.setVisible(false);
    }

    private Capture capture(Player currentPlayer) {

        Card selectedHandCard = currentPlayer.getSelectedHandCards().get(0);
        ArrayList<Card> selectedPoolCard = currentPlayer.getSelectedCards();

        Capture capture = Capture.returnHighestCapture(selectedHandCard, selectedPoolCard);

        // create and show invalidCaptureAlert when capture is null (i.e capture is
        // invalid)
        if (capture == null) {
            getInvalidCaptureAlert();
            return capture;
        }

        //
        getValidCaptureAlert(capture);

        currentPlayer.getHand().remove(selectedHandCard);
        replaceHandCard(currentPlayer);

        currentPlayer.setTotalScore(capture.getScore());

        for (Card poolCard : selectedPoolCard) {
            poolCards.remove(poolCard);
        }

        switchPlayer();

        return capture;
    }

    /*
     * creates an alert prompting the user that a valid capture has been made,
     * dsiplaying its type, value, and card captures
     */
    private void getValidCaptureAlert(Capture capture) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Congratulations!");
        ArrayList<ImageView> cardList = new ArrayList<>();

        // create ImageView objects storing the images of the capture cards and adding
        // them to an ArrayList
        for (Card c : capture.getCaptureCards()) {
            ImageView cardImage = new ImageView("file:resources/img/" + c.getCardImage());
            cardImage.setFitHeight(250);
            cardImage.setFitWidth(250);
            cardImage.setPreserveRatio(true);
            cardList.add(cardImage);
        }

        // casting to obervablelist and then ListView for future storing
        ObservableList<ImageView> obsCardList = FXCollections.observableArrayList(cardList);
        ListView<ImageView> cardListView = new ListView<>(obsCardList);

        alert.setGraphic(cardListView);
        alert.setHeaderText(String.format("%s of value %.1f captured successfully!",
                capture.getCaptureName(), capture.getScore()));
        alert.setContentText("Click close to end your turn.");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }

    private void getInvalidCaptureAlert() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Oh No!");
        alert.setHeaderText("Invalid Capture! Please try again!");
        alert.setContentText("Click close to return to game screen.");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }

    private void clearSelectedCards() {

        // Clear selected cards after each player's turn
        for (Player player : players) {
            player.getSelectedCards().clear();
            player.getSelectedHandCards().clear();
        }

    }

    private Deck initializeDeck() {
        Deck deck = new Deck(Suit.VALUES, Rank.VALUES);
        deck.shuffle();

        return deck;
    }

    private void replaceCardPool() {
        while (poolCards.size() < poolCardCount) {

            if (deck.isEmpty()) {
                deck.restoreDeck(Suit.VALUES, Rank.VALUES);
                // use deck = new deck;
                // deck.shuffle();
            }

            poolCards.add(deck.dealCard());

        }
    }

    private void replaceHandCard(Player currentPlayer) {
        while (currentPlayer.getHand().size() < playerCardCount) {

            if (deck.isEmpty()) {
                deck.restoreDeck(Suit.VALUES, Rank.VALUES);
            }

            currentPlayer.getHand().add(deck.dealCard());

        }
    }

    private ArrayList<Card> initializeCardPool() {

        ArrayList<Card> poolCards = new ArrayList<Card>();

        for (int i = 0; i < poolCardCount; i++) {
            poolCards.add(deck.dealCard());
        }

        return poolCards;
    }

    private ArrayList<Player> initializePlayers() {
        ArrayList<Player> players = new ArrayList<Player>();

        // Create players
        for (int i = 0; i < PLAYERCOUNT; i++) {
            players.add(new Player(i));
        }

        // Distribute cards to players
        for (int i = 0; i < (playerCardCount * PLAYERCOUNT); i++) {
            Player currentPlayer = players.get(i % PLAYERCOUNT);
            currentPlayer.getHand().add(deck.dealCard());
        }

        // Print hands of each player after distribution
        for (Player player : players) {
            System.out.println("Player's hand: " + player.getHand());
        }

        return players;

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
            clearSelectedCards();
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
        scoreLabel.setText(String.valueOf(player.getTotalScore()));

        // Populate hand with the new player's hand
        populateBoard(player.getHand(), true);

        // Switch player
        players.add(players.get(0));
        players.remove(0);

        discardButton.setDisable(true);
        gameButton.setDisable(true);

    }

}
