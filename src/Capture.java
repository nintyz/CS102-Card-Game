import java.util.ArrayList;

public abstract class Capture {
    protected double multiplier;
    protected Card[] captureCards;
    protected String captureName;
    private static Capture[] allPossibleCapture = {new Run(), new Straight(), new Combo(), new Triple(), new Pair()};

    //return capture object of own type if valid capture, null otherwise
    
    public abstract Capture formCapture(Card handCard, ArrayList<Card> poolCards);
    
    public double getScore() {
        return multiplier * captureCards.length;
    }

    public String getCaptureName() {
        return captureName;
    }

    //run through all possible captures, return highest score if can form, null otherwise;
    public static Capture returnHighestCapture(Card handCard, ArrayList<Card> poolCards) {
        for(Capture c: allPossibleCapture) {
            Capture capture = c.formCapture(handCard, poolCards);
            if (capture != null) {
                return capture;
            }
        }
        return null;
    }

    //return cards that are captured
    public Card[] getCaptureCards() {
        return captureCards;
    } 
}