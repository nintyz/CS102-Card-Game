package util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.card.Card;
import model.card.Rank;
import model.card.Suit;
import model.game.Deck;
import model.game.Player;

public class GameUtil {
    public static final String MAIN_SCENE = "resources/view/match-cards.fxml";
    public static final String END_SCENE = "resources/view/end-game.fxml";

    private static final int WINNING_SCORE = Integer.parseInt(getConfig().getProperty("winningScore"));

    private static final int PLAYER_COUNT = 2;
    private static final int POOL_CARD_COUNT = 10;
    private static final int HAND_CARD_COUNT = 4;
    private static final DecimalFormat DECFORMAT = new DecimalFormat("#.##");

    public static DecimalFormat getDecFormat() {
        return DECFORMAT;
    }

    public static Deck initializeDeck() {
        Deck deck = new Deck(Suit.VALUES, Rank.VALUES);
        deck.shuffle();

        return deck;
    }

    public static ArrayList<Card> initializeCardPool(Deck deck) {

        ArrayList<Card> poolCards = new ArrayList<Card>();

        for (int i = 0; i < POOL_CARD_COUNT; i++) {
            poolCards.add(deck.dealCard());
        }

        return poolCards;
    }

    public static ArrayList<Player> initializePlayers(Deck deck) {
        ArrayList<Player> players = new ArrayList<Player>();

        // Create players
        for (int i = 0; i < PLAYER_COUNT; i++) {
            players.add(new Player(i));
        }

        // Distribute cards to players
        for (int i = 0; i < (HAND_CARD_COUNT * PLAYER_COUNT); i++) {
            Player currentPlayer = players.get(i % PLAYER_COUNT);
            currentPlayer.getHand().add(deck.dealCard());
        }

        // Print hands of each player after distribution
        for (Player player : players) {
            System.out.println("Player's hand: " + player.getHand());
        }

        return players;

    }

    public static int getWinningScore() {
        return WINNING_SCORE;
    }

    public static void clearSelectedCards(List<Player> players) {

        // Clear selected cards after each player's turn
        for (Player player : players) {
            player.getSelectedCards().clear();
            player.getSelectedHandCards().clear();
        }

    }

    public static void resetHand(Player player, Deck deck) {
        ArrayList<Card> currentHand = player.getHand();
        currentHand.clear();

        while (currentHand.size() < HAND_CARD_COUNT) {
            currentHand.add(deck.dealCard());
        }

    }

    public static void resetPoolCards(List<Card> poolCards, Deck deck) {
        poolCards.clear();

        while (poolCards.size() < POOL_CARD_COUNT) {
            poolCards.add(deck.dealCard());
        }

    }

    public static void replaceCardPool(List<Card> poolCards, Deck deck, List<Player> players) {
        while (poolCards.size() < POOL_CARD_COUNT) {
            /**
             * refills the deck and resets the pool and handcards of the players with new
             * cards when deck is empty
             */
            if (deck.isEmpty()) {
                deck = new Deck(Suit.VALUES, Rank.VALUES);
                deck.shuffle();

                resetPoolCards(poolCards, deck);
                for (Player p : players) {
                    resetHand(p, deck);
                }

                break;
            }

            poolCards.add(deck.dealCard());

        }
    }

    public static void replaceHandCard(List<Card> poolCards, Deck deck, List<Player> players) {
        Player currentPlayer = players.get(1);

        while (currentPlayer.getHand().size() < HAND_CARD_COUNT) {

            if (deck.isEmpty()) {
                deck = new Deck(Suit.VALUES, Rank.VALUES);
                deck.shuffle();

                resetPoolCards(poolCards, deck);
                for (Player p : players) {
                    resetHand(p, deck);
                }

                break;
            }

            currentPlayer.getHand().add(deck.dealCard());

        }
    }

    private static Properties getConfig() {
        // Get the configuration from the config file
        Properties config = new Properties();

        try (InputStream input = new FileInputStream("app.config")) {
            config.load(input);

        } catch (Exception e) {
            System.out.println("Error reading config file: " + e.getMessage());
        }

        return config;
    }

    /**
     * Function to get the score text for a player
     */

    public String getPlayerScoreText(Player player, double score1, double score2) {
        return player.getPlayerId() == 0 ? formatScore(score1) : formatScore(score2);
    }

    public String formatScore(double score) {
        return String.valueOf(DECFORMAT.format(score));
    }

    public static boolean winningScoreReached(List<Player> players) {
        return (players.get(0).getTotalScore() >= WINNING_SCORE || players.get(1).getTotalScore() >= WINNING_SCORE);
    }
}
