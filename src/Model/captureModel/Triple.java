package Model.captureModel;
import java.util.*;

import Model.cardModel.Card;
/**
 * Triple.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class represents a Triple capture where the total capture cards is 3 and all cards have the same rank
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
 */

public class Triple extends Capture {
    
    public Triple(){};

    public Triple(Card[] captureCards) {
        multiplier = 1.5;
        captureName = "Triple";
        this.captureCards = captureCards;
    }

    public Capture formCapture(Card handCard, ArrayList<Card> poolCards) {
        
        if (poolCards.size() != 2) {
            return null;
        }

        //checks if the rank of any card is different from the rest, return null
        if (!handCard.getRank().equals(poolCards.get(0).getRank()) || !handCard.getRank().equals(poolCards.get(1).getRank())) {
            return null;
        }
        
        poolCards.add(handCard);
        Collections.sort(poolCards); 
        Card[] captureCards = poolCards.toArray(new Card[poolCards.size()]);
        return new Triple(captureCards);
    }

}
