import java.util.ArrayList;
import java.util.Collections;

public class Hand {

   private ArrayList<Card> hand;

   public Hand() {
      this.hand = new ArrayList<Card>();
   }

   public void addCard(Card card) {
      hand.add(card);
   }

   public Card getCard(int index) {
      return (Card) hand.get(index);
   }

   public Card removeCard(Card card) {
      int index = hand.indexOf(card);
      if (index < 0)
         return null;
      else
         return (Card) hand.remove(index);
   }

   public Card removeCard(int index) {
      return (Card) hand.remove(index);
   }

   public void discardHand() {
      hand.clear();
   }

   public int getNumberOfCards() {
      return hand.size();
   }

   public void sort() {
      Collections.sort(hand);
   }

   public boolean isEmpty() {
      return hand.isEmpty();
   }

   public boolean containsCard(Card card) {
      for (Card c: getHand()) {
         if (c.isSameAs(card)) {
            return true;
         }
      }
      return false;
   }

   public int findCard(Card card) {
      return hand.indexOf(card);
   }

   public String toString() {
      return hand.toString();
   }

   public boolean replaceCard(Card oldCard, Card replacementCard) {
      int location = findCard(oldCard);
      if (location < 0)
         return false;
      hand.set(location, replacementCard);
      return true;
   }

   public ArrayList<Card> getHand() {
      return this.hand;
   }

  /**
   * Create an arraylist of capturingCards and add the
   * selected cards into the arraylist.
   * 
   * @Return the capture based on the selected cards
   *         and null if player did not select anything.
   */
  // private Capture formCapture() {
  // if (selectedCards.size == 0 || selectedHandCards != 1) {
  // return null;
  // }

  // ArrayList<Card> capturingCards = new ArrayList<Card>();
  // for (int i = 0; i < selectedCards.size(); i++) {
  // capturingCards.add(selectedCards.get(i));
  // }
  // // Return to be updated
  // return null;
  // }

}