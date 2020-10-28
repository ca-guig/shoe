package ca.guig.shoe.utils;

import ca.guig.shoe.domain.DeckCard;

import java.util.List;

public class CardDealer {

    private final List<DeckCard> deck;

    private final List<DeckCard> hand;

    private final int numberToDeal;

    public CardDealer(List<DeckCard> deck, List<DeckCard> hand, int numberToDeal) {
        this.deck = deck;
        this.hand = hand;
        this.numberToDeal = numberToDeal;
    }

    public void deal() {
        int actualNumberToDeal = Math.min(numberToDeal, deck.size());
        if (actualNumberToDeal == 0) {
            return;
        }

        for (int i = 0; i < actualNumberToDeal; i++) {
            DeckCard dealtCard = deck.remove(0);
            hand.add(dealtCard);
        }
    }
}
