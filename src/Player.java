import java.util.ArrayList;
import java.util.List;

public class Player {

    private int playerId;
    private double totalScore;
    private Hand hand;
    
    public Player(int id) {
        this.playerId = id;
        this.hand = new Hand();
    }

    public int getPlayerId() {
        return playerId;
    }

    public double getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(double score) {
        this.totalScore += score;
    }

    public void resetScore() {
        this.totalScore = 0;
    }

    public ArrayList<Card> getHand() {
        return this.hand.getHand();
    }

}
