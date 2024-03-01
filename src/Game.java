import java.util.*;
public class Game {
    public static void main(String[] args) {
        Deck myDeck = new Deck(Suit.VALUES, Rank.VALUES);
        myDeck.shuffle();
        while (!myDeck.isEmpty()) {
            Card dealt = myDeck.dealCard();
            System.out.println(dealt);
        }

    }
}
