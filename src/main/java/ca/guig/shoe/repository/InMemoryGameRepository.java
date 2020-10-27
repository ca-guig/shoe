package ca.guig.shoe.repository;

import ca.guig.shoe.domain.Game;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryGameRepository implements GameRepository {

    private final Map<String, Game> data = new HashMap<>();

    @Override
    public Game save(Game game) {
        data.put(game.getId(), game);
        return game;
    }

    @Override
    public Game read(String id) {
        return data.get(id);
    }

    @Override
    public void delete(String id) {
        data.remove(id);
    }

    @Override
    public List<Game> findAll() {
        return List.copyOf(data.values());
    }
}
