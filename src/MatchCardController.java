import java.net.URL;
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

        Deck deck = new Deck(Suit.VALUES, Rank.VALUES);
        deck.shuffle();

        populateCardPool(deck);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
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
            String test = deck.dealCard().getCardImage();
            System.out.println(test);
            imageView.setImage(new Image("file:resources/img/" + test));
        }
    }

}
