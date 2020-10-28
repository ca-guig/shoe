package ca.guig.shoe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.guig.shoe.domain.Player;
import ca.guig.shoe.service.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = GamePlayerController.class)
class GamePlayerControllerTest {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mvc;

    @Test
    void addPlayerShouldAddPlayerToGivenGame() throws Exception {
        mvc
                .perform(post("/rest/v1/games/{gameId}/players", "1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Alice\"}"))
                .andExpect(status().isCreated());

        BDDMockito.verify(gameService).addPlayer(BDDMockito.eq("1000"), BDDMockito.refEq(player("Alice")));
    }

    @Test
    void removePlayerShouldRemovePlayerFromGivenGame() throws Exception {
        mvc
                .perform(delete("/rest/v1/games/{gameId}/players/{playerId}", "2000", "9000"))
                .andExpect(status().isNoContent());

        BDDMockito.verify(gameService).removePlayer(BDDMockito.eq("2000"), BDDMockito.eq("9000"));
    }

    private static Player player(String name) {
        return Player.builder().withName(name).build();
    }
}
