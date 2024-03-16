import java.util.ArrayList;
import java.util.Scanner;

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

    public static void capturePhase(Player currentPlayer) {
        System.out.println("Start PoolCard Capture Phase");
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            while (true) {
                System.out.print("Enter index of poolCard> ");
                int idx = sc.nextInt();
                sc.nextLine();
                try {
                    Card selected = poolCards.get(idx);
                    players.get(0).addSelectedCard(selected);
                    System.out.println(currentPlayer.getSelectedCards());
                    break;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Enter a valid index!");
                }
            }
            System.out.print("If done input d> ");
            String done = sc.nextLine();
            if (done.equalsIgnoreCase("d")) {
                break;
            }
        }
        System.out.println("End of poolCard selection phase!");
        System.out.println("Start handCard selection phase!");
            while (true) {
                System.out.print("Enter index of handCard> ");
                int idx = sc.nextInt();
                sc.nextLine();
                try {
                    currentPlayer.setSelectedHandCard(currentPlayer.getHand().get(idx));
                    System.out.println(currentPlayer.getSelectedCards());
                    break;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Enter a valid index!");
                }
            }
            System.out.println("End of handCard selection phase!");
            
            // form capture
            // Card[] selectedCardArray;
            // if (currentPlayer.getSelectedCards() instanceof ArrayList<Card>) {
            //     selectedCardArray = (Card[]) currentPlayer.getSelectedCards().toArray();
            // }
            // Capture capture = Capture.returnHighestCapture(currentPlayer.getselectedHandCard(), selectedCardArray);
            
    }
    public static void main(String[] args) {
        players = new ArrayList<>();
        players.add(new Player(1));
        players.add(new Player(2));
        initializeRound(2);
        capturePhase(players.get(0));



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
