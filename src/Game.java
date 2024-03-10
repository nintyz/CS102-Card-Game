import java.util.ArrayList;

public class Game {

    final static int POOLCARD = 10, PLAYERCARD = 4;
    private int playerCount;
    private Player currentPlayer;
    private static ArrayList<Player> players;
    private static ArrayList<Card> poolCards;
    private ArrayList<Card> deck;

    public static void initializeRound(int playerCount) {
        Deck myDeck = new Deck(Suit.VALUES, Rank.VALUES);
        myDeck.shuffle();

        // Add pool cards
        poolCards = new ArrayList<Card>();
        for (int i = 0; i < POOLCARD; i++) {
            poolCards.add(myDeck.dealCard());
        }

        // Distribute cards to players
        for (int i = POOLCARD, playerIndex = 0; i < (PLAYERCARD * playerCount) + POOLCARD; i++, playerIndex++) {
            Player currentPlayer = players.get(playerIndex % playerCount);
            currentPlayer.addCard(myDeck.dealCard());
        }

        // Print pool deck
        System.out.println("Pool deck:" + poolCards);

        // Print hands of each player after distribution
        for (Player player : players) {
            System.out.println("Player's hand: " + player.getHand());
        }

    }

    public static void main(String[] args) {
        players = new ArrayList<>();
        players.add(new Player(1));
        players.add(new Player(2));
        initializeRound(2);
        // Deck myDeck = new Deck(Suit.VALUES, Rank.VALUES);
        // myDeck.shuffle();
        // Hand myHand = new Hand();
        // System.out.println("Before:");
        // for (int i = 0; i < 4; i++) {
        // myHand.addCard(myDeck.dealCard());
        // System.out.println(myHand.getCard(i));
        // }
        // System.out.println("After");
        // myHand.sort();
        // for(int i = 0; i < 4; i++) {
        // System.out.println(myHand.getCard(i));
        // }

        // while (!myDeck.isEmpty()) {
        // Card dealt = myDeck.dealCard();
        // System.out.println(dealt);
        // }

    }
}
