package ca.guig.shoe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.guig.shoe.domain.Game;
import ca.guig.shoe.service.GameNotFoundException;
import ca.guig.shoe.service.GameService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = GameController.class)
class GameControllerTest {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mvc;

    @Test
    void createShouldCreateGame() throws Exception {
        BDDMockito.willReturn(new Game("1000", "mock")).given(gameService).createGame(BDDMockito.any());

        String json = "{\"name\":\"test-game-name\"}";
        mvc
                .perform(post("/rest/v1/games").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/rest/v1/games/1000"));

        BDDMockito.verify(gameService).createGame(BDDMockito.refEq(new Game("test-game-name")));
    }

    @Test
    void readShouldReturnGame() throws Exception {
        BDDMockito.willReturn(new Game("1000", "mock-name")).given(gameService).readGame("1000");

        mvc
                .perform(get("/rest/v1/games/{id}", "1000"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1000\",\"name\":\"mock-name\"}"));
    }

    @Test
    void updateShouldUpdateGame() throws Exception {
        BDDMockito.willReturn(new Game("1000", "mock-name")).given(gameService).readGame("1000");

        String json = "{\"name\":\"test-game-name\"}";
        mvc
                .perform(put("/rest/v1/games/{id}", "1000").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent());

        BDDMockito.verify(gameService).updateGame(BDDMockito.eq("1000"), BDDMockito.refEq(new Game("test-game-name")));
    }

    @Test
    void deleteShouldDeleteGame() throws Exception {
        mvc
                .perform(delete("/rest/v1/games/{id}", "1000"))
                .andExpect(status().isNoContent());

        BDDMockito.verify(gameService).deleteGame("1000");
    }

    @Test
    void findAllShouldReturnGames() throws Exception {
        BDDMockito
                .willReturn(List.of(new Game("1000", "mock-name-1"), new Game("2000", "mock-name-2")))
                .given(gameService)
                .findAll();

        String expectedJson = "" +
                "[" +
                "  {\"id\":\"1000\",\"name\":\"mock-name-1\"}," +
                "  {\"id\":\"2000\",\"name\":\"mock-name-2\"}" +
                "]";
        mvc
                .perform(get("/rest/v1/games"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldHandleExceptionWhenGameIsNotFound() throws Exception {
        BDDMockito.willThrow(new GameNotFoundException()).given(gameService).readGame("1000");

        mvc
                .perform(get("/rest/v1/games/{id}", "1000"))
                .andExpect(status().isNotFound());
    }
}
