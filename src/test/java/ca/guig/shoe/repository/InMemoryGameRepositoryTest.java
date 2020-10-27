package ca.guig.shoe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import ca.guig.shoe.domain.Game;
import java.util.List;
import org.junit.jupiter.api.Test;

class InMemoryGameRepositoryTest {

    private GameRepository repository = new InMemoryGameRepository();

    @Test
    void saveShouldSaveGameWhenGameDoesNotExist() {
        Game game = new Game("123456", "test-game-name");
        Game returnedGame = repository.save(game);

        Game expectedGame = new Game("123456", "test-game-name");
        assertThat(returnedGame).usingRecursiveComparison().isEqualTo(expectedGame);
        assertThat(repository.read("123456")).usingRecursiveComparison().isEqualTo(expectedGame);
    }

    @Test
    void saveShouldReplaceGameWhenGameExists() {
        given(new Game("1000", "test-game-name"));

        Game returnedGame = repository.save(new Game("1000", "new-test-game-name"));

        Game expectedGame = new Game("1000", "new-test-game-name");
        assertThat(returnedGame).usingRecursiveComparison().isEqualTo(expectedGame);
        assertThat(repository.read("1000")).usingRecursiveComparison().isEqualTo(expectedGame);
    }

    @Test
    void saveShouldCopyGameObject() {
        Game game = new Game("123456", "test-game-name");
        Game returnedGame = repository.save(game);

        Game expectedGame = new Game("123456", "test-game-name");
        assertThat(returnedGame).usingRecursiveComparison().isEqualTo(expectedGame);
        assertThat(repository.read("123456")).usingRecursiveComparison().isEqualTo(expectedGame);
    }

    @Test
    void readShouldReturnGameWhenGameExist() {
        given(new Game("1000", "test-game-name"));

        Game returnedGame = repository.read("1000");
        assertThat(returnedGame).usingRecursiveComparison().isEqualTo(new Game("1000", "test-game-name"));
    }

    @Test
    void deleteShouldDeleteGame() {
        given(new Game("1000", "test-game-name"));

        repository.delete("1000");

        assertThat(repository.read("1000")).isNull();
    }

    @Test
    void findAllShouldReturnAllGames() {
        given(
                new Game("1000", "game-a"),
                new Game("2000", "game-b"));

        List<Game> games = repository.findAll();

        assertThat(games)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(new Game("1000", "game-a"), new Game("2000", "game-b"));
    }

    private void given(Game... games) {
        for (Game game : games) {
            repository.save(game);
        }
    }
}