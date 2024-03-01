import java.util.*;
public class Game {
    
    final int POOLCARD = 10, PLAYERCARD = 4;
    private int playerCount;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<Card> poolCards;
    private ArrayList<Card> deck;
    
    public static void initializeRound(int playerCount) {
        Deck myDeck = new Deck(Suit.VALUES, Rank.VALUES);
        myDeck.shuffle();

        // Add pool cards
        poolCards = new ArrayList<Card>();
        for (int i = 0; i < POOLCARD; i++) {
            poolCards.add(myDeck.get(i));
        }

        // Distribute cards to players
        for (int i = POOLCARD, playerIndex = 0; i < PLAYERCARD * playerCount; i++, playerIndex++) {
            Player currentPlayer = players.get(playerIndex % playerCount);
            currentPlayer.addCard(myDeck.get(i));
        }

    }

    public static void main(String[] args) {
        Deck myDeck = new Deck(Suit.VALUES, Rank.VALUES);
        myDeck.shuffle();
        Hand myHand = new Hand();
        System.out.println("Before:");
        for (int i = 0; i < 4; i++) {
            myHand.addCard(myDeck.dealCard());
            System.out.println(myHand.getCard(i));
        }
        System.out.println("After");
        myHand.sort();
        for(int i = 0; i < 4; i++) {
            System.out.println(myHand.getCard(i));
        }
    
        // while (!myDeck.isEmpty()) {
        //     Card dealt = myDeck.dealCard();
        //     System.out.println(dealt);
        // }

    }
}
