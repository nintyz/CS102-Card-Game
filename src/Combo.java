public class Combo extends Capture {

    public Combo(){};

    public Combo(Card hand, Card[] deck) {
        multiplier = 1;
        captureCards = new Card[deck.length + 1];
        captureCards[0] = hand;
        for (int i = 0; i < deck.length; i++) {
            captureCards[i + 1] = deck[i];
        }
    }

    public Capture formCapture(Card hand, Card[] poolCards) {
        int total = 0;
        if (poolCards.length < 2) {
            return null;
        }
        //check if poolCards contain face cards
        for (Card c : poolCards) {
            if (c.getRank().getName() == "King" || c.getRank().getName() == "Queen" || c.getRank().getName() == "Jack") {
                return null;
            }
        }
        for (Card c : poolCards) {
            String sym = c.getRank().getSymbol();
            int value = Integer.parseInt(sym);
            total += value;
        }
        if (Integer.parseInt(hand.getRank().getSymbol()) != total) {
            return null;
        }
            
        return new Combo(hand, poolCards);
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
