package ca.guig.shoe.controller.game;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.guig.shoe.controller.Routes;
import ca.guig.shoe.domain.Game;
import ca.guig.shoe.service.game.GameNotFoundException;
import ca.guig.shoe.service.game.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(controllers = GameController.class)
class GameControllerTest {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mvc;

    @Test
    void createShouldCreateGame() throws Exception {
        BDDMockito.willReturn(game("1000", "mock")).given(gameService).createGame(BDDMockito.any());

        String json = "{\"name\":\"test-game-name\"}";
        mvc
                .perform(post(Routes.GAME_LIST).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/rest/v1/games/1000"));

        BDDMockito.verify(gameService).createGame(BDDMockito.refEq(game("test-game-name")));
    }

    @Test
    void readShouldReturnGame() throws Exception {
        BDDMockito.willReturn(game("1000", "mock-name")).given(gameService).readGame("1000");

        mvc
                .perform(get(Routes.GAME, "1000"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1000\",\"name\":\"mock-name\"}"));
    }

    @Test
    void updateShouldUpdateGame() throws Exception {
        BDDMockito.willReturn(game("1000", "mock-name")).given(gameService).readGame("1000");

        String json = "{\"name\":\"test-game-name\"}";
        mvc
                .perform(put(Routes.GAME, "1000").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent());

        BDDMockito.verify(gameService).updateGame(BDDMockito.eq("1000"), BDDMockito.refEq(game("test-game-name")));
    }

    @Test
    void deleteShouldDeleteGame() throws Exception {
        mvc
                .perform(delete(Routes.GAME, "1000"))
                .andExpect(status().isNoContent());

        BDDMockito.verify(gameService).deleteGame("1000");
    }

    @Test
    void findAllShouldReturnGames() throws Exception {
        BDDMockito
                .willReturn(List.of(game("1000", "mock-name-1"), game("2000", "mock-name-2")))
                .given(gameService)
                .findAll();

        String expectedJson = ""
                + "["
                + "  {\"id\":\"1000\",\"name\":\"mock-name-1\"},"
                + "  {\"id\":\"2000\",\"name\":\"mock-name-2\"}"
                + "]";
        mvc
                .perform(get(Routes.GAME_LIST))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldHandleExceptionWhenGameIsNotFound() throws Exception {
        BDDMockito.willThrow(new GameNotFoundException()).given(gameService).readGame("1000");

        mvc
                .perform(get(Routes.GAME, "1000"))
                .andExpect(status().isNotFound());
    }

    private static Game game(String name) {
        return Game.builder().withName(name).build();
    }

    private static Game game(String id, String name) {
        return Game.builder().withId(id).withName(name).build();
    }
}
