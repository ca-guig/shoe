package ca.guig.shoe.service.game;

import ca.guig.shoe.domain.Game;
import ca.guig.shoe.domain.Player;
import ca.guig.shoe.repository.game.GameRepository;
import ca.guig.shoe.service.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGameService implements GameService {

    private final GameRepository repository;

    private final IdGenerator idGenerator;

    public DefaultGameService(GameRepository repository, IdGenerator idGenerator) {
        this.repository = repository;
        this.idGenerator = idGenerator;
    }

    @Override
    public Game createGame(Game game) {
        Game copiedGame = Game.builder().withId(idGenerator.generateId()).withName(game.getName()).build();
        return repository.save(copiedGame);
    }

    @Override
    public Game readGame(String gameId) {
        Game game = repository.read(gameId);
        if (game == null) {
            throw new GameNotFoundException();
        }
        return game;
    }

    @Override
    public Game updateGame(String gameId, Game game) {
        Game.Builder gameBuilder = fromGame(readGame(gameId));
        return repository.save(gameBuilder.withName(game.getName()).build());
    }

    @Override
    public void deleteGame(String gameId) {
        repository.delete(gameId);
    }

    @Override
    public List<Game> findAll() {
        return repository.findAll();
    }

    @Override
    public void addPlayer(String gameId, Player player) {
        Game.Builder gameBuilder = fromGame(readGame(gameId));
        gameBuilder.addPlayer(Player.builder().withId(idGenerator.generateId()).withName(player.getName()).build());
        repository.save(gameBuilder.build());
    }

    @Override
    public void removePlayer(String gameId, String playerId) {
        Game.Builder gameBuilder = fromGame(readGame(gameId));
        gameBuilder.removePlayer(playerId);
        repository.save(gameBuilder.build());
    }

    private static Game.Builder fromGame(Game game) {
        return Game.builder().withId(game.getId()).withName(game.getName()).withPlayers(game.getPlayers());
    }
}
