package ca.guig.shoe.domain;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import ca.guig.shoe.repository.Identifiable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonDeserialize(builder = Game.Builder.class)
public final class Game implements Identifiable {

    @JsonProperty(access = READ_ONLY)
    private final String id;

    private final String name;

    @JsonProperty(access = READ_ONLY)
    private final Map<String, Player> players;

    private Game(String id, String name, Map<String, Player> players) {
        this.id = id;
        this.name = name;
        this.players = Map.copyOf(players);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        return Objects.equals(id, game.id)
                && Objects.equals(name, game.name)
                && Objects.equals(players, game.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, players);
    }

    @Override
    public String toString() {
        return "Game{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", players=" + players
                + '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;

        private String name;

        private final Map<String, Player> players = new HashMap<>();

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withPlayers(final Map<String, Player> players) {
            this.players.clear();
            this.players.putAll(players);
            return this;
        }

        public Builder addPlayer(Player player) {
            this.players.put(player.getId(), player);
            return this;
        }

        public Builder removePlayer(String playerId) {
            this.players.remove(playerId);
            return this;
        }

        public Game build() {
            return new Game(id, name, players);
        }
    }
}
