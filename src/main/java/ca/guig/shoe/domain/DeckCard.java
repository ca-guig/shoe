package ca.guig.shoe.domain;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Objects;

@JsonDeserialize(builder = DeckCard.Builder.class)
public final class DeckCard {

    @JsonProperty(access = READ_ONLY)
    private final String id;

    @JsonProperty(access = READ_ONLY)
    private final Card value;

    private DeckCard(String id, Card value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public Card getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeckCard deckCard = (DeckCard) o;
        return Objects.equals(id, deckCard.id)
                && value == deckCard.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "DeckCard{"
                + "id='" + id + '\''
                + ", value=" + value
                + '}';
    }

    public static final class Builder {

        private String id;

        private Card value;

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withValue(final Card value) {
            this.value = value;
            return this;
        }

        public DeckCard build() {
            return new DeckCard(id, value);
        }
    }
}
