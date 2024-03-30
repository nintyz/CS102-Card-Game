/**
 * model.capture.Run.java
 * 
 * @version 1.0
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * Last modified: 31 Mar 2024
 */


package model.capture;

import java.util.*;

import model.card.Card;
import model.card.Suit;


/**
 * This class represents a Run capture where the total number of cards captured is 3
 * and all cards have the same suit value and their rank values are in sequence
 */
public class Run extends Capture{ 

    public Run(){}
    
    public Run(Card[] captureCards) {
        multiplier = 2.0;
        captureName = "Run";
        this.captureCards = captureCards;
    }
    
    public Capture formCapture(Card handCard, ArrayList<Card> poolCards) {

        List<Card> temp = new ArrayList<>();

        // check if length of selected cards is at least 2
        if(poolCards.size() != 2) {
            return null;
        }
        // check if all selected cards are of the same suit
        Suit suit = handCard.getSuit();
        for(Card c: poolCards) {
            if(!c.getSuit().equals(suit)) {
                return null;
            }

            temp.add(c);
        }

        //check if the ranks of the cards are in sequence
        temp.add(handCard);
        Collections.sort(temp);

        for (int i = 0; i < temp.size() - 1; i++) {
            if(temp.get(i).getRank().compareTo(temp.get(i + 1).getRank()) != -1) {
                return null;
            }
        }

        poolCards.add(handCard);
        Card[] run = poolCards.toArray(new Card[poolCards.size()]);
        return new Run(run);
    }
}

