import java.util.ArrayList;

/**
 * Player.java
 * 
 * Last modified: 31 Mar 2024
 * 
 * This class represents a Player in the game
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * @version 1.0
 */

public class Player {

    private int playerId;
    private double totalScore;
    private Hand hand;
    private ArrayList<Card> selectedCards = new ArrayList<>();
    private ArrayList<Card> selectedHandCards = new ArrayList<>();

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

    public void addSelectedCard(Card card) {
        if (getHand().contains(card)) {
            selectedHandCards.add(card);
        } else {
            selectedCards.add(card);
        }
    }

    public void removeSelectedCard(Card card) {
        if (getHand().contains(card)) {
            selectedHandCards.remove(card);
        } else {
            selectedCards.remove(card);
        }
    }

    public ArrayList<Card> getSelectedCards() {
        return this.selectedCards;
    }

    public ArrayList<Card> getSelectedHandCards() {
        return this.selectedHandCards;
    }

}
