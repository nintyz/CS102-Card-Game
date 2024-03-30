package util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.card.Card;
import model.card.Rank;
import model.card.Suit;
import model.game.Deck;
import model.game.Player;

/**
 * util.GameUtil.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class contains game constants and methods directly manipulating non-javaFX elements of the game
 * game logic into them
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
 */

public class GameUtil {

    private static final int WINNING_SCORE = Integer.parseInt(getConfig().getProperty("winningScore"));

    private static final int PLAYER_COUNT = 2;
    private static final int POOL_CARD_COUNT = 10;
    private static final int HAND_CARD_COUNT = 4;

    public static int getWinningScore() {
        return WINNING_SCORE;
    }

    public static int getPlayerCount() {
        return PLAYER_COUNT;
    }

    public static int getPoolCardCount() {
        return POOL_CARD_COUNT;
    }

    public static int getHandCardCount() {
        return HAND_CARD_COUNT;
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

    public static boolean winningScoreReached(List<Player> players) {
        return (players.get(0).getTotalScore() >= WINNING_SCORE || players.get(1).getTotalScore() >= WINNING_SCORE);
    }
}
