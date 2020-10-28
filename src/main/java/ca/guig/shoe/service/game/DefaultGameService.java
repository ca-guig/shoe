package ca.guig.shoe.service.game;

import ca.guig.shoe.domain.Deck;
import ca.guig.shoe.domain.Game;
import ca.guig.shoe.domain.Player;
import ca.guig.shoe.domain.PlayerHand;
import ca.guig.shoe.domain.Shoe;
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
        Game copiedGame = Game.builder()
                .withId(idGenerator.generateId())
                .withName(game.getName())
                .build();
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
        Game.Builder gameBuilder = readGame(gameId).toBuilder();
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
        Game.Builder gameBuilder = readGame(gameId).toBuilder();
        gameBuilder.addPlayer(Player.builder().withId(idGenerator.generateId()).withName(player.getName()).build());
        repository.save(gameBuilder.build());
    }

    @Override
    public void removePlayer(String gameId, String playerId) {
        Game.Builder gameBuilder = readGame(gameId).toBuilder();
        gameBuilder.removePlayer(playerId);
        repository.save(gameBuilder.build());
    }

    @Override
    public void addDeckToShoe(String gameId, String deckId) {
        Game.Builder gameBuilder = readGame(gameId).toBuilder();
        Deck deck = deckService.readDeck(deckId);

        Shoe.Builder shoeBuilder = gameBuilder.getShoe().toBuilder();
        shoeBuilder.getCards().addAll(deck.getCards());

        gameBuilder.withShoe(shoeBuilder.build());

        repository.save(gameBuilder.build());
    }

    @Override
    public void shuffleShoe(String gameId) {
        Game.Builder gameBuilder = readGame(gameId).toBuilder();
        Shoe.Builder shoeBuilder = gameBuilder.getShoe().toBuilder();

        CardShuffler cardShuffler = new CardShuffler(shoeBuilder.getCards());
        cardShuffler.shuffle();

        shoeBuilder.withCards(cardShuffler.getCards());

        gameBuilder.withShoe(shoeBuilder.build());

        repository.save(gameBuilder.build());
    }

    @Override
    public void dealCards(String gameId, String playerId, int numberOfCards) {
        Game.Builder gameBuilder = readGame(gameId).toBuilder();

        if (numberOfCards == 0) {
            return;
        }

        Player player = gameBuilder.getPlayer(playerId);
        if (player == null) {
            return;
        }

        Shoe.Builder shoeBuilder = gameBuilder.getShoe().toBuilder();

        Player.Builder playerBuilder = player.toBuilder();
        PlayerHand.Builder playerHandBuilder = playerBuilder.getHand().toBuilder();

        CardDealer cardDealer = new CardDealer(shoeBuilder.getCards(), playerHandBuilder.getCards(), numberOfCards);
        cardDealer.deal();

        playerBuilder.withHand(playerHandBuilder.build());
        gameBuilder.updatePlayer(playerBuilder.build());

        gameBuilder.withShoe(shoeBuilder.build());

        repository.save(gameBuilder.build());
    }
}
