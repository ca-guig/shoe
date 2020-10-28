package ca.guig.shoe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ca.guig.shoe.domain.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

class InMemoryGameRepositoryTest {

    private final GameRepository repository = new InMemoryGameRepository();

    @Test
    void saveShouldSaveGameWhenGameDoesNotExist() {
        Game game = game("123456", "test-game-name");
        Game returnedGame = repository.save(game);

        Game expectedGame = game("123456", "test-game-name");
        assertThat(returnedGame).isEqualTo(expectedGame);
        assertThat(repository.read("123456")).isEqualTo(expectedGame);
    }

    @Test
    void saveShouldReplaceGameWhenGameExists() {
        given(game("1000", "test-game-name"));

        Game returnedGame = repository.save(game("1000", "new-test-game-name"));

        Game expectedGame = game("1000", "new-test-game-name");
        assertThat(returnedGame).isEqualTo(expectedGame);
        assertThat(repository.read("1000")).isEqualTo(expectedGame);
    }

    @Test
    void saveShouldCopyGameObject() {
        Game game = game("123456", "test-game-name");
        Game returnedGame = repository.save(game);

        Game expectedGame = game("123456", "test-game-name");
        assertThat(returnedGame).isEqualTo(expectedGame);
        assertThat(repository.read("123456")).isEqualTo(expectedGame);
    }

    @Test
    void readShouldReturnGameWhenGameExist() {
        given(game("1000", "test-game-name"));

        Game returnedGame = repository.read("1000");
        assertThat(returnedGame).isEqualTo(game("1000", "test-game-name"));
    }

    @Test
    void deleteShouldDeleteGame() {
        given(game("1000", "test-game-name"));

        repository.delete("1000");

        assertThat(repository.read("1000")).isNull();
    }

    @Test
    void findAllShouldReturnAllGames() {
        given(
                game("1000", "game-a"),
                game("2000", "game-b"));

        List<Game> games = repository.findAll();

        assertThat(games).containsExactlyInAnyOrder(game("1000", "game-a"), game("2000", "game-b"));
    }

    private void given(Game... games) {
        for (Game game : games) {
            repository.save(game);
        }
    }

    private static Game game(String id, String name) {
        return Game.builder().withId(id).withName(name).build();
    }
}