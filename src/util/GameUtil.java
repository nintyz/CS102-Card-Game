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
    
    //obtains value of winning score from app.config file and stores it into WINNING_SCORE
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
     *
     * This method deals cards until the current player's hand or poolcards are full
     * 
     */

    public static void replaceMissingCards(List<Card> poolCards, Deck deck, List<Player> players, boolean isHand) {
        
        List<Card> cardList = poolCards;
        int limit = POOL_CARD_COUNT;
        if (isHand) {
            cardList = players.get(1).getHand();
            limit = HAND_CARD_COUNT;
        }

        while (cardList.size() < limit) {
            
            if (deck.isEmpty()) {
                resetDeckHandAndPoolCards(poolCards, players, deck);
                break;
            }

            cardList.add(deck.dealCard());

        }
    }

    /**
     * 
     * This method clears and refills both the pool and each players hands with cards dealt from the deck
     * @param poolCards
     * @param players
     * @param deck
     */

    public static void resetDeckHandAndPoolCards(List<Card> poolCards, List<Player> players, Deck deck) {
        
        deck = new Deck(Suit.VALUES, Rank.VALUES);
        deck.shuffle();

        poolCards.clear();
        while (poolCards.size() < POOL_CARD_COUNT) {
            poolCards.add(deck.dealCard());
        }
        
        for (Player player : players) {

            ArrayList<Card> currentHand = player.getHand();
            currentHand.clear();

            while (currentHand.size() < HAND_CARD_COUNT) {
                currentHand.add(deck.dealCard());
            }

        }

    }

    public static boolean winningScoreReached(List<Player> players) {
        return (players.get(0).getTotalScore() >= WINNING_SCORE || players.get(1).getTotalScore() >= WINNING_SCORE);
    }

}
