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
    private ArrayList<Player> players = new ArrayList<>();

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

    }

    @FXML
    void startGame(ActionEvent event) {
        startGameButton.setVisible(false);
        handView.setVisible(true);

        // Initlialize players
        for (int i = 0; i < PLAYERCOUNT; i++) {
            players.add(new Player(i));
        }

        Deck deck = new Deck(Suit.VALUES, Rank.VALUES);
        deck.shuffle();

        populateCardPool(deck);

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

    private void populateCardPool(Deck deck) {
        for (int i = 0; i < cardPool.getChildren().size(); i++) {
            ImageView imageView = (ImageView) cardPool.getChildren().get(i);
            imageView.setImage(new Image("file:resources/img/" + deck.dealCard().getCardImage()));
        }
    }

    private void populateHand(Deck deck) {
        // Distribute cards to players
        for (int i = 0; i < (PLAYERCARD * PLAYERCOUNT); i++) {
            Player currentPlayer = players.get(i % PLAYERCOUNT);
            currentPlayer.addCard(deck.dealCard());
        }

        // Print hands of each player after distribution
        for (Player player : players) {
            System.out.println("Player's hand: " + player.getHand());
        }

        // populate cardhand
        for (int i = 0; i < handCard.getChildren().size(); i++) {
            ImageView imageView = (ImageView) handCard.getChildren().get(i);
            imageView.setImage(new Image("file:resources/img/" + players.get(0).getHand().get(i).getCardImage()));
            imageView.setUserData(i);
        }
        
    }

}
