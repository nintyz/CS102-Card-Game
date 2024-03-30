/**
 * model.capture.Capture.java
 * 
 * @version 1.0
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * Last modified: 31 Mar 2024
 */


package model.capture;

import java.util.ArrayList;

import model.card.Card;


/**
 * This abstract class represents a generic capture.
 */
public abstract class Capture {
    private static Capture[] allPossibleCapture = { new Run(), new Straight(), new Combo(), new Triple(), new Pair() };

    protected double multiplier;
    protected Card[] captureCards;
    protected String captureName;

    /**
     * This abstract method is to be implemented by specific capture classes such that it returns a
     * Capture of that specific class if valid, else return null.
     */
    public abstract Capture formCapture(Card handCard, ArrayList<Card> poolCards);

    public double getScore() {
        return multiplier * captureCards.length;
    }

    public String getCaptureName() {
        return captureName;
    }

    /**
     * This method will return a Capture with the highest score possible that can be formed from the 
     * handCard and poolCards selected
     */
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