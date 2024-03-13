import java.util.ArrayList;

public abstract class Capture {
    protected double multiplier;
    protected Card[] captureCards;
    protected String captureName;
    private static Capture[] allPossibleCapture = {new Run(), new Straight(), new Combo(), new Triple(), new Pair()};

    //return capture object of own type if valid capture, null otherwise
    
    public abstract Capture formCapture(Card handCard, ArrayList<Card> poolCards);
    
    public abstract double getScore();

    public abstract String getCaptureName();

    //run through all possible captures, return highest score if can form, null otherwise;
    public static Capture returnHighestCapture(Card handCard, ArrayList<Card> poolCards) {
        for(Capture c: allPossibleCapture) {
            if(c.formCapture(handCard, poolCards) != null) {
                return c.formCapture(handCard, poolCards);
            }
        }
        return null;
    }

    //return cards that are captured
    public Card[] getCaptureCards() {
        // Card[] returnCards = new Card[captureCards.length];
        // for(int i = 0; i < captureCards.length; i++) {
        //     returnCards[i] = captureCards[i];
        // }
        // Arrays.sort(captureCards);
        return captureCards;
    } 
}