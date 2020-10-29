package ca.guig.shoe.domain;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static java.util.stream.Collectors.toList;

import ca.guig.shoe.repository.Identifiable;
import ca.guig.shoe.utils.PlayerSorter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@JsonDeserialize(builder = Game.Builder.class)
public final class Game implements Identifiable {

    @JsonProperty(access = READ_ONLY)
    private final String id;

    private final String name;

    @JsonProperty(access = READ_ONLY)
    private final Shoe shoe;

    @JsonProperty(access = READ_ONLY)
    private final List<Player> players;

    private Game(String id, String name, Shoe shoe, List<Player> players) {
        this.id = id;
        this.name = name;
        this.shoe = shoe;
        players.sort(new PlayerSorter());
        this.players = List.copyOf(players);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public List<Player> getPlayers() {
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
                && Objects.equals(shoe, game.shoe)
                && Objects.equals(players, game.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shoe, players);
    }

    @Override
    public String toString() {
        return "Game{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", shoe=" + shoe
                + ", players=" + players
                + '}';
    }

    public Builder toBuilder() {
        return new Builder(id, name, shoe, players);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;

        private String name;

        private Shoe.Builder shoe = Shoe.builder();

        private final List<Player.Builder> players = new ArrayList<>();

        public Builder() {}

        @JsonCreator
        private Builder(String id, String name, Shoe shoe, List<Player> players) {
            this.id = id;
            this.name = name;
            this.shoe = shoe != null ? shoe.toBuilder() : Shoe.builder();
            if (players != null) {
                for (Player player : players) {
                    this.players.add(player.toBuilder());
                }
            }
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Shoe.Builder getShoe() {
            return shoe;
        }

        public Player.Builder getPlayer(String playerId) {
            for (Player.Builder player : players) {
                if (player.getId().equals(playerId)) {
                    return player;
                }
            }
            return null;
        }

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withShoe(final Shoe shoe) {
            this.shoe = shoe.toBuilder();
            return this;
        }

        public Builder withPlayers(final List<Player> players) {
            this.players.clear();
            for (Player player : players) {
                this.players.add(player.toBuilder());
            }
            return this;
        }

        public Builder addPlayer(Player player) {
            this.players.add(player.toBuilder());
            return this;
        }

        public Builder removePlayer(String playerId) {
            Iterator<Player.Builder> iterator = this.players.iterator();
            while (iterator.hasNext()) {
                Player.Builder player = iterator.next();
                if (player.getId().equals(playerId)) {
                    iterator.remove();
                    break;
                }
            }
            return this;
        }

        public Game build() {
            return new Game(id, name, shoe.build(), players.stream().map(Player.Builder::build).collect(toList()));
        }
    }
}
