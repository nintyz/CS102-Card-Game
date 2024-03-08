import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class MatchCardController implements Initializable {

    final int POOLCARD = 10, PLAYERCARD = 4, PLAYERCOUNT = 2;
    private boolean hasDistributedCards = false;

    private Deck deck = initializeDeck();

    private ArrayList<Player> players = initializePlayers();
    private ArrayList<Card> poolCards = initializeCardPool();

    @FXML
    private FlowPane cardPool;

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
    void matchButton(ActionEvent event) {

        populateHand(deck);

    }

    @FXML
    void startGame(ActionEvent event) {

        if (!hasDistributedCards) {
            hasDistributedCards = true;

            // Distribute cards to players
            for (int i = 0; i < (PLAYERCARD * PLAYERCOUNT); i++) {
                Player currentPlayer = players.get(i % PLAYERCOUNT);
                currentPlayer.addCard(deck.dealCard());
            }

            // Print hands of each player after distribution
            for (Player player : players) {
                System.out.println("Player's hand: " + player.getHand());
            }
        }

        startGameButton.setVisible(false);
        handView.setVisible(true);

        populateCardPool(poolCards);

        populateHand(deck);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeImageView();
    }

    /**
     * This will add a number to each ImageView and set the image to be the back of
     * a Card
     */
    private void initializeImageView() {

        handView.setVisible(false);

        for (int i = 0; i < cardPool.getChildren().size(); i++) {
            // "cast" the Node to be of type ImageView
            ImageView imageView = (ImageView) cardPool.getChildren().get(i);
            imageView.setImage(new Image("file:resources/img/back.png"));
            imageView.setUserData(i);
        }
    }

    private void clearImageView() {

        for (int i = 0; i < cardPool.getChildren().size(); i++) {
            // "cast" the Node to be of type ImageView
            ImageView imageView = (ImageView) cardPool.getChildren().get(i);
            imageView.setImage(null);
            imageView.setUserData(i);
        }
    }

    private Deck initializeDeck() {
        Deck deck = new Deck(Suit.VALUES, Rank.VALUES);
        deck.shuffle();

        return deck;
    }

    private ArrayList<Player> initializePlayers() {

        ArrayList<Player> players = new ArrayList<Player>();

        for (int i = 0; i < PLAYERCOUNT; i++) {
            players.add(new Player(i));
        }

        return players;
    }

    private ArrayList<Card> initializeCardPool() {

        ArrayList<Card> poolCards = new ArrayList<Card>();

        for (int i = 0; i < POOLCARD; i++) {
            poolCards.add(deck.dealCard());
        }

        return poolCards;
    }

    private void populateCardPool(ArrayList<Card> cards) {

        clearImageView();

        for (int i = 0; i < POOLCARD; i++) {
            ImageView imageView = (ImageView) cardPool.getChildren().get(i);
            imageView.setImage(new Image("file:resources/img/" + cards.get(i).getCardImage()));
        }
    }

    private void populateHand(Deck deck) {

        // populate cardhand with the first player's hand
        for (int i = 0; i < PLAYERCARD; i++) {
            ImageView imageView = (ImageView) handCard.getChildren().get(i);
            imageView.setImage(new Image("file:resources/img/" + players.get(0).getHand().get(i).getCardImage()));
            imageView.setUserData(i);
        }

        // Switch player label
        playerLabel.setText(Integer.toString(players.get(0).getPlayerId() + 1));

        // Switch player score
        scoreLabel.setText(String.valueOf(players.get(0).getTotalScore() + 1));

        // Switch player
        players.add(players.get(0));
        players.remove(0);

    }

}
