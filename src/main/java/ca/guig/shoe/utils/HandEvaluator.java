package ca.guig.shoe.utils;

import ca.guig.shoe.domain.DeckCard;

import java.util.List;

public final class HandEvaluator {

    private HandEvaluator() {}

    public static int calculateValue(List<DeckCard> cards) {
        return cards.stream().mapToInt(card -> card.getValue().getFace().getValue()).sum();
    }
}
