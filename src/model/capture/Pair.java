/**
 * model.capture.Pair.java
 * 
 * @version 1.0
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * Last modified: 31 Mar 2024
 */


package model.capture;

import java.util.*;

import model.card.Card;


/**
 * This class represents a Pair capture where the total capture cards is 2 and both cards have the same rank value
 */
public class Pair extends Capture{
  
    public Pair(){}

    public Pair(Card[] captureCards){
        multiplier = 1.0;
        captureName = "Pair";
        this.captureCards = captureCards;
    }

    public Capture formCapture(Card handCard, ArrayList<Card> poolCards){
        if (poolCards.size() != 1) {
            return null;
        }

        if (handCard.getRank() != poolCards.get(0).getRank()) {
            return null;
        }
        
        poolCards.add(handCard);
        Collections.sort(poolCards);
        Card[] captureCards = poolCards.toArray(new Card[poolCards.size()]);
        return new Pair(captureCards);
    }
}
