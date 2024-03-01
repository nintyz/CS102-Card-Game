public class Triple extends Capture {
    
    private int score;
    private Card[] capturePile;
    
    public Triple(){};

    public Triple(Card hand, Card[] deck) {
        score = 1;
        capturePile = new Card[deck.length + 1];
        capturePile[0] = hand;
        for (int i = 0; i < deck.length; i++) {
            capturePile[i + 1] = deck[i];
        }
    }

    public static Capture formTripleCapture(Card hand, Card[] deck) {
        if (deck.length != 2) {
            return null;
        }
        if (hand.getRank().compareTo(deck[0].getRank()) != 0 || hand.getRank().compareTo(deck[1].getRank()) != 0) {
            return null;
        }
        return new Triple(hand, deck);
    }

    public double scoringSystem() {
        return score * capturePile.length;
    }

    public String captureTripleSuccess() {
        return "You have captured Triple successfully!";
    }
}


