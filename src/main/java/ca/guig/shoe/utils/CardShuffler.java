package ca.guig.shoe.utils;

import ca.guig.shoe.domain.DeckCard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CardShuffler {

    private final Random random = new Random();

    private List<DeckCard> cards;

    public CardShuffler(List<DeckCard> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public void shuffle() {
        List<DeckCard> shuffledCards = new LinkedList<>();

        for (DeckCard card : cards) {
            shuffledCards.add(random.nextInt(shuffledCards.size() + 1), card);
        }

        this.cards = shuffledCards;
    }

    public List<DeckCard> getCards() {
        return cards;
    }
}
