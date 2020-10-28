package ca.guig.shoe.service.game;

import static org.assertj.core.api.Assertions.assertThat;

import ca.guig.shoe.domain.Game;
import ca.guig.shoe.domain.Player;
import ca.guig.shoe.repository.game.GameRepository;
import ca.guig.shoe.repository.game.InMemoryGameRepository;
import ca.guig.shoe.service.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private DefaultGameService gameService;

    @Spy
    private final GameRepository gameRepository = new InMemoryGameRepository();

    @Mock
    private IdGenerator idGenerator;

    @Test
    void createShouldSaveGameWithNewId() {
        BDDMockito.willReturn("1000").given(idGenerator).generateId();

        Game game = gameService.createGame(game("test-game-name"));

        assertThat(game).isEqualTo(game("1000", "test-game-name"));
    }

    @Test
    void readShouldReturnGameWhenGameExists() {
        given(game("1000", "test-game-name"));

        Game game = gameService.readGame("1000");

        assertThat(game).isEqualTo(game("1000", "test-game-name"));
    }

    @Test
    void readShouldThrowExceptionWhenGameDoesNotExist() {
        Assertions.assertThatThrownBy(() -> gameService.readGame("9999")).isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void updateShouldUpdateGameWhenGameExists() {
        given(game("1000", "test-game-name"));

        Game game = gameService.updateGame("1000", game("2000", "new-test-game-name"));

        assertThat(game).isEqualTo(game("1000", "new-test-game-name"));
        assertThat(gameService.readGame("1000"))
                
                .isEqualTo(game("1000", "new-test-game-name"));
    }

    @Test
    void updateShouldThrowExceptionWhenGameDoesNotExist() {
        Assertions
                .assertThatThrownBy(() -> gameService.updateGame("1000", game("new-test-game-name")))
                .isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void deleteShouldDeleteGameWhenGameExists() {
        given(game("1000", "test-game-name"));

        gameService.deleteGame("1000");

        Assertions.assertThatThrownBy(() -> gameService.readGame("1000")).isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void deleteShouldDeleteGameWheneverGameDoesNotExist() {
        gameService.deleteGame("1000");
    }

    @Test
    void findAllShouldReturnAllGames() {
        given(game("1000", "game-a"), game("2000", "game-b"), game("3000", "game-c"));

        List<Game> games = gameService.findAll();

        assertThat(games)
                .containsExactlyInAnyOrder(
                        game("1000", "game-a"),
                        game("2000", "game-b"),
                        game("3000", "game-c"));
    }

    @Test
    void findAllShouldReturnAnEmptyListWhenNoGamesExist() {
        List<Game> games = gameService.findAll();

        assertThat(games).isEmpty();
    }

    @Test
    void addPlayerShouldAddPlayerWhenThereIsNoPlayers() {
        given(game("1000", "game-a"));
        BDDMockito.willReturn("9000").given(idGenerator).generateId();

        gameService.addPlayer("1000", player("Alice"));

        assertThat(gameService.readGame("1000").getPlayers())
                .hasSize(1)
                .containsEntry("9000", player("9000", "Alice"));
    }

    @Test
    void addPlayerShouldAddPlayerWhenThereIsAlreadyOtherPlayers() {
        given(game("2000", "game-b"));
        givenToGame("2000", player("9001", "Alice"), player("9002", "Bobby"));

        BDDMockito.willReturn("5000").given(idGenerator).generateId();
        gameService.addPlayer("2000", player("Carol"));

        assertThat(gameService.readGame("2000").getPlayers())
                .hasSize(3)
                .containsEntry("9001", player("9001", "Alice"))
                .containsEntry("9002", player("9002", "Bobby"))
                .containsEntry("5000", player("5000", "Carol"));
    }

    @Test
    void removePlayerShouldRemovePlayer() {
        given(game("2000", "game-b"));
        givenToGame("2000", player("9001", "Alice"), player("9002", "Bobby"));

        gameService.removePlayer("2000", "9001");

        assertThat(gameService.readGame("2000").getPlayers())
                .hasSize(1)
                .containsEntry("9002", player("9002", "Bobby"));
    }

    private void given(Game game) {
        BDDMockito.willReturn(game.getId()).given(idGenerator).generateId();
        gameService.createGame(game);
    }

    private void given(Game... games) {
        for (Game game : games) {
            BDDMockito.willReturn(game.getId()).given(idGenerator).generateId();
            gameService.createGame(game);
        }
    }

    private void givenToGame(String gameId, Player... players) {
        for (Player player : players) {
            BDDMockito.willReturn(player.getId()).given(idGenerator).generateId();
            gameService.addPlayer(gameId, player);
        }
    }

    private static Game game(String name) {
        return Game.builder().withName(name).build();
    }

    private static Game game(String id, String name) {
        return Game.builder().withId(id).withName(name).build();
    }

    private static Game game(String id, String name, List<Player> players) {
        return Game.builder()
                .withId(id)
                .withName(name)
                .withPlayers(players.stream().collect(Collectors.toMap(Player::getId, Function.identity())))
                .build();
    }

    private static Player player(String name) {
        return Player.builder().withName(name).build();
    }

    private static Player player(String id, String name) {
        return Player.builder().withId(id).withName(name).build();
    }
}