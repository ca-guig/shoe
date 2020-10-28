package ca.guig.shoe.service;

import static org.assertj.core.api.Assertions.assertThat;

import ca.guig.shoe.domain.Game;
import ca.guig.shoe.repository.GameRepository;
import ca.guig.shoe.repository.InMemoryGameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Spy
    private GameRepository gameRepository = new InMemoryGameRepository();

    @Mock
    private IdGenerator idGenerator;

    @Test
    void createShouldSaveGameWithNewId() {
        BDDMockito.willReturn("1000").given(idGenerator).generateId();

        Game game = gameService.createGame(new Game("test-game-name"));

        assertThat(game).usingRecursiveComparison().isEqualTo(new Game("1000", "test-game-name"));
    }

    @Test
    void readShouldReturnGameWhenGameExist() {
        given(new Game("1000", "test-game-name"));

        Game game = gameService.readGame("1000");

        assertThat(game).usingRecursiveComparison().isEqualTo(new Game("1000", "test-game-name"));
    }

    @Test
    void readShouldThrowExceptionWhenGameDoesNotExist() {
        Assertions.assertThatThrownBy(() -> gameService.readGame("9999")).isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void updateShouldUpdateGameWhenGameExists() {
        given(new Game("1000", "test-game-name"));

        Game game = gameService.updateGame("1000", new Game("2000", "new-test-game-name"));

        assertThat(game).usingRecursiveComparison().isEqualTo(new Game("1000", "new-test-game-name"));
        assertThat(gameService.readGame("1000"))
                .usingRecursiveComparison()
                .isEqualTo(new Game("1000", "new-test-game-name"));
    }

    @Test
    void updateShouldThrowExceptionWhenGameDoesNotExist() {
        Assertions
                .assertThatThrownBy(() -> gameService.updateGame("1000", new Game("new-test-game-name")))
                .isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void deleteShouldDeleteGameWhenGameExists() {
        given(new Game("1000", "test-game-name"));

        gameService.deleteGame("1000");

        Assertions.assertThatThrownBy(() -> gameService.readGame("1000")).isInstanceOf(GameNotFoundException.class);
    }

    @Test
    void deleteShouldDeleteGameWheneverGameDoesNotExist() {
        gameService.deleteGame("1000");
    }

    @Test
    void findAllShouldReturnAllGames() {
        given(new Game("1000", "game-a"), new Game("2000", "game-b"), new Game("3000", "game-c"));

        List<Game> games = gameService.findAll();

        assertThat(games)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(
                        new Game("1000", "game-a"),
                        new Game("2000", "game-b"),
                        new Game("3000", "game-c"));
    }

    @Test
    void findAllShouldReturnAnEmptyListWhenNoGamesExist() {
        List<Game> games = gameService.findAll();

        assertThat(games).isEmpty();
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

}