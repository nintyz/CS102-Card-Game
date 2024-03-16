import java.util.*;

public class Triple extends Capture {
    
    public Triple(){};

    public Triple(Card[] captureCards) {
        multiplier = 1.8;
        captureName = "Triple";
        this.captureCards = captureCards;
    }

    public Capture formCapture(Card handCard, ArrayList<Card> poolCards) {
        if (poolCards.length != 2) {
            return null;
        }
        if (!handCard.getRank().equals(poolCards.get(0).getRank()) || !handCard.getRank().equals(poolCards.get(1).getRank())) {
            return null;
        }

        poolCards.add(handCard);
        Collections.sort(poolCards); // sort in ascending order of value
        Card[] captureCards = poolCards.toArray(new Card[poolCards.size()]);
        return new Triple(captureCards);
    }

}
