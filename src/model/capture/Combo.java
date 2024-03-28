package model.capture;
import java.util.*;

import model.card.Card;
import model.card.Rank;

/**
 * Combo.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class represents a Combo capture where the sum of the rank value of selected poolcards
 * equals the rank value of the selected hand card
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
 */

public class Combo extends Capture {
    
    public Combo(){};

    public Combo(Card[] captureCards) {
        multiplier = 1.3;
        captureName = "Combo";
        this.captureCards = captureCards;
    }
    
    
    public Capture formCapture(Card handCard, ArrayList<Card> poolCards) {
        int total = 0;
        
        if (poolCards.size() < 2) {
            return null;
        }

        // adds rank value of each poolcard to total
        for (Card card : poolCards) {
            total += card.getRank().getValue();
        }
        
        
        if (handCard.getRank().getValue() != total) {
            return null;
        } 

        poolCards.add(handCard);
        Collections.sort(poolCards);
        Card[] captureCards = poolCards.toArray(new Card[poolCards.size()]);
        return new Combo(captureCards); 
    }
}