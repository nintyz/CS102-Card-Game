import java.util.Arrays;

public class Straight extends Capture{
    //Score multiplier for a Straight
    public static final double multiplier = 1.3;
  
    public Straight(){};

    //Forms a Straight by placing the handCard and poolCards in an Array
    private Straight(Card handCard, Card[] poolCards){
        captureCards = new Card[poolCards.length+1];
        captureCards[0] = handCard;
        for(int i = 0; i < poolCards.length; i++){
            captureCards[i + 1] = poolCards[i];
        }
    }

    public Capture formCapture(Card handCard, Card[] poolCards){
        /* Straight involves 1 handCard and at least 2 poolCards
         * Return null if less than 2 poolCards are selected
        */
        if(poolCards.length < 2)
            return null;

        //Create a new Array to store the Ranks of the selected Cards
        Rank[] allCardRank = new Rank[poolCards.length + 1];

        //Get the RankValue of the handCard to be compared with that of the poolCards and store it in the Array
        allCardRank[0] = handCard.getRank();
        //Store the RankValues of the poolCards in the same Array
        for (int i = 0; i < poolCards.length; i++)
            allCardRank[i + 1] = poolCards[i].getRank();

        //Sorts the array in Ascending order of RankValue
        Arrays.sort(allCardRank);

        //Compare the RankValues by checking whether the RankValues of its neighbours differ by 1
        for (int i = 1; i < allCardRank.length; i++)
            if (allCardRank[i].compareTo(allCardRank[i - 1]) != 1) {
                return null;
            }
      
        //Returns a Straight
        return new Straight(handCard, poolCards);
    }

    //Get the Score of a Straight depending on the number of Cards captured
    public double getScore(){
        return multiplier * captureCards.length;
    }

    //returns the name of Capture of type "Straight"
    public String getCaptureName(){
        return "Straight";
    }
