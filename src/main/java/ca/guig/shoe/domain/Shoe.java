package ca.guig.shoe.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Shoe {

    private final List<DeckCard> cards;

    private Shoe(List<DeckCard> cards) {
        this.cards = List.copyOf(cards);
    }

    public List<DeckCard> getCards() {
        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shoe shoe = (Shoe) o;
        return Objects.equals(cards, shoe.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }

    @Override
    public String toString() {
        return "Shoe{"
                + "cards=" + cards
                + '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final List<DeckCard> cards = new ArrayList<>();

        public Builder withCards(final List<DeckCard> cards) {
            this.cards.clear();
            this.cards.addAll(cards);
            return this;
        }

        public Builder addCards(final List<DeckCard> cards) {
            this.cards.addAll(cards);
            return this;
        }

        public Shoe build() {
            return new Shoe(cards);
        }
    }
}
