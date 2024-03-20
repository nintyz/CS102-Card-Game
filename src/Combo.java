/*
* Combo
* 
* Version info: Matching Game version 1.0
* 
* created as part of CS102 Programming Fundamentals II coding
*/
import java.util.*;

public class Combo extends Capture {
    
    public Combo(){};

    public Combo(Card[] captureCards) {
        multiplier = 1.3;
        captureName = "Combo";
        this.captureCards = captureCards;
    }
    
    /**
     * returns Combo object storing the capture cards of they form a valid combo (i.e value sum of poolcards = value of handcard), else return null
    */
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