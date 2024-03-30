/**
 * Card.java - John K. Estell - 8 May 2003
 * Last modified: 23 Febraury 2004
 */
package model.card;

/**
 * Representation of a single playing card. A card consists of a suit value
 * (e.g. hearts, spades), a rank value (e.g. ace, 7, king), and an image of
 * the front of the card.  A card object is immutable; once instantiated, the
 * values cannot change.
 *
 * @author John K. Estell
 * @version 1.0
 */
public class Card implements Comparable<Card> {

   private static boolean sortRankMajorOrder = true;

   private Suit suitValue;
   private Rank rankValue;

   public Card(Suit suit, Rank rank) {
      this.suitValue = suit;
      this.rankValue = rank;
   }

   public String getCardImage() {
      return rankValue.getSymbol().toLowerCase() + "_of_" + suitValue.getSymbol().toLowerCase() + ".png";
   }

   public Suit getSuit() {
      return suitValue;
   }

   public Rank getRank() {
      return rankValue;
   }

   public String toString() {
      return rankValue.toString() + " of " + suitValue.toString();
   }

   public int compareTo(Card otherCardObject) {
      Card otherCard = otherCardObject;
      int suitDiff = suitValue.compareTo(otherCard.suitValue);
      int rankDiff = rankValue.compareTo(otherCard.rankValue);

      if (sortRankMajorOrder) {
         if (rankDiff != 0)
            return rankDiff;
         else
            return suitDiff;
      } else {
         if (suitDiff != 0)
            return suitDiff;
         else
            return rankDiff;
      }
   }

   public boolean isSameAs(Card card) {
      if ((rankValue != card.rankValue) || (suitValue != card.suitValue))
         return false;
      else
         return true;
   }

   public boolean equals(Object o) {
      Card card = (Card) o;
      if ((rankValue.toString().equals(card.rankValue.toString())) &&
         (suitValue.toString().equals(card.suitValue.toString())))
         return true;
      else
         return false;
   }

}
