package model.game;

import java.util.ArrayList;

import model.card.Card;

/**
 * game.Hand.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class represents a Hand in the game
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
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