import java.util.*;

public class Run extends Capture{ 
    // Run formed when two or more poolCards are captured such that their ranks are in sequence with the selected handCard. All cards must
    // be of the same suit.

    public Run(){};
    
    public Run(Card[] captureCards) {
        multiplier = 2.0;
        captureName = "Run";
        this.captureCards = captureCards;
    }

    public Capture formCapture(Card handCard, ArrayList<Card> poolCards) {
        // check if length of selected cards is at least 2
        if(poolCards.size() < 2) {
            return null;
        }
        // check if all selected cards are of the same suit
        Suit suit = handCard.getSuit();
        for(Card c: poolCards) {
            if(!c.getSuit().equals(suit)) {
                return null;
            }
        }

        //check if the ranks of the cards are in sequence
        poolCards.add(handCard);
        Collections.sort(poolCards);

        for (int i = 0; i < poolCards.size() - 1; i++) {
            if(poolCards.get(i).getRank().compareTo(poolCards.get(i + 1).getRank()) != -1) {
                return null;
            }
        }

        Card[] run = poolCards.toArray(new Card[poolCards.size()]);
        return new Run(run);
    }
}

