import java.util.*;

public class Straight extends Capture {
    public Straight(){};

    //Forms a Straight by placing the handCard and poolCards in an Array
    public Straight(Card[] captureCards){
        multiplier = 1.5;
        captureName = "Straight";
        this.captureCards = captureCards;
    }

    public Capture formCapture(Card handCard, ArrayList<Card> poolCards){
        /* Straight involves 1 handCard and at least 2 poolCards
         * Return null if less than 2 poolCards are selected
        */
        if(poolCards.size() < 2)
            return null;

        //Create a new Array to store the Ranks of the selected Cards
        Rank[] allCardRank = new Rank[poolCards.size() + 1];

        //Get the RankValue of the handCard to be compared with that of the poolCards and store it in the Array
        allCardRank[0] = handCard.getRank();

        //Store the RankValues of the poolCards in the same Array
        for (int i = 0; i < poolCards.size(); i++) {
            allCardRank[i + 1] = poolCards.get(i).getRank();
        }

        //Sorts the array in Ascending order of RankValue
        Arrays.sort(allCardRank);
       
        //Compare the RankValues by checking whether the RankValues of its neighbours differ by 1
        for (int i = 1; i < allCardRank.length; i++) {
            if (allCardRank[i].compareTo(allCardRank[i - 1]) != 1) {
                return null;
            }
        }
        poolCards.add(handCard);
        Card[] captureCards = poolCards.toArray(new Card[poolCards.size()]);

        //Returns a Straight
        return new Straight(captureCards);
    }
    
}
