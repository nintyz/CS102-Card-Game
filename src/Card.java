public class Card implements Comparable<Card> {

   private Suit suitValue;
   private Rank rankValue;
   private static boolean sortRankMajorOrder = true;

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

   public String rankToString() {
      return rankValue.toString();
   }

   public String suitToString() {
      return suitValue.toString();
   }

   public static void setRankMajorSort() {
      sortRankMajorOrder = true;
   }

   public static void setSuitMajorSort() {
      sortRankMajorOrder = false;
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
