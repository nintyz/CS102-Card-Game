import java.util.*;

public class Triple extends Capture {
    
    public Triple(){};

    public Triple(Card[] captureCards) {
        multiplier = 1.5;
        captureName = "Triple";
        this.captureCards = captureCards;
    }
    
    /**
     * Creates and returns a Triple object when the selected handCard and poolCards all have the same rank and total number of cards is 3
     **/

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
