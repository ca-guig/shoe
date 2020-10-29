package ca.guig.shoe.domain;

import ca.guig.shoe.utils.HandEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class PlayerHand {

    private final List<DeckCard> cards;

    private final int value;

    private PlayerHand(List<DeckCard> cards) {
        this.cards = List.copyOf(cards);
        this.value = HandEvaluator.calculateValue(this.cards);
    }

    public List<DeckCard> getCards() {
        return cards;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlayerHand that = (PlayerHand) o;
        return value == that.value
                && Objects.equals(cards, that.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards, value);
    }

    @Override
    public String toString() {
        return "PlayerHand{"
                + "cards=" + cards
                + ", value=" + value
                + '}';
    }

    public Builder toBuilder() {
        return new Builder(cards);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final List<DeckCard> cards = new ArrayList<>();

        public Builder() {}

        private Builder(List<DeckCard> cards) {
            if (cards != null) {
                this.cards.addAll(cards);
            }
        }

        public List<DeckCard> getCards() {
            return cards;
        }

        public Builder withCards(final List<DeckCard> cards) {
            this.cards.clear();
            this.cards.addAll(cards);
            return this;
        }

        public PlayerHand build() {
            return new PlayerHand(cards);
        }
    }
}
