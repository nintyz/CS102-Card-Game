/**
 * util.InitializeUtil.java
 * 
 * @version 1.0
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * Last modified: 31 Mar 2024
 */


package util;

import java.util.ArrayList;

import model.card.Card;
import model.card.Rank;
import model.card.Suit;
import model.game.Deck;
import model.game.Player;


/**
 * 
 * This class contains methods to initialize the starting deck, cardpool and players.
 * 
 */
public class InitializeUtil {
    
    public static Deck initializeDeck() {
        Deck deck = new Deck(Suit.VALUES, Rank.VALUES);
        deck.shuffle();

        return deck;
    }

    public static ArrayList<Card> initializeCardPool(Deck deck) {
        ArrayList<Card> poolCards = new ArrayList<Card>();

        for (int i = 0; i < GameUtil.POOL_CARD_COUNT; i++) {
            poolCards.add(deck.dealCard());
        }

        return poolCards;
    }

    public static ArrayList<Player> initializePlayers(Deck deck) {
        ArrayList<Player> players = new ArrayList<Player>();

        // Create players
        for (int i = 0; i < GameUtil.PLAYER_COUNT; i++) {
            players.add(new Player(i));
        }

        // Distribute cards to players
        for (int i = 0; i < (GameUtil.HAND_CARD_COUNT * GameUtil.PLAYER_COUNT); i++) {
            Player currentPlayer = players.get(i % GameUtil.PLAYER_COUNT);
            currentPlayer.getHand().add(deck.dealCard());
        }

        // Print hands of each player after distribution
        for (Player player : players) {
            System.out.println("Player's hand: " + player.getHand());
        }

        return players;
    }
}
