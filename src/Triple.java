public class Triple extends Capture {
    //Score multiplier for a Triple
    private static final double multiplier = 1.8;
    
    private Card[] capturePile;
    
    public Triple(){};

    public Triple(Card handCard, Card[] poolCards) {
        capturePile = new Card[poolCards.length + 1];
        capturePile[0] = handCard;
        for (int i = 0; i < poolCards.length; i++) {
            capturePile[i + 1] = poolCards[i];
        }
    }

    public Capture formCapture(Card handCard, Card[] poolCards) {
        if (poolCards.length != 2) {
            return null;
        }
        if (!handCard.getRank().equals(poolCards[0].getRank()) || !handCard.getRank().equals(poolCards[1].getRank())) {
            return null;
        }
        return new Triple(handCard, poolCards);
    }

    public double getScore() {
        return score * capturePile.length;
    }

    public String getCaptureName() {
        return "Triple Capture!";
    }
}
