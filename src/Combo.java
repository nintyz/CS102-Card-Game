import java.util.*;

public class Combo extends Capture {
    
    //if sum of value of Rank of selected poolCards equals that of selected handCard

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

        Rank zeroIdxCard = Rank.ACE;
        if (Rank.isAceHigh())
            zeroIdxCard = Rank.TWO;
            
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