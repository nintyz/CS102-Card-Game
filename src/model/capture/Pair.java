package model.capture;
import java.util.*;

import model.card.Card;

/**
 * Pair.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class represents a Pair capture where the total capture cards is 2 and all cards have the same rank
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
 * 
 */

public class Pair extends Capture{
  
    public Pair(){};

    //forms a Pair by placing both the handCard and poolCard in an array of length = 2
    public Pair(Card[] captureCards){
        multiplier = 1.0;
        captureName = "Pair";
        this.captureCards = captureCards;
    }

    public Capture formCapture(Card handCard, ArrayList<Card> poolCards){
        /*Pair can only involve 1 handCard and 1 poolCard
         *return null if more than 1 poolCard is selected
        */
        if (poolCards.size() != 1) {
            return null;
        }
        //return null if both cards do not have the same RankValue
        if (handCard.getRank() != poolCards.get(0).getRank()) {
            return null;
        }
        //returns a Pair
        poolCards.add(handCard);
        Collections.sort(poolCards);
        Card[] captureCards = poolCards.toArray(new Card[poolCards.size()]);
        return new Pair(captureCards);
    }
}
