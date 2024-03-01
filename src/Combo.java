public class ComboCapture extends Capture {
    private int score;
    private Card[] capturePile;

    public ComboCapture(Card hand, Card[] deck) {
        score = 1;
        capturePile = new Card[deck.length + 1];
        capturePile[0] = hand;
        for (int i = 0; i < deck.length; i++) {
            capturePile[i + 1] = deck[i];
        }
    }

    public static Capture FormComboCapture(Card hand, Card[] deck) {
        int total = 0;
        if (deck.length < 2) {
            return null;
        }
        for (Card card : deck) {
            total += card.getRank();
        }
        if (hand.getRank() != total) {
            return null;
        } else {
            return new ComboCapture(hand, deck);
        }
    }

    public double scoringSystem() {
        return score * capturePile.length;
    }

    public String captureComboSuccess() {
        return "You have captured Combo successfully!";
    }
}
