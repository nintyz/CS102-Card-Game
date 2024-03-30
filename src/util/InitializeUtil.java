package util;

import java.util.ArrayList;

import model.card.Card;
import model.card.Rank;
import model.card.Suit;
import model.game.Deck;
import model.game.Player;

/**
 * util.InitializeUtil.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class contains methods to initialize the starting deck, cardpool and players.
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
 */

public class InitializeUtil {
    
    public static Deck initializeDeck() {
        Deck deck = new Deck(Suit.VALUES, Rank.VALUES);
        deck.shuffle();

        return deck;
    }

    public static ArrayList<Card> initializeCardPool(Deck deck) {

        ArrayList<Card> poolCards = new ArrayList<Card>();

        for (int i = 0; i < GameUtil.getPoolCardCount(); i++) {
            poolCards.add(deck.dealCard());
        }

        return poolCards;
    }

    public static ArrayList<Player> initializePlayers(Deck deck) {
        ArrayList<Player> players = new ArrayList<Player>();

        // Create players
        for (int i = 0; i < GameUtil.getPlayerCount(); i++) {
            players.add(new Player(i));
        }

        // Distribute cards to players
        for (int i = 0; i < (GameUtil.getHandCardCount() * GameUtil.getPlayerCount()); i++) {
            Player currentPlayer = players.get(i % GameUtil.getPlayerCount());
            currentPlayer.getHand().add(deck.dealCard());
        }

        // Print hands of each player after distribution
        for (Player player : players) {
            System.out.println("Player's hand: " + player.getHand());
        }

        return players;

    }

}
