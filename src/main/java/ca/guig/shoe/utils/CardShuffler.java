package ca.guig.shoe.utils;

import ca.guig.shoe.domain.DeckCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardShuffler {

    private final Random random = new Random();

    private List<DeckCard> cards;

    public CardShuffler(List<DeckCard> cards) {
        this.cards = cards;
    }

    public void shuffle() {
        List<DeckCard> originalCards = new ArrayList<>(cards);

        cards.clear();
        for (DeckCard card : originalCards) {
            cards.add(random.nextInt(cards.size() + 1), card);
        }
    }
}
