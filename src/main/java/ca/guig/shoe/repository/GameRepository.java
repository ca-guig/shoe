package ca.guig.shoe.repository;

import ca.guig.shoe.domain.Game;
import java.util.List;

public interface GameRepository {
    Game save(Game game);

    Game read(String id);

    void delete(String id);

    List<Game> findAll();
}
