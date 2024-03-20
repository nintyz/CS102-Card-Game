import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

public class MatchCardController implements Initializable {

    private final int PLAYERCOUNT = 2;
    private int poolCardCount = 10, playerCardCount = 4;

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
    void discardButton(ActionEvent event) {
        System.out.println("Discard button clicked");
    }

    @FXML
    void matchButton(ActionEvent event) {
        capture();
        if (players.get(1).getTotalScore() > 1) {
            try {
                FXMLLoader loader = new FXMLLoader(new File("resources/view/end-game-scene.fxml").toURI().toURL());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.close();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }
        switchPlayer();
        replaceCardPool();
        populateBoard(poolCards, false);
        gameButton.setDisable(true);
    }

    // void getEndPrompt(Capture capture) {
    // Alert endGameAlert = new Alert(Alert.AlertType.NONE);
    // endGameAlert.setTitle("Game End");
    // endGameAlert.setHeaderText(String.format("Player %d wins! %n Last Capture:
    // %s", players.get(1).getPlayerId(), capture.getCaptureName()));
    // endGameAlert.setContentText("Click close to restart");
    // endGameAlert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    // endGameAlert.showAndWait();
    // }

    @FXML
    void startGame(ActionEvent event) {

        startGameButton.setVisible(false);
        gameButton.setDisable(true);
        handView.setVisible(true);

        populateBoard(poolCards, false);

        switchPlayer();

    }

    // public void getEndScene(ActionEvent event) {
    // try {
    // FXMLLoader loader = new FXMLLoader(new
    // File("resources/view/end-game-scene.fxml").toURI().toURL());
    // Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    // Scene scene = new Scene(loader.load());
    // stage.close();
    // stage.setScene(scene);
    // stage.show();
    // }catch (IOException e){
    // e.printStackTrace();
    // }
    // }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        handView.setVisible(false);
    }

    private void capture() {

        Player currentPlayer = players.get(1);

        Card selectedHandCard = currentPlayer.getSelectedHandCards().get(0);
        ArrayList<Card> selectedPoolCard = currentPlayer.getSelectedCards();

        Capture comboCapture = Capture.returnHighestCapture(selectedHandCard, selectedPoolCard);

        if (comboCapture != null) {
            // creates an alert dialog displaying the capture type, value and captureCards
            // involved
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Congratulations!");
            ArrayList<ImageView> cardList = new ArrayList<>();
            for (Card c : comboCapture.getCaptureCards()) {
                ImageView cardImage = new ImageView("file:resources/img/" + c.getCardImage());
                cardImage.setFitHeight(250);
                cardImage.setFitWidth(250);
                cardImage.setPreserveRatio(true);
                cardList.add(cardImage);
            }
            ObservableList<ImageView> obsCardList = FXCollections.observableArrayList(cardList);
            ListView<ImageView> cardListView = new ListView<>(obsCardList);
            alert.setGraphic(cardListView);
            alert.setHeaderText(String.format("%s of value %.1f captured successfully!", comboCapture.getCaptureName(),
                    comboCapture.getScore()));
            alert.setContentText("Click close to end your turn.");
            alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            alert.showAndWait();

            currentPlayer.getHand().remove(selectedHandCard);
            currentPlayer.setTotalScore(comboCapture.getScore());

            for (Card poolCard : selectedPoolCard) {
                poolCards.remove(poolCard);
            }
        }

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
            poolCards.add(deck.dealCard());
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
    }

    /**
     * This method sets the player view and score view to first player on first run,
     * then switches to the next player on subsequent runs
     **/
    private void switchPlayer() {

        // Switch player label
        playerLabel.setText(Integer.toString(players.get(0).getPlayerId() + 1));

        // Switch player score
        scoreLabel.setText(String.valueOf(players.get(0).getTotalScore() + 1));

        // Populate hand with the new player's hand
        populateBoard(players.get(0).getHand(), true);

        // Switch player
        players.add(players.get(0));
        players.remove(0);

    }

}
