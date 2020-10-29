package ca.guig.shoe.domain;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import ca.guig.shoe.repository.Identifiable;
import ca.guig.shoe.utils.PlayerSorter;
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

        private Shoe shoe = Shoe.builder().build();

        private final List<Player> players = new ArrayList<>();

        public Builder() {}

        private Builder(String id, String name, Shoe shoe, List<Player> players) {
            this.id = id;
            this.name = name;
            this.shoe = shoe != null ? shoe : Shoe.builder().build();
            if (players != null) {
                this.players.addAll(players);
            }
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Shoe getShoe() {
            return shoe;
        }

        public Player getPlayer(String playerId) {
            for (Player player : players) {
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
            this.shoe = shoe;
            return this;
        }

        public Builder withPlayers(final List<Player> players) {
            this.players.clear();
            this.players.addAll(players);
            return this;
        }

        public Builder addPlayer(Player player) {
            this.players.add(player);
            return this;
        }

        public Builder updatePlayer(Player player) {
            removePlayer(player.getId());
            addPlayer(player);
            return this;
        }

        public Builder removePlayer(String playerId) {
            Iterator<Player> iterator = this.players.iterator();
            while (iterator.hasNext()) {
                Player player = iterator.next();
                if (player.getId().equals(playerId)) {
                    iterator.remove();
                    break;
                }
            }
            return this;
        }

        public Game build() {
            return new Game(id, name, shoe, players);
        }
    }
}
