package ca.guig.shoe.domain;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import ca.guig.shoe.repository.Identifiable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonDeserialize(builder = Deck.Builder.class)
public final class Deck implements Identifiable {

    @JsonProperty(access = READ_ONLY)
    private final String id;

    private final String color;

    @JsonProperty(access = READ_ONLY)
    private final List<DeckCard> cards;

    private Deck(String id, String color, List<DeckCard> cards) {
        this.id = id;
        this.color = color;
        this.cards = List.copyOf(cards);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
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
        Deck deck = (Deck) o;
        return Objects.equals(id, deck.id)
                && Objects.equals(color, deck.color)
                && Objects.equals(cards, deck.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color, cards);
    }

    @Override
    public String toString() {
        return "Deck{"
                + "id='" + id + '\''
                + ", color='" + color + '\''
                + ", cards=" + cards
                + '}';
    }

    public Builder toBuilder() {
        return new Builder(id, color, cards);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;

        private String color;

        private final List<DeckCard> cards = new ArrayList<>();

        public Builder() {}

        @JsonCreator
        private Builder(String id, String color, List<DeckCard> cards) {
            this.id = id;
            this.color = color;
            if (cards != null) {
                this.cards.addAll(cards);
            }
        }

        public String getId() {
            return id;
        }

        public String getColor() {
            return color;
        }

        public List<DeckCard> getCards() {
            return cards;
        }

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withColor(final String color) {
            this.color = color;
            return this;
        }

        public Builder withCards(final List<DeckCard> cards) {
            this.cards.clear();
            this.cards.addAll(cards);
            return this;
        }

        public Deck build() {
            return new Deck(id, color, cards);
        }
    }
}
