import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

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
        // Alert alert = new Alert(AlertType.INFORMATION);
        // alert.setTitle("Information Dialog");
        // alert.setHeaderText("Look, an Information Dialog");
        // alert.setContentText("I have a great message for you!");

        // alert.showAndWait();
        capture();
        switchPlayer();

        // For testing, REMOVE LATER
        poolCards.remove(0);
        players.get(0).getHand().remove(0);

        replaceCardPool();

        populateBoard(poolCards, false);
        gameButton.setDisable(true);
    }

    @FXML
    void startGame(ActionEvent event) {

        startGameButton.setVisible(false);
        gameButton.setDisable(true);
        handView.setVisible(true);

        populateBoard(poolCards, false);

        switchPlayer();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeImageView();
    }

    private void capture() {
        // Test data
        // run
        // Card handCard = new Card(Suit.CLUBS, Rank.FOUR);
        // Card poolCard = new Card(Suit.CLUBS, Rank.TWO);
        // Card poolCard2 = new Card(Suit.CLUBS, Rank.THREE);
        // Straight
        // Card handCard = new Card(Suit.DIAMONDS, Rank.FOUR);
        // Card poolCard = new Card(Suit.CLUBS, Rank.TWO);
        // Card poolCard2 = new Card(Suit.CLUBS, Rank.THREE);
        // combo
        // Card handCard = new Card(Suit.DIAMONDS, Rank.FOUR);
        // Card poolCard = new Card(Suit.CLUBS, Rank.TWO);
        // Card poolCard2 = new Card(Suit.CLUBS, Rank.TWO);
        // triple
        // Card handCard = new Card(Suit.DIAMONDS, Rank.TWO);
        // Card poolCard = new Card(Suit.HEARTS, Rank.TWO);
        // Card poolCard2 = new Card(Suit.CLUBS, Rank.TWO);
        // pair
        // Card handCard = new Card(Suit.DIAMONDS, Rank.TWO);
        // Card poolCard = new Card(Suit.HEARTS, Rank.TWO);
        // Card poolCard2 = new Card(Suit.CLUBS, Rank.TWO);
        // pair
        Card handCard = new Card(Suit.DIAMONDS, Rank.TWO);
        Card poolCard = new Card(Suit.HEARTS, Rank.TWO);
        // Card poolCard2 = new Card(Suit.CLUBS, Rank.TWO);

        ArrayList<Card> poolCards = new ArrayList<>();
        poolCards.add(poolCard);
        // poolCards.add(poolCard2);

        // Straight combo = new Straight();
        // Capture comboCapture = combo.formCapture(handCard, poolCards);
        // if (comboCapture == null) {
        // System.out.println("null");
        // }
        Capture comboCapture = Capture.returnHighestCapture(handCard, poolCards);
        if (comboCapture != null) {
            System.out.println(comboCapture.getScore());
            players.get(1).setTotalScore(comboCapture.getScore());
        }
    }

    private void clearSelectedCards() {

        // Clear selected cards after each player's turn
        for (Player player : players) {
            player.getSelectedCards().clear();
            player.getSelectedHandCards().clear();
        }

    }

    /**
     * This will add a number to each ImageView and set the image to be the back of
     * a Card
     */
    private void initializeImageView() {

        handView.setVisible(false);

        for (int i = 0; i < cardPool.getChildren().size(); i++) {
            BorderPane borderPane = (BorderPane) cardPool.getChildren().get(i);

            ImageView imageView = (ImageView) borderPane.getChildren().get(0);
            imageView.setImage(new Image("file:resources/img/back.png"));
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
        clearBoard(pool);

        for (int i = 0; i < cards.size(); i++) {
            BorderPane borderPane = (BorderPane) pool.getChildren().get(i);

            ImageView imageView = (ImageView) borderPane.getChildren().get(0);
            imageView.setImage(new Image("file:resources/img/" + cards.get(i).getCardImage()));
            imageView.setUserData(cards.get(i));

            registerClickListener(borderPane, imageView);
        }
    }

    private void clearBoard(FlowPane flowPane) {

        for (int i = 0; i < flowPane.getChildren().size(); i++) {
            BorderPane borderPane = (BorderPane) flowPane.getChildren().get(i);

            ImageView imageView = (ImageView) borderPane.getChildren().get(0);

            // Remove the click listener and the image
            imageView.setOnMouseClicked(null);
            imageView.setImage(null);

            // Reset the state of the border of the ImageView
            borderPane.pseudoClassStateChanged(imageViewBorder, false);

        }

        clearSelectedCards();
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

            setMatchButtonState((Card) imageView.getUserData());
        });

    }

    private void setMatchButtonState(Card selectedCard) {

        Player currentPlayer = players.get(1);

        boolean isSelected = currentPlayer.getSelectedCards().contains(selectedCard);
        boolean isHandSelected = currentPlayer.getSelectedHandCards().contains(selectedCard);

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
