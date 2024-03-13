public class Combo extends Capture {
    
    //Score multiplier for a Combo
    private static final double multiplier = 1.3;

    public Combo(){};

    public Combo(Card handCard, Card[] poolCards) {
        captureCards = new Card[poolCards.length + 1];
        captureCards[0] = handCard;
        for (int i = 0; i < poolCards.length; i++) {
            captureCards[i + 1] = poolCards[i];
        }
    }

    public Capture formCapture(Card handCard, Card[] poolCards) {
        int total = 0;
        if (poolCards.length < 2) {
            return null;
        }
        for (Card card : poolCards) {
            total += card.getRank().compareTo(Rank.TWO);
        }
        if (handCard.getRank().compareTo(Rank.TWO) != total) {
            return null;
        } else {
            return new Combo(handCard, poolCards);
        }
    }

    public double getScore() {
        return multiplier * captureCards.length;
    }

    public String getCaptureName() {
        return "You have captured Combo successfully!";
    }
}