// Capture.java - Xavier, Aaron, Andre, En Ting, Gerald -  31 Mar 2024
// Last modified: 31 Mar 2024
// 

import java.util.ArrayList;

/**
 * This abstract class represents a generic capture
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * @version 1.0
 **/
public abstract class Capture {
    protected double multiplier;
    protected Card[] captureCards;
    protected String captureName;
    private static Capture[] allPossibleCapture = {new Run(), new Straight(), new Combo(), new Triple(), new Pair()};

    /**
    *  abstract method to be implemented by specific capture classes, returns a Capture object of a specific type if valid, else return null 
    */
    public abstract Capture formCapture(Card handCard, ArrayList<Card> poolCards);
    
    public double getScore() {
        return multiplier * captureCards.length;
    }

    public String getCaptureName() {
        return captureName;
    }

    //run through all possible captures, return highest value valid capture object, null otherwise
    public static Capture returnHighestCapture(Card handCard, ArrayList<Card> poolCards) {
        for(Capture c: allPossibleCapture) {
            Capture capture = c.formCapture(handCard, poolCards);
            if (capture != null) {
                return capture;
            }
        }
        return null;
    }

    public Card[] getCaptureCards() {
        return captureCards;
    } 
}