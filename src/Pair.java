import java.util.*;

public class Pair extends Capture{
  
    public Pair(){};

    //forms a Pair by placing both the handCard and poolCard in an array of length = 2
    public Pair(Card[] captureCards){
        multiplier = 1.0;
        captureName = "Pair";
        this.captureCards = captureCards;
    }

    // public Pair formCapture(Card handCard, ArrayList<Card> poolCards){
    //     /*Pair can only involve 1 handCard and 1 poolCard
    //      *return null if more than 1 poolCard is selected
    //     */
    //     if (poolCards.size() != 1) {
    //         return null;
    //     }
    //     //return null if both cards do not have the same RankValue
    //     if (handCard.getRank().compareTo(poolCards.get(0).getRank()) == 0) {
    //         return null;
    //     }
    //     //returns a Pair
    //     return new Pair(handCard, poolCards);
    // }

    public Pair formCapture(Card handCard, ArrayList<Card> poolCards){
        /*Pair can only involve 1 handCard and 1 poolCard
         *return null if more than 1 poolCard is selected
        */
        if (poolCards.size() != 1) {
            return null;
        }
        //return null if both cards do not have the same RankValue
        if (handCard.getRank() != poolCards.get(0).getRank()) {
            return null;
        }
        //returns a Pair
        poolCards.add(handCard);
        Collections.sort(poolCards);
        Card[] captureCards = poolCards.toArray(new Card[poolCards.size()]);
        return new Pair(captureCards);
    }
}
