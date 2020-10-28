package ca.guig.shoe.service.game;

import ca.guig.shoe.domain.Game;
import ca.guig.shoe.domain.Player;

import java.util.List;

public interface GameService {
    Game createGame(Game game);

    Game readGame(String gameId);

    Game updateGame(String gameId, Game game);

    void deleteGame(String gameId);

    List<Game> findAll();

    void addPlayer(String gameId, Player player);

    void removePlayer(String gameId, String playerId);

    void addDeckToShoe(String gameId, String deckId);

    void shuffleShoe(String gameId);

}
