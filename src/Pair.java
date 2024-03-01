import java.util.Arrays;

public class Pair extends Capture{
    //Score multiplier for a Pair
    private static final double multiplier = 1;
  
    public Pair(){};

    //forms a Pair by placing both the handCard and poolCard in an array of length = 2
    private Pair(Card handCard, Card[] poolCards){
        captureCards = new Card[2];
        captureCards[0] = handCard;
        captureCards[1] = poolCards[0];
    }

    public Capture formCapture(Card handCard, Card[] poolCards){
        /*Pair can only involve 1 handCard and 1 poolCard
         *return null if more than 1 poolCard is selected
        */
        if (poolCards.length != 1) {
            return null;
        }
        //return null if both cards do not have the same RankValue
        if (handCard.getRank().compareTo(poolCards[0].getRank())) {
            return null;
        }
        //returns a Pair
        return new Pair(handCard, poolCards);
    }

    //returns the score of a Pair
    public double getScore(){
        return multiplier * captureCards.length;
    }

    //returns the name of Capture of type "Pair"
    public String getCaptureName(){
        return "Pair";
    }

}
