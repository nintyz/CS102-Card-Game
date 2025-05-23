/**
 * game.Player.java
 * 
 * @version 1.0
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * Last modified: 31 Mar 2024
 */


package model.game;

import java.util.ArrayList;

import model.card.Card;


/**
 * This class represents a Player in the game
 */
public class Player {

    private int playerId;
    private double totalScore;
    private Hand hand; 
    private ArrayList<Card> selectedCards;
    private ArrayList<Card> selectedHandCards;

    public Player(int id) {
        this.playerId = id;
        this.hand = new Hand();
        selectedCards = new ArrayList<>();
        selectedHandCards = new ArrayList<>();
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
