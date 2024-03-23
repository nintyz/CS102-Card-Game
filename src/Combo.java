import java.util.*;

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
        // gets the value of zero index card to be compared with the other cards
        Rank zeroIdxCard = Rank.ACE;
        if (Rank.isAceHigh()) {
            zeroIdxCard = Rank.TWO;
        }

        // adds rank value of each poolcard to total
        for (Card card : poolCards) {
            total += card.getRank().compareTo(zeroIdxCard) + 1;
        }
        
        
        if (handCard.getRank().compareTo(zeroIdxCard) + 1 != total) {
            return null;
        } 

        poolCards.add(handCard);
        Collections.sort(poolCards);
        Card[] captureCards = poolCards.toArray(new Card[poolCards.size()]);
        return new Combo(captureCards); 
    }
}