import java.util.*;
public class Game {
    public static void main(String[] args) {
        Deck myDeck = new Deck(Suit.VALUES, Rank.VALUES);
        myDeck.shuffle();
        Hand myHand = new Hand();
        System.out.println("Before:");
        for (int i = 0; i < 3; i++) {
            myHand.addCard(myDeck.dealCard());
            System.out.println(myHand.getCard(i));
        }
        System.out.println("After");
        myHand.sort();
        for(int i = 0; i < 3; i++) {
            System.out.println(myHand.getCard(i));
        }
    
        // while (!myDeck.isEmpty()) {
        //     Card dealt = myDeck.dealCard();
        //     System.out.println(dealt);
        // }

    }
}
