package model.captureModel;
import java.util.*;

import model.cardModel.Card;
import model.cardModel.Rank;

/**
 * Straight.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class represents a Straight capture where the rank of each card captured is in sequence
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
 */

public class Straight extends Capture {
    
    public Straight(){};

    public Straight(Card[] captureCards){
        multiplier = 1.8;
        captureName = "Straight";
        this.captureCards = captureCards;
    }

    public Capture formCapture(Card handCard, ArrayList<Card> poolCards){
        if(poolCards.size() != 2) {
            return null;
        }
        
        Rank[] allCardRank = new Rank[poolCards.size() + 1];

        //Get the RankValue of the handCard to be compared with that of the poolCards and store it in the Array
        allCardRank[0] = handCard.getRank();

        //Store the RankValues of the poolCards in the same Array
        for (int i = 0; i < poolCards.size(); i++) {
            allCardRank[i + 1] = poolCards.get(i).getRank();
        }
        
        Arrays.sort(allCardRank);
       
        //Compare the RankValues by checking whether the RankValues of its neighbours differ by 1
        for (int i = 1; i < allCardRank.length; i++) {
            if (allCardRank[i].compareTo(allCardRank[i - 1]) != 1) {
                return null;
            }
        }

        poolCards.add(handCard);
        Collections.sort(poolCards);
        Card[] captureCards = poolCards.toArray(new Card[poolCards.size()]);
        return new Straight(captureCards);
    }
    
}
