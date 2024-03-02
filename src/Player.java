import java.util.ArrayList;


public class Player {

    // Instance variables for player class
    private int playerId;
    private double totalScore;
    private ArrayList<Card> handCards;
    private ArrayList<Card> selectedCards;
        private ArrayList<Card> selectedHandCards;
    //    private ArrayList<Capture> capturedCards;
        
        public Player(int id) {
            this.playerId = id;
            this.handCards = new ArrayList<>();
        }

        public int getPlayerId() {
            return playerId;
        }

    /**
     * Remove a card from the player's hand if a player 
     * captures/run/combo their card(s).
     * 
     * @param selectedCard a card object removed by player
     */
    public void removeCard(Card selectedCard) {
        handCards.remove(selectedCard);
    }

    /**
     * Remove cards from the player's hand if a player 
     * captures/run/combo their card(s).
     * 
     * @param selectedCards a list of cards selected by player
     */
    public void removeCard(ArrayList<Card> selectedCards) {
        handCards.removeAll(selectedCards);
    }

    /**
     * Add a card to the player's hand if a player 
     * captures/run/combo card(s) from the pool deck.
     * 
     * @param card a card object obtained by player
     */
    public void addCard(Card card) {
        handCards.add(card);
    }

    /**
     * Add cards to the player's hand if a player 
     * captures/run/combo card(s) from the pool deck.
     * 
     * @param cards a list of cards obtained by player
     */
    public void addCard(ArrayList<Card> cards) {
        for (Card i : cards) {
            handCards.add(i);
        }
    }

    /**
     * Add a card selected by the player from the pool deck.
     * If player selects a card that exists in his/her hand,
     * add into the selectedHardCards arraylist. Else, add
     * the card into the selectedCards arraylist since the player
     * clicked on a card in the pool deck.
     * 
     * @param card a card object selected by the player
     */
    public void addSelectedCard(Card card){
        if (handCards.contains(card)) {
            selectedHandCards.add(card);
        } else {
            selectedCards.add(card);
        }
    }

    /**
     * Remove a card selected by the player.
     * If the card selected exists in the player's hand,
     * remove it from the selectedHandCards arraylist. Else,
     * remove it frm the selectedCards arraylist since the player
     * clicked on a card in the pool deck.
     * 
     * @param card a card object selected by the player
     */
    public void removeSelectedCard(Card card) {
        if (handCards.contains(card)) {
            selectedHandCards.remove(card);
        } else {
            selectedCards.remove(card); 
        }
    }

    /**
     * Create an arraylist of capturingCards and add the
     * selected cards into the arraylist.
     * 
     * @Return the capture based on the selected cards
     * and null if player did not select anything.
     */  
//    private Capture formCapture() {
//        if (selectedCards.size == 0 || selectedHandCards != 1) {
//            return null;
//        }

//        ArrayList<Card> capturingCards = new ArrayList<Card>();
//        for (int i = 0; i < selectedCards.size(); i++) {
//            capturingCards.add(selectedCards.get(i));
//       }
// // Return to be updated   
//        return null;
//    }

    /**
     * @Return the score of the player as a double.
     */
    public double getScore() {
        return this.totalScore;
    }

    public ArrayList<Card> getHand() {
        return this.handCards;
    }

}
