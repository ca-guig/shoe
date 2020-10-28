package ca.guig.shoe.domain;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Objects;

@JsonDeserialize(builder = Player.Builder.class)
public final class Player {

    @JsonProperty(access = READ_ONLY)
    private final String id;

    private final String name;

    private final PlayerHand hand;

    private Player(String id, String name, PlayerHand hand) {
        this.id = id;
        this.name = name;
        this.hand = hand;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PlayerHand getHand() {
        return hand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(id, player.id)
                && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Player{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + '}';
    }

    public Builder toBuilder() {
        return new Builder(id, name, hand);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;

        private String name;

        private PlayerHand hand = PlayerHand.builder().build();

        public Builder() {}

        private Builder(String id, String name, PlayerHand hand) {
            this.id = id;
            this.name = name;
            this.hand = hand != null ? hand : PlayerHand.builder().build();
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public PlayerHand getHand() {
            return hand;
        }

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withHand(final PlayerHand hand) {
            this.hand = hand;
            return this;
        }

        public Player build() {
            return new Player(id, name, hand);
        }
    }
}
