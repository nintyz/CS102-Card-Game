/**
 * model.capture.Combo.java
 * 
 * @version 1.0
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * Last modified: 31 Mar 2024
 */

package model.capture;

import java.util.ArrayList;
import java.util.Collections;

import model.card.Card;

/**
 * This class represents a Combo capture where the number of poolcards selected is two, and the sum of the rank value of 
 * selected poolcards is equal to the rank value of the selected handcard
 */

public class Combo extends Capture {

    public Combo() {}

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

        for (Card card : poolCards) {
            total += card.getRank().getValue();
        }

        if (handCard.getRank().getValue() != total) {
            return null;
        }

        poolCards.add(handCard);
        Collections.sort(poolCards);
        Card[] captureCards = poolCards.toArray(new Card[poolCards.size()]);
        return new Combo(captureCards);
    }
}