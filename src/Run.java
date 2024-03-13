import java.util.*;

public class Run extends Capture{ 
    //Score multiplier for a Run
    private static final double multiplier = 2.0;

    public Run(){};
    
    public Run(Card[] runCards) {
        captureCards = new Card[runCards.length];
        for(int i = 0; i < runCards.length; i++) {
            captureCards[i] = runCards[i];
        }
        captureName = "Run";
    }

    public Capture formCapture(Card handCard, ArrayList<Card> poolCards) {
        // check if length of selected cards is at least 2
        if(poolCards.length < 2) {
            return null;
        }
        // check if all selected cards are of the same suit
        Suit suit = handCard.getSuit();
        for(Card c: poolCards) {
            if(c.getSuit() != suit) {
                return null;
            }
        }

        //check if the ranks of the cards are in sequence
        Card[] possibleRun = new Card[poolCards.length + 1];
        possibleRun[0] = handCard;
        for(int i = 1; i < possibleRun.length; i++) {
            possibleRun[i] = poolCards[i - 1];
        }
        Arrays.sort(possibleRun);
        for (int i = 0; i < possibleRun.length - 1; i++) {
            if(possibleRun[i].getRank().compareTo(possibleRun[i + 1].getRank()) != -1) {
                return null;
            }
        }

        return new Run(possibleRun);
    }

    public double getScore(){
        return multiplier * captureCards.length;
    }
    
    public String getCaptureName() {
        return captureName;
    }
}

