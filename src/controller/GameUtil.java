package controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import model.capture.Capture;
import model.card.Card;
import model.card.Rank;
import model.card.Suit;
import model.game.Deck;
import model.game.Player;

public class GameUtil {
    private static final int PLAYER_COUNT = 2;
    private static final int POOL_CARD_COUNT = 10;
    private static final int HAND_CARD_COUNT = 4;
    private static final int WINNING_SCORE = 7;
    private static final DecimalFormat DECFORMAT = new DecimalFormat("#.##");

    private static Deck deck = initializeDeck();
    private static ArrayList<Player> players = initializePlayers();
    private static ArrayList<Card> poolCards = initializeCardPool();

    public static Deck initializeDeck() {
        Deck deck = new Deck(Suit.VALUES, Rank.VALUES);
        deck.shuffle();

        return deck;
    }

    public static ArrayList<Card> initializeCardPool() {

        ArrayList<Card> poolCards = new ArrayList<Card>();

        for (int i = 0; i < POOL_CARD_COUNT; i++) {
            poolCards.add(deck.dealCard());
        }

        return poolCards;
    }

    public static ArrayList<Player> initializePlayers() {
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


    public static Deck getDeck() {
        return deck;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static ArrayList<Card> getPoolCards() {
        return poolCards;
    }

    public static int getWinningScore() {
        return WINNING_SCORE;
    }
    public static void clearSelectedCards() {

        // Clear selected cards after each player's turn
        for (Player player : players) {
            player.getSelectedCards().clear();
            player.getSelectedHandCards().clear();
        }

    }


    public static void resetHand(Player player) {
        ArrayList<Card> currentHand = player.getHand();
        currentHand.clear();

        while (currentHand.size() < HAND_CARD_COUNT) {
            currentHand.add(deck.dealCard());
        }

    }

    public static void resetPoolCards() {
        poolCards.clear();

        while (poolCards.size() < POOL_CARD_COUNT) {
            poolCards.add(deck.dealCard());
        }

    }

    public static void replaceCardPool() {
        while (poolCards.size() < POOL_CARD_COUNT) {
            /**
             * refills the deck and resets the pool and handcards of the players with new
             * cards when deck is empty
             */
            if (deck.isEmpty()) {
                deck = new Deck(Suit.VALUES, Rank.VALUES);
                deck.shuffle();

                resetPoolCards();
                for (Player p : players) {
                    resetHand(p);
                }

                break;
            }

            poolCards.add(deck.dealCard());

        }
    }

    public static void replaceHandCard(Player currentPlayer) {
        while (currentPlayer.getHand().size() < HAND_CARD_COUNT) {

            if (deck.isEmpty()) {
                deck = new Deck(Suit.VALUES, Rank.VALUES);
                deck.shuffle();

                resetPoolCards();
                for (Player p : players) {
                    resetHand(p);
                }

                break;
            }

            currentPlayer.getHand().add(deck.dealCard());
            
        }
    }

    public static String getPlayerScoreText(Player player, double score1, double score2) {
        return player.getPlayerId() == 0 ? formatScore(score1) : formatScore(score2);
    }

    public static String formatScore(double score) {
        return String.valueOf(DECFORMAT.format(score));
    }

    public static boolean winningScoreReached(Capture capture) throws IOException {
        return (players.get(0).getTotalScore() >= WINNING_SCORE || players.get(1).getTotalScore() >= WINNING_SCORE);
    }
}
