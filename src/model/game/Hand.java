/**
 * game.Hand.java
 * 
 * @version 1.0
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * Last modified: 31 Mar 2024
 */


package model.game;

import java.util.ArrayList;

import model.card.Card;


/**
 * This class represents a Hand in the game
 */
public class Hand {

    private ArrayList<Card> hand;

    public Hand() {
        this.hand = new ArrayList<Card>();
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

}