package ca.guig.shoe.service.game;

import ca.guig.shoe.domain.Deck;
import ca.guig.shoe.domain.Game;
import ca.guig.shoe.domain.Player;
import ca.guig.shoe.repository.game.GameRepository;
import ca.guig.shoe.service.IdGenerator;
import ca.guig.shoe.service.deck.DeckService;
import ca.guig.shoe.utils.CardDealer;
import ca.guig.shoe.utils.CardShuffler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGameService implements GameService {

    private final GameRepository repository;

    private final DeckService deckService;

    private final IdGenerator idGenerator;

    public DefaultGameService(GameRepository repository, DeckService deckService, IdGenerator idGenerator) {
        this.repository = repository;
        this.deckService = deckService;
        this.idGenerator = idGenerator;
    }

    @Override
    public Game createGame(Game game) {
        Game.Builder newGame = Game.builder()
                .withId(idGenerator.generateId())
                .withName(game.getName());
        return repository.save(newGame.build());
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
        Game.Builder updateGame = readGame(gameId).toBuilder();
        updateGame.withName(game.getName());
        return repository.save(updateGame.build());
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
        Game.Builder game = readGame(gameId).toBuilder();
        game.addPlayer(Player.builder().withId(idGenerator.generateId()).withName(player.getName()).build());
        repository.save(game.build());
    }

    @Override
    public void removePlayer(String gameId, String playerId) {
        Game.Builder game = readGame(gameId).toBuilder();
        game.removePlayer(playerId);
        repository.save(game.build());
    }

    @Override
    public void addDeckToShoe(String gameId, String deckId) {
        Game.Builder game = readGame(gameId).toBuilder();

        Deck deck = deckService.readDeck(deckId);

        game.getShoe().getCards().addAll(deck.getCards());

        repository.save(game.build());
    }

    @Override
    public void shuffleShoe(String gameId) {
        Game.Builder game = readGame(gameId).toBuilder();

        CardShuffler cardShuffler = new CardShuffler(game.getShoe().getCards());
        cardShuffler.shuffle();

        repository.save(game.build());
    }

    @Override
    public void dealCards(String gameId, String playerId, int numberOfCards) {
        Game.Builder game = readGame(gameId).toBuilder();

        if (numberOfCards == 0) {
            return;
        }

        Player.Builder player = game.getPlayer(playerId);
        if (player == null) {
            return;
        }

        CardDealer cardDealer = new CardDealer(
                game.getShoe().getCards(),
                player.getHand().getCards(),
                numberOfCards);
        cardDealer.deal();

        repository.save(game.build());
    }
}
