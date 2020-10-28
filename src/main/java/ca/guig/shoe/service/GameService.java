package ca.guig.shoe.service;

import ca.guig.shoe.domain.Game;
import ca.guig.shoe.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository repository;

    private final IdGenerator idGenerator;

    public GameService(GameRepository repository, IdGenerator idGenerator) {
        this.repository = repository;
        this.idGenerator = idGenerator;
    }

    public Game createGame(Game game) {
        Game copiedGame = new Game(idGenerator.generateId(), game.getName());
        return repository.save(copiedGame);
    }

    public Game readGame(String id) {
        Game game = repository.read(id);
        if (game == null) {
            throw new GameNotFoundException();
        }
        return game;
    }

    public Game updateGame(String id, Game game) {
        Game existingGame = readGame(id);
        return repository.save(new Game(existingGame.getId(), game.getName()));
    }

    public void deleteGame(String id) {
        repository.delete(id);
    }

    public List<Game> findAll() {
        return repository.findAll();
    }
}
