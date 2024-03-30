package model.capture;

import java.util.ArrayList;

import model.card.Card;

/**
 * model.capture.Capture.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This abstract class represents a generic capture
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
 * 
 */

public abstract class Capture {
    private static Capture[] allPossibleCapture = { new Run(), new Straight(), new Combo(), new Triple(), new Pair() };

    protected double multiplier;
    protected Card[] captureCards;
    protected String captureName;

    /**
     * abstract method to be implemented by specific capture classes, returns a
     * Capture object of a specific type if valid, else return null
     */
    public abstract Capture formCapture(Card handCard, ArrayList<Card> poolCards);

    public double getScore() {
        return multiplier * captureCards.length;
    }

    public String getCaptureName() {
        return captureName;
    }

    // iterate through array of allPossibleCapture, return highest value valid
    // capture object able to be formed, null otherwise
    public static Capture returnHighestCapture(Card handCard, ArrayList<Card> poolCards) {
        for (Capture c : allPossibleCapture) {
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